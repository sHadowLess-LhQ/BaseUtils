package cn.com.shadowless.baseutils;

import com.google.gson.Gson;

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
    private int timeOut = 10;

    /**
     * The Time out unit.
     */
    private TimeUnit timeOutUnit = TimeUnit.SECONDS;

    /**
     * The Ok http client.
     */
    private OkHttpClient okHttpClient;

    /**
     * The interface Init interface.
     */
    interface InitInterface {
        /**
         * Create interface.
         *
         * @param retrofit the retrofit
         */
        void createInterface(Retrofit retrofit);
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
     * Gets ok http client.
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
}
