package cn.com.shadowless.baseutils.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Builder;

/**
 * RxJava2工具类
 *
 * @author sHadowLess
 */
@Builder
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
     * 观察者回调
     *
     * @param <T> the type parameter
     */
    public interface ObserverCallBack<T> {

        /**
         * 发射器回调
         *
         * @param <T> the type parameter
         */
        interface EmitterCallBack<T> {
            /**
             * On 发射器.
             *
             * @param emitter the emitter
             */
            void onEmitter(ObservableEmitter<T> emitter);
        }

        /**
         * 成功
         *
         * @param t the 泛型数据
         */
        void onSuccess(T t);

        /**
         * 失败
         *
         * @param throwable the 异常
         */
        void onFail(Throwable throwable);

        /**
         * 结束
         */
        void onEnd();
    }

    /**
     * Create操作符
     *
     * @param <T>              the type parameter
     * @param threadSign       the 线程枚举
     * @param emitterCallBack  the 发射器回调
     * @param observerCallBack the 观察者回调
     */
    public <T> void rxCreate(ThreadSign threadSign, ObserverCallBack.EmitterCallBack<T> emitterCallBack, ObserverCallBack<T> observerCallBack) {
        Observable
                .create(emitterCallBack::onEmitter)
                .compose(dealThread(threadSign))
                .subscribe(new Observer<T>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(T t) {
                        observerCallBack.onSuccess(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observerCallBack.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                        observerCallBack.onEnd();
                        disposable.dispose();
                    }
                });
    }

    /**
     * timer操作符
     *
     * @param time             the 计时时间
     * @param timeUnit         the 时间单位
     * @param threadSign       the 线程枚举
     * @param observerCallBack the 观察者回调
     */
    public void rxTimer(long time, TimeUnit timeUnit, ThreadSign threadSign, ObserverCallBack<Long> observerCallBack) {
        Observable
                .timer(time, timeUnit)
                .compose(dealThread(threadSign))
                .subscribe(new Observer<Long>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        observerCallBack.onSuccess(aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observerCallBack.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                        observerCallBack.onEnd();
                        disposable.dispose();
                    }
                });
    }

    /**
     * interval操作符
     *
     * @param time             the 循环时间
     * @param timeUnit         the 时间单位
     * @param threadSign       the 线程枚举
     * @param observerCallBack the 观察者回调
     */
    public void rxInterval(long time, TimeUnit timeUnit, ThreadSign threadSign, ObserverCallBack<Long> observerCallBack) {
        Observable
                .interval(time, timeUnit)
                .compose(dealThread(threadSign))
                .subscribe(new Observer<Long>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        observerCallBack.onSuccess(aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observerCallBack.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                        observerCallBack.onEnd();
                        disposable.dispose();
                    }
                });
    }

    /**
     * 线程转换器
     *
     * @param <T>        the type parameter
     * @param threadSign the 线程枚举
     * @return the observable transformer
     */
    private <T> ObservableTransformer<T, T> dealThread(ThreadSign threadSign) {
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
    private <T> ObservableTransformer<T, T> setStream(Scheduler subscribeOn, Scheduler observeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn)
                .unsubscribeOn(subscribeOn)
                .observeOn(observeOn);
    }
}
