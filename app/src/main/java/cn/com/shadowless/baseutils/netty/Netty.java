package cn.com.shadowless.baseutils.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

/**
 * The interface Netty.
 *
 * @author sHadowLess
 */
public interface Netty {
    /**
     * Connect.
     *
     * @param host the host
     * @param port the port
     */
    void connect(String host, int port);

    /**
     * 重连
     *
     * @param delayMillis the delay millis
     */
    void reconnect(long delayMillis);

    /**
     * 发送消息
     *
     * @param msg the msg
     */
    void sendMessage(Object msg);

    /**
     * 设置连接监听
     *
     * @param listener the listener
     */
    void setOnConnectListener(OnConnectListener listener);

    /**
     * 设置消息发送监听
     *
     * @param listener the listener
     */
    void setOnSendMessageListener(OnSendMessageListener listener);

    /**
     * 关闭
     */
    void close();

    /**
     * 断开链接
     */
    void disconnect();

    /**
     * 是否连接
     *
     * @return boolean boolean
     */
    boolean isConnected();

    /**
     * 是否打开
     *
     * @return boolean boolean
     */
    boolean isOpen();

    /**
     * 获取{@link ChannelFuture}
     *
     * @return channel future
     */
    ChannelFuture getChannelFuture();

    /**
     * 连接监听
     */
    interface OnConnectListener {
        /**
         * On success.
         */
        void onSuccess();

        /**
         * On failed.
         */
        void onFailed();

        /**
         * On error.
         *
         * @param e the e
         */
        void onError(Exception e);
    }

    /**
     * 通道消息处理（接收消息）
     */
    interface OnChannelHandler {
        /**
         * On message received.
         *
         * @param ctx the ctx
         * @param msg the msg
         */
        void onMessageReceived(ChannelHandlerContext ctx, String msg);

        /**
         * On exception caught.
         *
         * @param ctx the ctx
         * @param e   the e
         */
        void onExceptionCaught(ChannelHandlerContext ctx, Throwable e);
    }

    /**
     * 发送消息监听
     */
    interface OnSendMessageListener {
        /**
         * On send message.
         *
         * @param msg     the msg
         * @param success the success
         */
        void onSendMessage(Object msg, boolean success);

        /**
         * On exception.
         *
         * @param e the e
         */
        void onException(Throwable e);
    }
}
