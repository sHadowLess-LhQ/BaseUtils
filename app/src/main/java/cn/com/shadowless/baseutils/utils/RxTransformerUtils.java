package cn.com.shadowless.baseutils.utils;

import io.reactivex.rxjava3.core.CompletableTransformer;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.MaybeTransformer;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.SingleTransformer;

/**
 * RxJava工具类
 *
 * @author sHadowLess
 */
public class RxTransformerUtils {

    /**
     * 私有构造函数，不允许外部实例化
     */
    private RxTransformerUtils() {
    }


    /**
     * 设置转换流
     *
     * @param <T>         泛型参数
     * @param subscribeOn 订阅调度器
     * @param observeOn   观察调度器
     * @return Completable流
     */
    public static <T> FlowableTransformer<T, T> setFlowableStream(Scheduler subscribeOn, Scheduler observeOn) {
        return setFlowableStream(subscribeOn, subscribeOn, observeOn);
    }

    /**
     * 设置Completable流
     *
     * @param <T>           泛型参数
     * @param subscribeOn   订阅调度器
     * @param unsubscribeOn 取消订阅调度器
     * @param observeOn     观察调度器
     * @return Completable流
     */
    public static <T> FlowableTransformer<T, T> setFlowableStream(Scheduler subscribeOn, Scheduler unsubscribeOn, Scheduler observeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn).unsubscribeOn(unsubscribeOn).observeOn(observeOn);
    }

    /**
     * 设置Observable流（无观察调度器）
     *
     * @param <T>         泛型参数
     * @param subscribeOn 订阅调度器
     * @return Observable流（无观察调度器）
     */
    public static <T> FlowableTransformer<T, T> setFlowableStreamWithoutObserveOn(Scheduler subscribeOn) {
        return setFlowableStreamWithoutObserveOn(subscribeOn, subscribeOn);
    }

    /**
     * 设置Observable流（无观察调度器）
     *
     * @param <T>           泛型参数
     * @param subscribeOn   订阅调度器
     * @param unsubscribeOn 取消订阅调度器
     * @return Observable流（无观察调度器）
     */
    public static <T> FlowableTransformer<T, T> setFlowableStreamWithoutObserveOn(Scheduler subscribeOn, Scheduler unsubscribeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn).unsubscribeOn(unsubscribeOn);
    }

    /**
     * 设置Completable流（无订阅调度器）
     *
     * @param <T>           泛型参数
     * @param unsubscribeOn 取消订阅调度器
     * @param observeOn     观察调度器
     * @return Completable流（无订阅调度器）
     */
    public static <T> FlowableTransformer<T, T> setFlowableStreamWithoutSubscribeOn(Scheduler unsubscribeOn, Scheduler observeOn) {
        return upstream -> upstream.unsubscribeOn(unsubscribeOn).observeOn(observeOn);
    }

    /**
     * 设置Flowable流（取消订阅调度器）
     *
     * @param <T>           泛型参数
     * @param unsubscribeOn 取消订阅调度器
     * @return Flowable流（取消订阅调度器）
     */
    public static <T> FlowableTransformer<T, T> setFlowableStreamUnsubscribeOn(Scheduler unsubscribeOn) {
        return upstream -> upstream.unsubscribeOn(unsubscribeOn);
    }

    /**
     * 设置转换流
     *
     * @param <T>         泛型参数
     * @param subscribeOn 订阅调度器
     * @param observeOn   观察调度器
     * @return Completable流
     */
    public static <T> SingleTransformer<T, T> setSingleStream(Scheduler subscribeOn, Scheduler observeOn) {
        return setSingleStream(subscribeOn, subscribeOn, observeOn);
    }

    /**
     * Sets completable stream.
     *
     * @param <T>           the type parameter
     * @param subscribeOn   the subscribe on
     * @param unsubscribeOn the unsubscribe on
     * @param observeOn     the observe on
     * @return the completable stream
     */
    public static <T> SingleTransformer<T, T> setSingleStream(Scheduler subscribeOn, Scheduler unsubscribeOn, Scheduler observeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn).unsubscribeOn(unsubscribeOn).observeOn(observeOn);
    }

    /**
     * Sets observable stream without observe on.
     *
     * @param <T>         the type parameter
     * @param subscribeOn the subscribe on
     * @return the observable stream without observe on
     */
    public static <T> SingleTransformer<T, T> setSingleStreamWithoutObserveOn(Scheduler subscribeOn) {
        return setSingleStreamWithoutObserveOn(subscribeOn, subscribeOn);
    }

    /**
     * Sets observable stream without observe on.
     *
     * @param <T>           the type parameter
     * @param subscribeOn   the subscribe on
     * @param unsubscribeOn the unsubscribe on
     * @return the observable stream without observe on
     */
    public static <T> SingleTransformer<T, T> setSingleStreamWithoutObserveOn(Scheduler subscribeOn, Scheduler unsubscribeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn).unsubscribeOn(unsubscribeOn);
    }

    /**
     * Sets completable stream without subscribe on.
     *
     * @param <T>           the type parameter
     * @param unsubscribeOn the unsubscribe on
     * @param observeOn     the observe on
     * @return the completable stream without subscribe on
     */
    public static <T> SingleTransformer<T, T> setSingleStreamWithoutSubscribeOn(Scheduler unsubscribeOn, Scheduler observeOn) {
        return upstream -> upstream.unsubscribeOn(unsubscribeOn).observeOn(observeOn);
    }

    /**
     * Sets single stream unsubscribe on.
     *
     * @param <T>           the type parameter
     * @param unsubscribeOn the unsubscribe on
     * @return the single stream unsubscribe on
     */
    public static <T> SingleTransformer<T, T> setSingleStreamUnsubscribeOn(Scheduler unsubscribeOn) {
        return upstream -> upstream.unsubscribeOn(unsubscribeOn);
    }


    /**
     * 设置转换流
     *
     * @param <T>         the type parameter
     * @param subscribeOn the subscribe on
     * @param observeOn   the observe on
     * @return the completable stream
     */
    public static <T> MaybeTransformer<T, T> setMaybeStream(Scheduler subscribeOn, Scheduler observeOn) {
        return setMaybeStream(subscribeOn, subscribeOn, observeOn);
    }

    /**
     * Sets completable stream.
     *
     * @param <T>           the type parameter
     * @param subscribeOn   the subscribe on
     * @param unsubscribeOn the unsubscribe on
     * @param observeOn     the observe on
     * @return the completable stream
     */
    public static <T> MaybeTransformer<T, T> setMaybeStream(Scheduler subscribeOn, Scheduler unsubscribeOn, Scheduler observeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn).unsubscribeOn(unsubscribeOn).observeOn(observeOn);
    }

    /**
     * Sets observable stream without observe on.
     *
     * @param <T>         the type parameter
     * @param subscribeOn the subscribe on
     * @return the observable stream without observe on
     */
    public static <T> MaybeTransformer<T, T> setMaybeStreamWithoutObserveOn(Scheduler subscribeOn) {
        return setMaybeStreamWithoutObserveOn(subscribeOn, subscribeOn);
    }

    /**
     * Sets observable stream without observe on.
     *
     * @param <T>           the type parameter
     * @param subscribeOn   the subscribe on
     * @param unsubscribeOn the unsubscribe on
     * @return the observable stream without observe on
     */
    public static <T> MaybeTransformer<T, T> setMaybeStreamWithoutObserveOn(Scheduler subscribeOn, Scheduler unsubscribeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn).unsubscribeOn(unsubscribeOn);
    }

    /**
     * Sets completable stream without subscribe on.
     *
     * @param <T>           the type parameter
     * @param unsubscribeOn the unsubscribe on
     * @param observeOn     the observe on
     * @return the completable stream without subscribe on
     */
    public static <T> MaybeTransformer<T, T> setMaybeStreamWithoutSubscribeOn(Scheduler unsubscribeOn, Scheduler observeOn) {
        return upstream -> upstream.unsubscribeOn(unsubscribeOn).observeOn(observeOn);
    }

    /**
     * Sets maybe stream unsubscribe on.
     *
     * @param <T>           the type parameter
     * @param unsubscribeOn the unsubscribe on
     * @return the maybe stream unsubscribe on
     */
    public static <T> MaybeTransformer<T, T> setMaybeStreamUnsubscribeOn(Scheduler unsubscribeOn) {
        return upstream -> upstream.unsubscribeOn(unsubscribeOn);
    }


    /**
     * 设置转换流
     *
     * @param subscribeOn the subscribe on
     * @param observeOn   the observe on
     * @return the completable stream
     */
    public static CompletableTransformer setCompletableStream(Scheduler subscribeOn, Scheduler observeOn) {
        return setCompletableStream(subscribeOn, subscribeOn, observeOn);
    }

    /**
     * Sets completable stream.
     *
     * @param subscribeOn   the subscribe on
     * @param unsubscribeOn the unsubscribe on
     * @param observeOn     the observe on
     * @return the completable stream
     */
    public static CompletableTransformer setCompletableStream(Scheduler subscribeOn, Scheduler unsubscribeOn, Scheduler observeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn).unsubscribeOn(unsubscribeOn).observeOn(observeOn);
    }

    /**
     * Sets observable stream without observe on.
     *
     * @param subscribeOn the subscribe on
     * @return the observable stream without observe on
     */
    public static CompletableTransformer setCompletableStreamWithoutObserveOn(Scheduler subscribeOn) {
        return setCompletableStreamWithoutObserveOn(subscribeOn, subscribeOn);
    }

    /**
     * Sets observable stream without observe on.
     *
     * @param subscribeOn   the subscribe on
     * @param unsubscribeOn the unsubscribe on
     * @return the observable stream without observe on
     */
    public static CompletableTransformer setCompletableStreamWithoutObserveOn(Scheduler subscribeOn, Scheduler unsubscribeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn).unsubscribeOn(unsubscribeOn);
    }

    /**
     * Sets completable stream without subscribe on.
     *
     * @param unsubscribeOn the unsubscribe on
     * @param observeOn     the observe on
     * @return the completable stream without subscribe on
     */
    public static CompletableTransformer setCompletableStreamWithoutSubscribeOn(Scheduler unsubscribeOn, Scheduler observeOn) {
        return upstream -> upstream.unsubscribeOn(unsubscribeOn).observeOn(observeOn);
    }

    /**
     * Sets completable stream unsubscribe on.
     *
     * @param unsubscribeOn the unsubscribe on
     * @return the completable stream unsubscribe on
     */
    public static CompletableTransformer setCompletableStreamUnsubscribeOn(Scheduler unsubscribeOn) {
        return upstream -> upstream.unsubscribeOn(unsubscribeOn);
    }


    /**
     * 设置转换流
     *
     * @param <T>         the type parameter
     * @param subscribeOn the subscribe on
     * @param observeOn   the observe on
     * @return the stream
     */
    public static <T> ObservableTransformer<T, T> setObservableStream(Scheduler subscribeOn, Scheduler observeOn) {
        return setObservableStream(subscribeOn, subscribeOn, observeOn);
    }

    /**
     * Sets observable stream.
     *
     * @param <T>           the type parameter
     * @param subscribeOn   the subscribe on
     * @param unsubscribeOn the unsubscribe on
     * @param observeOn     the observe on
     * @return the observable stream
     */
    public static <T> ObservableTransformer<T, T> setObservableStream(Scheduler subscribeOn, Scheduler unsubscribeOn, Scheduler observeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn).unsubscribeOn(unsubscribeOn).observeOn(observeOn);
    }

    /**
     * Sets observable stream without observe on.
     *
     * @param <T>         the type parameter
     * @param subscribeOn the subscribe on
     * @return the observable stream without observe on
     */
    public static <T> ObservableTransformer<T, T> setObservableStreamWithoutObserveOn(Scheduler subscribeOn) {
        return setObservableStreamWithoutObserveOn(subscribeOn, subscribeOn);
    }

    /**
     * Sets observable stream without observe on.
     *
     * @param <T>           the type parameter
     * @param subscribeOn   the subscribe on
     * @param unsubscribeOn the unsubscribe on
     * @return the observable stream without observe on
     */
    public static <T> ObservableTransformer<T, T> setObservableStreamWithoutObserveOn(Scheduler subscribeOn, Scheduler unsubscribeOn) {
        return upstream -> upstream.subscribeOn(subscribeOn).unsubscribeOn(unsubscribeOn);
    }

    /**
     * Sets observable stream without observe on.
     *
     * @param <T>           the type parameter
     * @param unsubscribeOn the unsubscribe on
     * @param observeOn     the observe on
     * @return the observable stream without observe on
     */
    public static <T> ObservableTransformer<T, T> setObservableStreamWithoutSubscribeOn(Scheduler unsubscribeOn, Scheduler observeOn) {
        return upstream -> upstream.unsubscribeOn(unsubscribeOn).observeOn(observeOn);
    }

    /**
     * Sets observable stream unsubscribe on.
     *
     * @param <T>           the type parameter
     * @param unsubscribeOn the unsubscribe on
     * @return the observable stream unsubscribe on
     */
    public static <T> ObservableTransformer<T, T> setObservableStreamUnsubscribeOn(Scheduler unsubscribeOn) {
        return upstream -> upstream.unsubscribeOn(unsubscribeOn);
    }
}
