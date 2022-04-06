package cn.com.shadowless.baseutils.utils;

import com.google.gson.Gson;

import java.io.EOFException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.Builder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络工具类
 *
 * @author sHadowLess
 */
public class NetUtils {

    /**
     * 根地址
     */
    private String baseUrl;

    /**
     * 超时时间
     */
    private int timeOut;

    /**
     * 时间单位
     */
    private TimeUnit timeOutUnit;

    /**
     * okHttp实例
     */
    private OkHttpClient okHttpClient;

    /**
     * 超时错误信息
     */
    private static final String ERROR_TIME_OUT_MESSAGE = "请求数据超时，请重试";

    /**
     * 连接错误信息
     */
    private static final String ERROR_CONNECT_REFUSED_MESSAGE = "无法连接服务器，请检测网络或联系管理员";

    /**
     * 故障错误信息
     */
    private static final String ERROR_SOCKET_CLOSED_MESSAGE = "网络故障，无法与服务器通讯";

    /**
     * 波动错误信息
     */
    private static final String ERROR_WAVE_MESSAGE = "网络波动，清重试";

    /**
     * 域名错误信息
     */
    private static final String ERROR_UN_KNOW_HOST_MESSAGE = "无法解析域名，请检查网络或联系管理员";

    /**
     * 错误默认信息
     */
    private static final String ERROR_DEFAULT = "请求失败，请重试";

    /**
     * The Api map.
     */
    private static Map<String, Object> API_MAP = null;

    /**
     * 错误枚举
     */
    private enum ERROR {
        /**
         * 超时
         */
        TIMEOUT,
        /**
         * 连接错误
         */
        CONNECT_REFUSED,
        /**
         * 套接字关闭
         */
        SOCKET_CLOSED,
        /**
         * 波动
         */
        WAVE,
        /**
         * 域名
         */
        UN_KNOW_HOST,
        /**
         * 默认
         */
        DEFAULT
    }

    /**
     * 获取ohkHttp实例
     *
     * @return the ok http client
     */
    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(timeOut, timeOutUnit)
                .readTimeout(timeOut, timeOutUnit)
                .writeTimeout(timeOut, timeOutUnit)
                .build();
    }

    /**
     * 初始化retrofit
     *
     * @param cls the cls
     */
    public void initRetrofit(Class<?>... cls) {
        if (null == okHttpClient) {
            okHttpClient = getOkHttpClient();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        API_MAP = new HashMap<>(cls.length);
        for (Class<?> cl : cls) {
            Object obj = retrofit.create(cl);
            API_MAP.put(cl.getSimpleName(), obj);
        }
    }

    /**
     * 获取Api对象
     *
     * @param name the name
     * @return the object
     */
    public static <T> T getApi(String name) {
        return (T) API_MAP.get(name);
    }

    /**
     * RxJava订阅所处线程
     *
     * @param <T> the type parameter
     * @return the observable transformer
     */
    public static <T> ObservableTransformer<T, T> obsIoMain() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取错误信息
     *
     * @param throwable the 异常
     * @return the exception message
     */
    public static String getExceptionMessage(Throwable throwable) {
        return exceptionMessage(checkException(throwable));
    }

    /**
     * 确定异常信息类型
     *
     * @param e the 异常
     * @return the constants . error
     */
    private static ERROR checkException(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            return ERROR.TIMEOUT;
        } else if (e instanceof ConnectException) {
            return ERROR.CONNECT_REFUSED;
        } else if (e instanceof SocketException) {
            return ERROR.SOCKET_CLOSED;
        } else if (e instanceof EOFException) {
            return ERROR.WAVE;
        } else if (e instanceof UnknownHostException) {
            return ERROR.UN_KNOW_HOST;
        } else {
            return ERROR.DEFAULT;
        }
    }

    /**
     * 确定异常详细信息
     *
     * @param error the 错误枚举
     * @return the string
     */
    private static String exceptionMessage(ERROR error) {
        switch (error) {
            case UN_KNOW_HOST:
                return ERROR_UN_KNOW_HOST_MESSAGE;
            case TIMEOUT:
                return ERROR_TIME_OUT_MESSAGE;
            case SOCKET_CLOSED:
                return ERROR_SOCKET_CLOSED_MESSAGE;
            case CONNECT_REFUSED:
                return ERROR_CONNECT_REFUSED_MESSAGE;
            case WAVE:
                return ERROR_WAVE_MESSAGE;
            default:
                return ERROR_DEFAULT;
        }
    }

    /**
     * 构造
     *
     * @param baseUrl      the base url
     * @param timeOut      the time out
     * @param timeOutUnit  the time out unit
     * @param okHttpClient the ok http client
     */
    public NetUtils(String baseUrl, int timeOut, TimeUnit timeOutUnit, OkHttpClient okHttpClient) {
        this.baseUrl = baseUrl;
        this.timeOut = timeOut;
        this.timeOutUnit = timeOutUnit;
        this.okHttpClient = okHttpClient;
    }

    /**
     * 构造者
     *
     * @return the net utils . net utils builder
     */
    public static NetUtils.NetUtilsBuilder builder() {
        return new NetUtils.NetUtilsBuilder();
    }

    /**
     * 构造者实体
     */
    public static class NetUtilsBuilder {
        /**
         * 根地址
         */
        private String baseUrl;
        /**
         * 超时时间
         */
        private int timeOut = 10;
        /**
         * 超时时间单位
         */
        private TimeUnit timeOutUnit = TimeUnit.SECONDS;
        /**
         * OkHttp对象
         */
        private OkHttpClient okHttpClient;

        /**
         * Base url net utils . net utils builder.
         *
         * @param baseUrl the base url
         * @return the net utils . net utils builder
         */
        public NetUtils.NetUtilsBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Time out net utils . net utils builder.
         *
         * @param timeOut the time out
         * @return the net utils . net utils builder
         */
        public NetUtils.NetUtilsBuilder timeOut(int timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        /**
         * Time out unit net utils . net utils builder.
         *
         * @param timeOutUnit the time out unit
         * @return the net utils . net utils builder
         */
        public NetUtils.NetUtilsBuilder timeOutUnit(TimeUnit timeOutUnit) {
            this.timeOutUnit = timeOutUnit;
            return this;
        }

        /**
         * Ok http client net utils . net utils builder.
         *
         * @param okHttpClient the ok http client
         * @return the net utils . net utils builder
         */
        public NetUtils.NetUtilsBuilder okHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }

        /**
         * Build net utils.
         *
         * @return the net utils
         */
        public NetUtils build() {
            return new NetUtils(this.baseUrl, this.timeOut, this.timeOutUnit, this.okHttpClient);
        }
    }
}
