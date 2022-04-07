package cn.com.shadowless.baseutils.utils;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava2工具类
 *
 * @author sHadowLess
 */
public class RxUtils {

    /**
     * Instantiates a new Rx utils.
     */
    private RxUtils() {
    }

    /**
     * 线程枚举
     */
    public enum ThreadSign {
        /**
         * 默认
         */
        DEFAULT,
        /**
         * 非密集计算线程到主线程
         */
        IO_TO_MAIN,
        /**
         * 非密集计算线程到非密集计算线程
         */
        IO_TO_IO,
        /**
         * 非密集计算线程到新线程
         */
        IO_TO_NEW_THREAD,
        /**
         * 非密集计算线程到密集计算线程
         */
        IO_TO_COMPUTATION,
        /**
         * 非密集计算线程到有序单线程
         */
        IO_TO_SINGLE,
        /**
         * 新线程到主线程
         */
        NEW_THREAD_TO_MAIN,
        /**
         * 新线程到非密集计算线程
         */
        NEW_THREAD_TO_IO,
        /**
         * 新线程到新线程
         */
        NEW_THREAD_TO_NEW_THREAD,
        /**
         * 新线程到密集计算线程
         */
        NEW_THREAD_TO_COMPUTATION,
        /**
         * 新线程到有序单线程
         */
        NEW_THREAD_TO_SINGLE,
        /**
         * 密集计算线程到主线程
         */
        COMPUTATION_TO_MAIN,
        /**
         * 密集计算线程到非密集计算线程到
         */
        COMPUTATION_TO_IO,
        /**
         * 密集计算线程到新线程
         */
        COMPUTATION_TO_NEW_THREAD,
        /**
         * 密集计算线程到密集计算线程到
         */
        COMPUTATION_TO_COMPUTATION,
        /**
         * 密集计算线程到有序单线程
         */
        COMPUTATION_TO_SINGLE,
        /**
         * 有序单线程到主线程
         */
        SINGLE_TO_MAIN,
        /**
         * 有序单线程到非密集计算线程
         */
        SINGLE_TO_IO,
        /**
         * 有序单线程到新线程
         */
        SINGLE_TO_NEW_THREAD,
        /**
         * 有序单线程到密集计算线程
         */
        SINGLE_TO_COMPUTATION,
        /**
         * 有序单线程到有序单线程到
         */
        SINGLE_TO_SINGLE
    }

    /**
     * 线程转换器
     *
     * @param <T>        the type parameter
     * @param threadSign the 线程枚举
     * @return the observable transformer
     */
    public static <T> ObservableTransformer<T, T> dealThread(ThreadSign threadSign) {
        switch (threadSign) {
            case IO_TO_MAIN:
                return setStream(Schedulers.io(), AndroidSchedulers.mainThread());
            case IO_TO_IO:
                return setStream(Schedulers.io(), Schedulers.io());
            case IO_TO_SINGLE:
                return setStream(Schedulers.io(), Schedulers.single());
            case IO_TO_NEW_THREAD:
                return setStream(Schedulers.io(), Schedulers.newThread());
            case IO_TO_COMPUTATION:
                return setStream(Schedulers.io(), Schedulers.computation());
            case NEW_THREAD_TO_MAIN:
                return setStream(Schedulers.newThread(), AndroidSchedulers.mainThread());
            case NEW_THREAD_TO_IO:
                return setStream(Schedulers.newThread(), Schedulers.io());
            case NEW_THREAD_TO_SINGLE:
                return setStream(Schedulers.newThread(), Schedulers.single());
            case NEW_THREAD_TO_NEW_THREAD:
                return setStream(Schedulers.newThread(), Schedulers.newThread());
            case NEW_THREAD_TO_COMPUTATION:
                return setStream(Schedulers.newThread(), Schedulers.computation());
            case COMPUTATION_TO_MAIN:
                return setStream(Schedulers.computation(), AndroidSchedulers.mainThread());
            case COMPUTATION_TO_IO:
                return setStream(Schedulers.computation(), Schedulers.io());
            case COMPUTATION_TO_SINGLE:
                return setStream(Schedulers.computation(), Schedulers.single());
            case COMPUTATION_TO_NEW_THREAD:
                return setStream(Schedulers.computation(), Schedulers.newThread());
            case COMPUTATION_TO_COMPUTATION:
                return setStream(Schedulers.computation(), Schedulers.computation());
            case SINGLE_TO_MAIN:
                return setStream(Schedulers.single(), AndroidSchedulers.mainThread());
            case SINGLE_TO_IO:
                return setStream(Schedulers.single(), Schedulers.io());
            case SINGLE_TO_SINGLE:
                return setStream(Schedulers.single(), Schedulers.single());
            case SINGLE_TO_NEW_THREAD:
                return setStream(Schedulers.single(), Schedulers.newThread());
            case SINGLE_TO_COMPUTATION:
                return setStream(Schedulers.single(), Schedulers.computation());
            default:
                return setStream(AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread());
        }
    }

    /**
     * 设置转换流
     *
     * @param <T>         the type parameter
     * @param subscribeOn the subscribe on
     * @param observeOn   the observe on
     * @return the stream
     */
    private static <T> ObservableTransformer<T, T> setStream(Scheduler subscribeOn, Scheduler observeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn)
                .unsubscribeOn(subscribeOn)
                .observeOn(observeOn);
    }
}
