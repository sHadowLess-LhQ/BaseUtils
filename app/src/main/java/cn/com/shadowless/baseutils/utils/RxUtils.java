package cn.com.shadowless.baseutils.utils;


import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.SingleTransformer;
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
     * 默认
     */
    public final static int DEFAULT = 0;
    /**
     * 非密集计算线程到主线程
     */
    public final static int IO_TO_MAIN = 1;
    /**
     * 非密集计算线程到非密集计算线程
     */
    public final static int IO_TO_IO = 2;
    /**
     * 非密集计算线程到新线程
     */
    public final static int IO_TO_NEW_THREAD = 3;
    /**
     * 非密集计算线程到密集计算线程
     */
    public final static int IO_TO_COMPUTATION = 4;
    /**
     * 非密集计算线程到有序单线程
     */
    public final static int IO_TO_SINGLE = 5;
    /**
     * 新线程到主线程
     */
    public final static int NEW_THREAD_TO_MAIN = 6;
    /**
     * 新线程到非密集计算线程
     */
    public final static int NEW_THREAD_TO_IO = 7;
    /**
     * 新线程到新线程
     */
    public final static int NEW_THREAD_TO_NEW_THREAD = 8;
    /**
     * 新线程到密集计算线程
     */
    public final static int NEW_THREAD_TO_COMPUTATION = 9;
    /**
     * 新线程到有序单线程
     */
    public final static int NEW_THREAD_TO_SINGLE = 10;
    /**
     * 密集计算线程到主线程
     */
    public final static int COMPUTATION_TO_MAIN = 11;
    /**
     * 密集计算线程到非密集计算线程到
     */
    public final static int COMPUTATION_TO_IO = 12;
    /**
     * 密集计算线程到新线程
     */
    public final static int COMPUTATION_TO_NEW_THREAD = 13;
    /**
     * 密集计算线程到密集计算线程到
     */
    public final static int COMPUTATION_TO_COMPUTATION = 14;
    /**
     * 密集计算线程到有序单线程
     */
    public final static int COMPUTATION_TO_SINGLE = 15;
    /**
     * 有序单线程到主线程
     */
    public final static int SINGLE_TO_MAIN = 16;
    /**
     * 有序单线程到非密集计算线程
     */
    public final static int SINGLE_TO_IO = 17;
    /**
     * 有序单线程到新线程
     */
    public final static int SINGLE_TO_NEW_THREAD = 18;
    /**
     * 有序单线程到密集计算线程
     */
    public final static int SINGLE_TO_COMPUTATION = 19;
    /**
     * 有序单线程到有序单线程到
     */
    public final static int SINGLE_TO_SINGLE = 20;

    /**
     * 线程转换器
     *
     * @param <T>        the type parameter
     * @param threadSign the 线程枚举
     * @return the observable transformer
     */
    public static <T> ObservableTransformer<T, T> dealObservableThread(int threadSign) {
        switch (threadSign) {
            case IO_TO_MAIN:
                return setObservableStream(Schedulers.io(), AndroidSchedulers.mainThread());
            case IO_TO_IO:
                return setObservableStream(Schedulers.io(), Schedulers.io());
            case IO_TO_SINGLE:
                return setObservableStream(Schedulers.io(), Schedulers.single());
            case IO_TO_NEW_THREAD:
                return setObservableStream(Schedulers.io(), Schedulers.newThread());
            case IO_TO_COMPUTATION:
                return setObservableStream(Schedulers.io(), Schedulers.computation());
            case NEW_THREAD_TO_MAIN:
                return setObservableStream(Schedulers.newThread(), AndroidSchedulers.mainThread());
            case NEW_THREAD_TO_IO:
                return setObservableStream(Schedulers.newThread(), Schedulers.io());
            case NEW_THREAD_TO_SINGLE:
                return setObservableStream(Schedulers.newThread(), Schedulers.single());
            case NEW_THREAD_TO_NEW_THREAD:
                return setObservableStream(Schedulers.newThread(), Schedulers.newThread());
            case NEW_THREAD_TO_COMPUTATION:
                return setObservableStream(Schedulers.newThread(), Schedulers.computation());
            case COMPUTATION_TO_MAIN:
                return setObservableStream(Schedulers.computation(), AndroidSchedulers.mainThread());
            case COMPUTATION_TO_IO:
                return setObservableStream(Schedulers.computation(), Schedulers.io());
            case COMPUTATION_TO_SINGLE:
                return setObservableStream(Schedulers.computation(), Schedulers.single());
            case COMPUTATION_TO_NEW_THREAD:
                return setObservableStream(Schedulers.computation(), Schedulers.newThread());
            case COMPUTATION_TO_COMPUTATION:
                return setObservableStream(Schedulers.computation(), Schedulers.computation());
            case SINGLE_TO_MAIN:
                return setObservableStream(Schedulers.single(), AndroidSchedulers.mainThread());
            case SINGLE_TO_IO:
                return setObservableStream(Schedulers.single(), Schedulers.io());
            case SINGLE_TO_SINGLE:
                return setObservableStream(Schedulers.single(), Schedulers.single());
            case SINGLE_TO_NEW_THREAD:
                return setObservableStream(Schedulers.single(), Schedulers.newThread());
            case SINGLE_TO_COMPUTATION:
                return setObservableStream(Schedulers.single(), Schedulers.computation());
            default:
                return setObservableStream(AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread());
        }
    }

    /**
     * 线程转换器
     *
     * @param threadSign the 线程枚举
     * @return the observable transformer
     */
    public static CompletableTransformer dealCompletableThread(int threadSign) {
        switch (threadSign) {
            case IO_TO_MAIN:
                return setCompletableStream(Schedulers.io(), AndroidSchedulers.mainThread());
            case IO_TO_IO:
                return setCompletableStream(Schedulers.io(), Schedulers.io());
            case IO_TO_SINGLE:
                return setCompletableStream(Schedulers.io(), Schedulers.single());
            case IO_TO_NEW_THREAD:
                return setCompletableStream(Schedulers.io(), Schedulers.newThread());
            case IO_TO_COMPUTATION:
                return setCompletableStream(Schedulers.io(), Schedulers.computation());
            case NEW_THREAD_TO_MAIN:
                return setCompletableStream(Schedulers.newThread(), AndroidSchedulers.mainThread());
            case NEW_THREAD_TO_IO:
                return setCompletableStream(Schedulers.newThread(), Schedulers.io());
            case NEW_THREAD_TO_SINGLE:
                return setCompletableStream(Schedulers.newThread(), Schedulers.single());
            case NEW_THREAD_TO_NEW_THREAD:
                return setCompletableStream(Schedulers.newThread(), Schedulers.newThread());
            case NEW_THREAD_TO_COMPUTATION:
                return setCompletableStream(Schedulers.newThread(), Schedulers.computation());
            case COMPUTATION_TO_MAIN:
                return setCompletableStream(Schedulers.computation(), AndroidSchedulers.mainThread());
            case COMPUTATION_TO_IO:
                return setCompletableStream(Schedulers.computation(), Schedulers.io());
            case COMPUTATION_TO_SINGLE:
                return setCompletableStream(Schedulers.computation(), Schedulers.single());
            case COMPUTATION_TO_NEW_THREAD:
                return setCompletableStream(Schedulers.computation(), Schedulers.newThread());
            case COMPUTATION_TO_COMPUTATION:
                return setCompletableStream(Schedulers.computation(), Schedulers.computation());
            case SINGLE_TO_MAIN:
                return setCompletableStream(Schedulers.single(), AndroidSchedulers.mainThread());
            case SINGLE_TO_IO:
                return setCompletableStream(Schedulers.single(), Schedulers.io());
            case SINGLE_TO_SINGLE:
                return setCompletableStream(Schedulers.single(), Schedulers.single());
            case SINGLE_TO_NEW_THREAD:
                return setCompletableStream(Schedulers.single(), Schedulers.newThread());
            case SINGLE_TO_COMPUTATION:
                return setCompletableStream(Schedulers.single(), Schedulers.computation());
            default:
                return setCompletableStream(AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread());
        }
    }

    /**
     * 线程转换器
     *
     * @param <T>        the type parameter
     * @param threadSign the 线程枚举
     * @return the observable transformer
     */
    public static <T> MaybeTransformer<T, T> dealMaybeThread(int threadSign) {
        switch (threadSign) {
            case IO_TO_MAIN:
                return setMaybeStream(Schedulers.io(), AndroidSchedulers.mainThread());
            case IO_TO_IO:
                return setMaybeStream(Schedulers.io(), Schedulers.io());
            case IO_TO_SINGLE:
                return setMaybeStream(Schedulers.io(), Schedulers.single());
            case IO_TO_NEW_THREAD:
                return setMaybeStream(Schedulers.io(), Schedulers.newThread());
            case IO_TO_COMPUTATION:
                return setMaybeStream(Schedulers.io(), Schedulers.computation());
            case NEW_THREAD_TO_MAIN:
                return setMaybeStream(Schedulers.newThread(), AndroidSchedulers.mainThread());
            case NEW_THREAD_TO_IO:
                return setMaybeStream(Schedulers.newThread(), Schedulers.io());
            case NEW_THREAD_TO_SINGLE:
                return setMaybeStream(Schedulers.newThread(), Schedulers.single());
            case NEW_THREAD_TO_NEW_THREAD:
                return setMaybeStream(Schedulers.newThread(), Schedulers.newThread());
            case NEW_THREAD_TO_COMPUTATION:
                return setMaybeStream(Schedulers.newThread(), Schedulers.computation());
            case COMPUTATION_TO_MAIN:
                return setMaybeStream(Schedulers.computation(), AndroidSchedulers.mainThread());
            case COMPUTATION_TO_IO:
                return setMaybeStream(Schedulers.computation(), Schedulers.io());
            case COMPUTATION_TO_SINGLE:
                return setMaybeStream(Schedulers.computation(), Schedulers.single());
            case COMPUTATION_TO_NEW_THREAD:
                return setMaybeStream(Schedulers.computation(), Schedulers.newThread());
            case COMPUTATION_TO_COMPUTATION:
                return setMaybeStream(Schedulers.computation(), Schedulers.computation());
            case SINGLE_TO_MAIN:
                return setMaybeStream(Schedulers.single(), AndroidSchedulers.mainThread());
            case SINGLE_TO_IO:
                return setMaybeStream(Schedulers.single(), Schedulers.io());
            case SINGLE_TO_SINGLE:
                return setMaybeStream(Schedulers.single(), Schedulers.single());
            case SINGLE_TO_NEW_THREAD:
                return setMaybeStream(Schedulers.single(), Schedulers.newThread());
            case SINGLE_TO_COMPUTATION:
                return setMaybeStream(Schedulers.single(), Schedulers.computation());
            default:
                return setMaybeStream(AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread());
        }
    }

    /**
     * 线程转换器
     *
     * @param <T>        the type parameter
     * @param threadSign the 线程枚举
     * @return the observable transformer
     */
    public static <T> SingleTransformer<T, T> dealSingleThread(int threadSign) {
        switch (threadSign) {
            case IO_TO_MAIN:
                return setSingleStream(Schedulers.io(), AndroidSchedulers.mainThread());
            case IO_TO_IO:
                return setSingleStream(Schedulers.io(), Schedulers.io());
            case IO_TO_SINGLE:
                return setSingleStream(Schedulers.io(), Schedulers.single());
            case IO_TO_NEW_THREAD:
                return setSingleStream(Schedulers.io(), Schedulers.newThread());
            case IO_TO_COMPUTATION:
                return setSingleStream(Schedulers.io(), Schedulers.computation());
            case NEW_THREAD_TO_MAIN:
                return setSingleStream(Schedulers.newThread(), AndroidSchedulers.mainThread());
            case NEW_THREAD_TO_IO:
                return setSingleStream(Schedulers.newThread(), Schedulers.io());
            case NEW_THREAD_TO_SINGLE:
                return setSingleStream(Schedulers.newThread(), Schedulers.single());
            case NEW_THREAD_TO_NEW_THREAD:
                return setSingleStream(Schedulers.newThread(), Schedulers.newThread());
            case NEW_THREAD_TO_COMPUTATION:
                return setSingleStream(Schedulers.newThread(), Schedulers.computation());
            case COMPUTATION_TO_MAIN:
                return setSingleStream(Schedulers.computation(), AndroidSchedulers.mainThread());
            case COMPUTATION_TO_IO:
                return setSingleStream(Schedulers.computation(), Schedulers.io());
            case COMPUTATION_TO_SINGLE:
                return setSingleStream(Schedulers.computation(), Schedulers.single());
            case COMPUTATION_TO_NEW_THREAD:
                return setSingleStream(Schedulers.computation(), Schedulers.newThread());
            case COMPUTATION_TO_COMPUTATION:
                return setSingleStream(Schedulers.computation(), Schedulers.computation());
            case SINGLE_TO_MAIN:
                return setSingleStream(Schedulers.single(), AndroidSchedulers.mainThread());
            case SINGLE_TO_IO:
                return setSingleStream(Schedulers.single(), Schedulers.io());
            case SINGLE_TO_SINGLE:
                return setSingleStream(Schedulers.single(), Schedulers.single());
            case SINGLE_TO_NEW_THREAD:
                return setSingleStream(Schedulers.single(), Schedulers.newThread());
            case SINGLE_TO_COMPUTATION:
                return setSingleStream(Schedulers.single(), Schedulers.computation());
            default:
                return setSingleStream(AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread());
        }
    }

    /**
     * 线程转换器
     *
     * @param <T>        the type parameter
     * @param threadSign the 线程枚举
     * @return the observable transformer
     */
    public static <T> FlowableTransformer<T, T> dealFlowableThread(int threadSign) {
        switch (threadSign) {
            case IO_TO_MAIN:
                return setFlowableStream(Schedulers.io(), AndroidSchedulers.mainThread());
            case IO_TO_IO:
                return setFlowableStream(Schedulers.io(), Schedulers.io());
            case IO_TO_SINGLE:
                return setFlowableStream(Schedulers.io(), Schedulers.single());
            case IO_TO_NEW_THREAD:
                return setFlowableStream(Schedulers.io(), Schedulers.newThread());
            case IO_TO_COMPUTATION:
                return setFlowableStream(Schedulers.io(), Schedulers.computation());
            case NEW_THREAD_TO_MAIN:
                return setFlowableStream(Schedulers.newThread(), AndroidSchedulers.mainThread());
            case NEW_THREAD_TO_IO:
                return setFlowableStream(Schedulers.newThread(), Schedulers.io());
            case NEW_THREAD_TO_SINGLE:
                return setFlowableStream(Schedulers.newThread(), Schedulers.single());
            case NEW_THREAD_TO_NEW_THREAD:
                return setFlowableStream(Schedulers.newThread(), Schedulers.newThread());
            case NEW_THREAD_TO_COMPUTATION:
                return setFlowableStream(Schedulers.newThread(), Schedulers.computation());
            case COMPUTATION_TO_MAIN:
                return setFlowableStream(Schedulers.computation(), AndroidSchedulers.mainThread());
            case COMPUTATION_TO_IO:
                return setFlowableStream(Schedulers.computation(), Schedulers.io());
            case COMPUTATION_TO_SINGLE:
                return setFlowableStream(Schedulers.computation(), Schedulers.single());
            case COMPUTATION_TO_NEW_THREAD:
                return setFlowableStream(Schedulers.computation(), Schedulers.newThread());
            case COMPUTATION_TO_COMPUTATION:
                return setFlowableStream(Schedulers.computation(), Schedulers.computation());
            case SINGLE_TO_MAIN:
                return setFlowableStream(Schedulers.single(), AndroidSchedulers.mainThread());
            case SINGLE_TO_IO:
                return setFlowableStream(Schedulers.single(), Schedulers.io());
            case SINGLE_TO_SINGLE:
                return setFlowableStream(Schedulers.single(), Schedulers.single());
            case SINGLE_TO_NEW_THREAD:
                return setFlowableStream(Schedulers.single(), Schedulers.newThread());
            case SINGLE_TO_COMPUTATION:
                return setFlowableStream(Schedulers.single(), Schedulers.computation());
            default:
                return setFlowableStream(AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread());
        }
    }

    /**
     * 设置转换流
     *
     * @param <T>         the type parameter
     * @param subscribeOn the subscribe on
     * @param observeOn   the observe on
     * @return the flowable stream
     */
    private static <T> FlowableTransformer<T, T> setFlowableStream(Scheduler subscribeOn, Scheduler observeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn)
                .unsubscribeOn(subscribeOn)
                .observeOn(observeOn);
    }

    /**
     * 设置转换流
     *
     * @param <T>         the type parameter
     * @param subscribeOn the subscribe on
     * @param observeOn   the observe on
     * @return the single stream
     */
    private static <T> SingleTransformer<T, T> setSingleStream(Scheduler subscribeOn, Scheduler observeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn)
                .unsubscribeOn(subscribeOn)
                .observeOn(observeOn);
    }

    /**
     * 设置转换流
     *
     * @param <T>         the type parameter
     * @param subscribeOn the subscribe on
     * @param observeOn   the observe on
     * @return the maybe stream
     */
    private static <T> MaybeTransformer<T, T> setMaybeStream(Scheduler subscribeOn, Scheduler observeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn)
                .unsubscribeOn(subscribeOn)
                .observeOn(observeOn);
    }

    /**
     * 设置转换流
     *
     * @param subscribeOn the subscribe on
     * @param observeOn   the observe on
     * @return the completable stream
     */
    private static CompletableTransformer setCompletableStream(Scheduler subscribeOn, Scheduler observeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn)
                .unsubscribeOn(subscribeOn)
                .observeOn(observeOn);
    }

    /**
     * 设置转换流
     *
     * @param <T>         the type parameter
     * @param subscribeOn the subscribe on
     * @param observeOn   the observe on
     * @return the stream
     */
    private static <T> ObservableTransformer<T, T> setObservableStream(Scheduler subscribeOn, Scheduler observeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn)
                .unsubscribeOn(subscribeOn)
                .observeOn(observeOn);
    }
}
