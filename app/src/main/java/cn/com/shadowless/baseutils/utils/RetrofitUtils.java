package cn.com.shadowless.baseutils.utils;

import com.google.gson.Gson;

import java.io.EOFException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络工具类
 *
 * @author sHadowLess
 */
public class RetrofitUtils {

    /**
     * 根地址
     */
    private final String baseUrl;

    /**
     * 超时时间
     */
    private final int timeOut;

    /**
     * 时间单位
     */
    private final TimeUnit timeOutUnit;

    /**
     * okHttp实例
     */
    private OkHttpClient okHttpClient;

    /**
     * Retrofit实例
     */
    private Retrofit retrofit;

    /**
     * The Gson.
     */
    private Gson gson;

    /**
     * The Factory.
     */
    private Converter.Factory converterFactory;

    /**
     * The Call adapterFactory.
     */
    private CallAdapter.Factory callAdapterFactory;

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
     * 初始化完成回调
     *
     * @param <T> the type parameter
     */
    public interface InitCallBack<T> {
        /**
         * 完成
         *
         * @param api the 接口
         */
        void finish(T api);
    }

    /**
     * 初始化retrofit
     *
     * @return the net utils
     */
    public RetrofitUtils initRetrofit() {
        if (null == okHttpClient) {
            okHttpClient = getOkHttpClient();
        }
        if (null == gson) {
            gson = new Gson();
        }
        if (null == callAdapterFactory) {
            callAdapterFactory = RxJava2CallAdapterFactory.create();
        }
        if (null == converterFactory) {
            converterFactory = GsonConverterFactory.create(gson);
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient)
                .build();
        return this;
    }

    /**
     * 初始化接口
     *
     * @param <T>          the type 接口类型
     * @param cls          the 接口类
     * @param initCallBack the 回调
     * @return the net utils
     */
    public <T> RetrofitUtils initApi(Class<T> cls, InitCallBack<T> initCallBack) {
        T api = retrofit.create(cls);
        initCallBack.finish(api);
        return this;
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
                return "无法解析域名，请检查网络或联系管理员";
            case TIMEOUT:
                return "请求数据超时，请重试";
            case SOCKET_CLOSED:
                return "网络故障，无法与服务器通讯";
            case CONNECT_REFUSED:
                return "无法连接服务器，请检测网络或联系管理员";
            case WAVE:
                return "网络波动，清重试";
            default:
                return "请求失败，请重试";
        }
    }

    /**
     * 构造
     *
     * @param baseUrl            the base url
     * @param timeOut            the time out
     * @param timeOutUnit        the time out unit
     * @param okHttpClient       the ok http client
     * @param converterFactory   the converter factory
     * @param callAdapterFactory the call adapter factory
     * @param gson               the gson
     */
    public RetrofitUtils(String baseUrl, int timeOut, TimeUnit timeOutUnit, OkHttpClient okHttpClient, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory, Gson gson) {
        this.baseUrl = baseUrl;
        this.timeOut = timeOut;
        this.timeOutUnit = timeOutUnit;
        this.okHttpClient = okHttpClient;
        this.converterFactory = converterFactory;
        this.callAdapterFactory = callAdapterFactory;
        this.gson = gson;
    }

    /**
     * 构造者
     *
     * @return the net utils . net utils builder
     */
    public static RetrofitUtils.NetUtilsBuilder builder() {
        return new RetrofitUtils.NetUtilsBuilder();
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
         * The Gson.
         */
        private Gson gson;
        /**
         * Gson工厂类
         */
        private Converter.Factory converterFactory;
        /**
         * RxJava适配器
         */
        private CallAdapter.Factory callAdapterFactory;

        /**
         * Base url net utils . net utils builder.
         *
         * @param baseUrl the base url
         * @return the net utils . net utils builder
         */
        public RetrofitUtils.NetUtilsBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Base url net utils . net utils builder.
         *
         * @param gson the gson
         * @return the net utils . net utils builder
         */
        public RetrofitUtils.NetUtilsBuilder gson(Gson gson) {
            this.gson = gson;
            return this;
        }

        /**
         * Time out net utils . net utils builder.
         *
         * @param timeOut the time out
         * @return the net utils . net utils builder
         */
        public RetrofitUtils.NetUtilsBuilder timeOut(int timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        /**
         * Time out unit net utils . net utils builder.
         *
         * @param timeOutUnit the time out unit
         * @return the net utils . net utils builder
         */
        public RetrofitUtils.NetUtilsBuilder timeOutUnit(TimeUnit timeOutUnit) {
            this.timeOutUnit = timeOutUnit;
            return this;
        }

        /**
         * Ok http client net utils . net utils builder.
         *
         * @param okHttpClient the ok http client
         * @return the net utils . net utils builder
         */
        public RetrofitUtils.NetUtilsBuilder okHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }

        /**
         * Converter factory net utils . net utils builder.
         *
         * @param converterFactory the converter factory
         * @return the net utils . net utils builder
         */
        public RetrofitUtils.NetUtilsBuilder converterFactory(Converter.Factory converterFactory) {
            this.converterFactory = converterFactory;
            return this;
        }

        /**
         * Call adapter factory net utils . net utils builder.
         *
         * @param callAdapterFactory the call adapter factory
         * @return the net utils . net utils builder
         */
        public RetrofitUtils.NetUtilsBuilder callAdapterFactory(CallAdapter.Factory callAdapterFactory) {
            this.callAdapterFactory = callAdapterFactory;
            return this;
        }

        /**
         * Build net utils.
         *
         * @return the net utils
         */
        public RetrofitUtils build() {
            return new RetrofitUtils(this.baseUrl, this.timeOut, this.timeOutUnit, this.okHttpClient, this.converterFactory, this.callAdapterFactory, this.gson);
        }
    }
}
