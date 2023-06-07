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
     * @param loadingPopupView the loading popup view
     */
    public BaseSubscriber(LoadingPopupView loadingPopupView, boolean isSmartDismiss) {
        this.isSmartDismiss = isSmartDismiss;
        this.loadingPopupView = loadingPopupView;
    }

    /**
     * Instantiates a new Base life observer.
     *
     * @param activity the activity
     * @param config   the config
     */
    public BaseSubscriber(Activity activity, LoadingConfig config) {
        this.isSmartDismiss = config.isSmartDismiss();
        loadingPopupView = new XPopup.Builder(activity)
                .isViewMode(config.isViewModel())
                .dismissOnBackPressed(config.isCanBackCancel())
                .dismissOnTouchOutside(config.isCanOutSideCancel())
                .hasBlurBg(config.isHasBlurBg())
                .hasShadowBg(config.isHasShadow())
                .asLoading(config.getLoadName());
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