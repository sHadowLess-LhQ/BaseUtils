package cn.com.shadowless.baseutils;

import com.google.gson.Gson;

import java.io.EOFException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
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
 * The type Net utils.
 *
 * @author sHadowLess
 */
@Builder
public class NetUtils {

    /**
     * 根地址
     */
    private String baseUrl;

    /**
     * The Default timeout.
     */
    private int timeOut;

    /**
     * The Time out unit.
     */
    private TimeUnit timeOutUnit;

    /**
     * The Ok http client.
     */
    private OkHttpClient okHttpClient;

    /**
     * The constant ERROR_TIME_OUT_MESSAGE.
     */
    private static final String ERROR_TIME_OUT_MESSAGE = "请求数据超时，请重试";

    /**
     * The constant ERROR_CONNECT_REFUSED_MESSAGE.
     */
    private static final String ERROR_CONNECT_REFUSED_MESSAGE = "无法连接服务器，请检测网络或联系管理员";
    /**
     * The constant ERROR_SOCKET_CLOSED_MESSAGE.
     */
    private static final String ERROR_SOCKET_CLOSED_MESSAGE = "网络故障，无法与服务器通讯";
    /**
     * The constant ERROR_WAVE_MESSAGE.
     */
    private static final String ERROR_WAVE_MESSAGE = "网络波动，清重试";
    /**
     * The constant ERROR_UN_KNOW_HOST_MESSAGE.
     */
    private static final String ERROR_UN_KNOW_HOST_MESSAGE = "无法解析域名，请检查网络或联系管理员";
    /**
     * The constant ERROR_DEFAULT.
     */
    private static final String ERROR_DEFAULT = "请求失败，请重试";

    /**
     * The enum Error.
     */
    private enum ERROR {
        /**
         * Timeout error.
         */
        TIMEOUT,
        /**
         * Connect refused error.
         */
        CONNECT_REFUSED,
        /**
         * Socket closed error.
         */
        SOCKET_CLOSED,
        /**
         * Wave error.
         */
        WAVE,
        /**
         * Un know host error.
         */
        UN_KNOW_HOST,
        /**
         * Default error.
         */
        DEFAULT
    }

    /**
     * The interface Init interface.
     */
    public interface InitInterface {
        /**
         * Create interface.
         *
         * @param retrofit the retrofit
         */
        void createInterface(Retrofit retrofit);
    }

    /**
     * Gets ok http client.
     *
     * @return the ok http client
     */
    private OkHttpClient getOkHttpClient() {
        if (timeOutUnit == null) {
            timeOutUnit = TimeUnit.SECONDS;
        } else if (timeOut == 0) {
            timeOut = 10;
        }
        return new OkHttpClient.Builder()
                .connectTimeout(timeOut, timeOutUnit)
                .readTimeout(timeOut, timeOutUnit)
                .writeTimeout(timeOut, timeOutUnit)
                .build();
    }

    /**
     * Init retrofit.
     *
     * @param initInterface the init interface
     */
    public void initRetrofit(InitInterface initInterface) {
        //初始化接口
        if (null == okHttpClient) {
            okHttpClient = getOkHttpClient();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        initInterface.createInterface(retrofit);
    }

    /**
     * Obs io main observable transformer.
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
     * Gets exception message.
     *
     * @param throwable the throwable
     * @return the exception message
     */
    public static String getExceptionMessage(Throwable throwable) {
        return exceptionMessage(checkException(throwable));
    }

    /**
     * Check exception constants . error.
     *
     * @param e the e
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
     * Exception message string.
     *
     * @param error the error
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
}
