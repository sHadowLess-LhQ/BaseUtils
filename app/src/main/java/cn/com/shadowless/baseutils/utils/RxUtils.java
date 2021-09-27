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
 * The type Rx utils.
 *
 * @author sHadowLess
 */
@Builder
public class RxUtils {

    /**
     * The enum Thread sign.
     */
    public enum ThreadSign {
        /**
         * Default thread sign.
         */
        DEFAULT,
        /**
         * Io to main thread sign.
         */
        IO_TO_MAIN,
        /**
         * Io to io thread sign.
         */
        IO_TO_IO,
        /**
         * Io to new thread thread sign.
         */
        IO_TO_NEW_THREAD,
        /**
         * Io to computation thread sign.
         */
        IO_TO_COMPUTATION,
        /**
         * Io to single thread sign.
         */
        IO_TO_SINGLE,
        /**
         * New thread to main thread sign.
         */
        NEW_THREAD_TO_MAIN,
        /**
         * New thread to io thread sign.
         */
        NEW_THREAD_TO_IO,
        /**
         * New thread to new thread thread sign.
         */
        NEW_THREAD_TO_NEW_THREAD,
        /**
         * New thread to computation thread sign.
         */
        NEW_THREAD_TO_COMPUTATION,
        /**
         * New thread to single thread sign.
         */
        NEW_THREAD_TO_SINGLE,
        /**
         * Computation to main thread sign.
         */
        COMPUTATION_TO_MAIN,
        /**
         * Computation to io thread sign.
         */
        COMPUTATION_TO_IO,
        /**
         * Computation to new thread thread sign.
         */
        COMPUTATION_TO_NEW_THREAD,
        /**
         * Computation to computation thread sign.
         */
        COMPUTATION_TO_COMPUTATION,
        /**
         * Computation to single thread sign.
         */
        COMPUTATION_TO_SINGLE,
        /**
         * Single to main thread sign.
         */
        SINGLE_TO_MAIN,
        /**
         * Single to io thread sign.
         */
        SINGLE_TO_IO,
        /**
         * Single to new thread thread sign.
         */
        SINGLE_TO_NEW_THREAD,
        /**
         * Single to computation thread sign.
         */
        SINGLE_TO_COMPUTATION,
        /**
         * Single to single thread sign.
         */
        SINGLE_TO_SINGLE
    }

    /**
     * The interface Observer call back.
     *
     * @param <T> the type parameter
     */
    public interface ObserverCallBack<T> {

        /**
         * The interface Emitter call back.
         *
         * @param <T> the type parameter
         */
        interface EmitterCallBack<T> {
            /**
             * On emitter.
             *
             * @param emitter the emitter
             */
            void onEmitter(ObservableEmitter<T> emitter);
        }

        /**
         * On success.
         *
         * @param t the t
         */
        void onSuccess(T t);

        /**
         * On fail.
         *
         * @param throwable the throwable
         */
        void onFail(Throwable throwable);

        /**
         * On end.
         */
        void onEnd();
    }

    /**
     * Rx create.
     *
     * @param <T>              the type parameter
     * @param threadSign       the thread sign
     * @param emitterCallBack  the emitter call back
     * @param observerCallBack the observer call back
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
     * Rx timer.
     *
     * @param time             the time
     * @param timeUnit         the time unit
     * @param threadSign       the thread sign
     * @param observerCallBack the observer call back
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
     * Rx interval.
     *
     * @param time             the time
     * @param timeUnit         the time unit
     * @param threadSign       the thread sign
     * @param observerCallBack the observer call back
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
     * Io to main observable transformer.
     *
     * @param <T>        the type parameter
     * @param threadSign the thread sign
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
     * Sets stream.
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
