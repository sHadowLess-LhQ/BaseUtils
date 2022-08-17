package cn.com.shadowless.baseutils.observer;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import cn.com.shadowless.baseutils.utils.RetrofitUtils;

/**
 * The type Base observer.
 *
 * @param <T> the type parameter
 * @author sHadowLess
 */
public abstract class BaseSubscriber<T> implements Subscriber<T> {

    /**
     * The Subscription.
     */
    private Subscription subscription = null;

    /**
     * The Loading popup view.
     */
    private LoadingPopupView loadingPopupView = null;

    /**
     * The Is smart dismiss.
     */
    private boolean isSmartDismiss = false;

    /**
     * Instantiates a new Base observer.
     */
    public BaseSubscriber() {
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity the activity
     */
    public BaseSubscriber(Activity activity) {
        loadingPopupView = new XPopup.Builder(activity).asLoading();
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity         the activity
     * @param loadingPopupView the loading popup view
     */
    public BaseSubscriber(Activity activity, LoadingPopupView loadingPopupView) {
        this.loadingPopupView = loadingPopupView;
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity    the activity
     * @param isViewModel the is view model
     */
    public BaseSubscriber(Activity activity, boolean isViewModel) {
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).asLoading();
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity    the activity
     * @param isViewModel the is view model
     * @param loadName    the load name
     */
    public BaseSubscriber(Activity activity, boolean isViewModel, String loadName) {
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).asLoading(loadName);
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity      the activity
     * @param isViewModel   the is view model
     * @param canBackCancel the can back cancel
     * @param loadName      the load name
     */
    public BaseSubscriber(Activity activity, boolean isViewModel, boolean canBackCancel, String loadName) {
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).dismissOnBackPressed(canBackCancel).asLoading(loadName);
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity         the activity
     * @param isViewModel      the is view model
     * @param canBackCancel    the can back cancel
     * @param canOutSideCancel the can out side cancel
     * @param loadName         the load name
     */
    public BaseSubscriber(Activity activity, boolean isViewModel, boolean canBackCancel, boolean canOutSideCancel, String loadName) {
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).dismissOnBackPressed(canBackCancel).dismissOnTouchOutside(canOutSideCancel).asLoading(loadName);
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity         the activity
     * @param isViewModel      the is view model
     * @param canBackCancel    the can back cancel
     * @param canOutSideCancel the can out side cancel
     * @param hasBlurBg        the has blur bg
     * @param loadName         the load name
     */
    public BaseSubscriber(Activity activity, boolean isViewModel, boolean canBackCancel, boolean canOutSideCancel, boolean hasBlurBg, String loadName) {
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).dismissOnBackPressed(canBackCancel).dismissOnTouchOutside(canOutSideCancel).hasBlurBg(hasBlurBg).asLoading(loadName);
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity         the activity
     * @param isViewModel      the is view model
     * @param canBackCancel    the can back cancel
     * @param canOutSideCancel the can out side cancel
     * @param hasBlurBg        the has blur bg
     * @param hasShadow        the has shadow
     * @param loadName         the load name
     */
    public BaseSubscriber(Activity activity, boolean isViewModel, boolean canBackCancel, boolean canOutSideCancel, boolean hasBlurBg, boolean hasShadow, String loadName) {
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).dismissOnBackPressed(canBackCancel).dismissOnTouchOutside(canOutSideCancel).hasBlurBg(hasBlurBg).hasShadowBg(hasShadow).asLoading(loadName);
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity         the activity
     * @param isViewModel      the is view model
     * @param canBackCancel    the can back cancel
     * @param canOutSideCancel the can out side cancel
     * @param hasBlurBg        the has blur bg
     * @param hasShadow        the has shadow
     * @param canCancel        the can cancel
     * @param loadName         the load name
     */
    public BaseSubscriber(Activity activity, boolean isViewModel, boolean canBackCancel, boolean canOutSideCancel, boolean hasBlurBg, boolean hasShadow, boolean canCancel, String loadName) {
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).dismissOnBackPressed(canBackCancel).dismissOnTouchOutside(canOutSideCancel).hasBlurBg(hasBlurBg).hasShadowBg(hasShadow).dismissOnBackPressed(canCancel).dismissOnTouchOutside(canCancel).asLoading(loadName);
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity         the activity
     * @param isViewModel      the is view model
     * @param canBackCancel    the can back cancel
     * @param canOutSideCancel the can out side cancel
     * @param hasBlurBg        the has blur bg
     * @param hasShadow        the has shadow
     * @param canCancel        the can cancel
     * @param loadName         the load name
     */
    public BaseSubscriber(Activity activity, boolean isViewModel, boolean canBackCancel, boolean canOutSideCancel, boolean hasBlurBg, boolean hasShadow, boolean canCancel, boolean isSmartDismiss, String loadName) {
        this.isSmartDismiss = isSmartDismiss;
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).dismissOnBackPressed(canBackCancel).dismissOnTouchOutside(canOutSideCancel).hasBlurBg(hasBlurBg).hasShadowBg(hasShadow).dismissOnBackPressed(canCancel).dismissOnTouchOutside(canCancel).asLoading(loadName);
    }

    @Override
    public void onSubscribe(Subscription s) {
        subscription = s;
        if (loadingPopupView != null) {
            loadingPopupView.show();
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (loadingPopupView != null) {
            if (isSmartDismiss) {
                loadingPopupView.smartDismiss();
                onFail(RetrofitUtils.getExceptionMessage(e), e);
            } else {
                loadingPopupView.dismissWith(() -> onFail(RetrofitUtils.getExceptionMessage(e), e));
            }
        } else {
            onFail(RetrofitUtils.getExceptionMessage(e), e);
        }
    }

    @Override
    public void onComplete() {
        if (loadingPopupView != null) {
            if (isSmartDismiss) {
                loadingPopupView.smartDismiss();
                onFinish(subscription);
            } else {
                loadingPopupView.dismissWith(() -> onFinish(subscription));
            }
        } else {
            onFinish(subscription);
        }
    }

    /**
     * On success.
     *
     * @param t the t
     */
    public abstract void onSuccess(@NonNull T t);

    /**
     * On finish.
     *
     * @param subscription the subscription
     */
    public abstract void onFinish(Subscription subscription);

    /**
     * On fail.
     *
     * @param error the error
     * @param e     the e
     */
    public abstract void onFail(@NonNull String error, Throwable e);
}