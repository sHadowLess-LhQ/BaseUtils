package cn.com.shadowless.baseutils.netty;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * The type Netty.
 *
 * @author sHadowLess
 */
public class NettyClient implements Netty {

    /**
     * The constant TAG.
     */
    public static final String TAG = NettyClient.class.getSimpleName();

    /**
     * The constant NETTY_INIT.
     */
    private static final int NETTY_INIT = 0x01;
    /**
     * The constant NETTY_CONNECT.
     */
    private static final int NETTY_CONNECT = 0x02;
    /**
     * The constant NETTY_SEND_MESSAGE.
     */
    private static final int NETTY_SEND_MESSAGE = 0x03;
    /**
     * The M handler thread.
     */
    private HandlerThread mHandlerThread;

    /**
     * The M handler.
     */
    private Handler mHandler;

    /**
     * The M main handler.
     */
    private Handler mMainHandler;

    /**
     * The M host.
     */
    private String mHost;

    /**
     * The M port.
     */
    private int mPort;

    /**
     * The Is debug.
     */
    private final boolean isDebug;

    /**
     * The M channel future.
     */
    private ChannelFuture mChannelFuture;

    /**
     * The M bootstrap.
     */
    private Bootstrap mBootstrap;

    /**
     * The M channel initializer.
     */
    private final ChannelInitializer<SocketChannel> mChannelInitializer;

    /**
     * The M on connect listener.
     */
    private OnConnectListener mOnConnectListener;

    /**
     * The M on send message listener.
     */
    private OnSendMessageListener mOnSendMessageListener;

    /**
     * 构造
     *
     * @param isDebug  the is debug
     * @param handlers the handlers
     */
    public NettyClient(boolean isDebug, ChannelHandler... handlers) {
        this.isDebug = isDebug;
        this.mChannelInitializer = new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                //建立管道
                ChannelPipeline channelPipeline = ch.pipeline();
                //添加相关编码器，解码器，处理器等
                for (ChannelHandler channelHandler : handlers) {
                    channelPipeline.addLast(channelHandler);
                }
            }
        };
        initHandlerThread();
        mHandler.sendEmptyMessage(NETTY_INIT);
    }

    /**
     * Init handler thread.
     */
    private void initHandlerThread() {
        mMainHandler = new Handler(Looper.getMainLooper());
        mHandlerThread = new HandlerThread(NettyClient.class.getSimpleName());
        mHandlerThread.start();

        mHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case NETTY_INIT:
                        handleNettyInit();
                        break;
                    case NETTY_CONNECT:
                        handleConnect();
                        break;
                    case NETTY_SEND_MESSAGE:
                        handleSendMessage(msg.obj);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    /**
     * Handle netty init.
     */
    private void handleNettyInit() {
        mBootstrap = new Bootstrap();
        mBootstrap.channel(NioSocketChannel.class);
        EventLoopGroup mGroup = new NioEventLoopGroup();
        mBootstrap.group(mGroup)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(new LoggingHandler(isDebug ? LogLevel.DEBUG : LogLevel.INFO));
        mBootstrap.handler(mChannelInitializer);
    }


    /**
     * Handle connect.
     */
    private void handleConnect() {
        try {
            mChannelFuture = mBootstrap.connect(mHost, mPort)
                    .addListener(future -> {
                        boolean isSuccess = future.isSuccess();
                        if (isDebug) {
                            if (isSuccess) {
                                Log.d(TAG, "Netty connect success.");
                            } else {
                                Log.d(TAG, "Netty connect failed.");
                            }
                        }
                        if (mOnConnectListener != null) {
                            mMainHandler.post(() -> {
                                if (isSuccess) {
                                    mOnConnectListener.onSuccess();
                                } else {
                                    mOnConnectListener.onFailed();
                                }
                            });

                        }
                    })
                    .sync();
        } catch (Exception e) {
            e.printStackTrace();
            if (mOnConnectListener != null) {
                mMainHandler.post(() -> mOnConnectListener.onError(e));
            }
        }
    }

    /**
     * Handle send message.
     *
     * @param msg the msg
     */
    private void handleSendMessage(Object msg) {
        try {
            if (isOpen()) {
                mChannelFuture.channel().writeAndFlush(msg).addListener(future -> {
                    boolean isSuccess = future.isSuccess();
                    if (isDebug) {
                        if (isSuccess) {
                            Log.d(TAG, "Send message:" + msg);
                        } else {
                            Log.d(TAG, "Send failed.");
                        }
                    }
                    if (mOnSendMessageListener != null) {
                        mMainHandler.post(() -> mOnSendMessageListener.onSendMessage(msg, isSuccess));
                    }

                }).sync();
            } else {
                if (mOnSendMessageListener != null) {
                    mMainHandler.post(() -> mOnSendMessageListener.onSendMessage(msg, false));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (mOnSendMessageListener != null) {
                mMainHandler.post(() -> mOnSendMessageListener.onException(e));
            }
        }
    }

    @Override
    public void connect(String host, int port) {
        if (isConnected()) {
            return;
        }
        this.mHost = host;
        this.mPort = port;
        mHandler.sendEmptyMessage(NETTY_CONNECT);
    }

    @Override
    public void reconnect(long delayMillis) {
        close();
        mHandler.sendEmptyMessageDelayed(NETTY_CONNECT, delayMillis);
    }

    @Override
    public void sendMessage(Object msg) {
        mHandler.obtainMessage(NETTY_SEND_MESSAGE, msg).sendToTarget();
    }

    @Override
    public void setOnConnectListener(OnConnectListener listener) {
        this.mOnConnectListener = listener;
    }

    @Override
    public void setOnSendMessageListener(OnSendMessageListener listener) {
        this.mOnSendMessageListener = listener;
    }

    @Override
    public void close() {
        if (isOpen()) {
            mChannelFuture.channel().close();
            if (isDebug) {
                Log.d(TAG, "Netty channel connect closed.");
            }

        }
    }

    @Override
    public void disconnect() {
        if (isConnected()) {
            mChannelFuture.channel().disconnect();
            if (isDebug) {
                Log.d(TAG, "Netty channel disconnected.");
            }
        }
    }


    @Override
    public boolean isConnected() {
        boolean isConnected = mChannelFuture != null && mChannelFuture.channel().isActive();
        if (isDebug && !isConnected) {
            Log.w(TAG, "Netty channel is not connected.");
        }
        return isConnected;
    }

    @Override
    public boolean isOpen() {
        boolean isOpen = mChannelFuture != null && mChannelFuture.channel().isOpen();
        if (isDebug && !isOpen) {
            Log.w(TAG, "Netty channel is not opened.");
        }
        return isOpen;
    }

    @Override
    public ChannelFuture getChannelFuture() {
        return mChannelFuture;
    }
}
