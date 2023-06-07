package cn.com.shadowless.baseutils.observer;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import cn.com.shadowless.baseutils.utils.RetrofitUtils;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

/**
 * The type Base observer.
 *
 * @param <T> the type parameter
 * @author sHadowLess
 */
public abstract class BaseLifeMaybeObserver<T> implements MaybeObserver<T> {

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
    public BaseLifeMaybeObserver() {
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param loadingPopupView the loading popup view
     */
    public BaseLifeMaybeObserver(LoadingPopupView loadingPopupView, boolean isSmartDismiss) {
        this.isSmartDismiss = isSmartDismiss;
        this.loadingPopupView = loadingPopupView;
    }

    /**
     * Instantiates a new Base life observer.
     *
     * @param activity the activity
     * @param config   the config
     */
    public BaseLifeMaybeObserver(Activity activity, LoadingConfig config) {
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
    public void onSubscribe(@NonNull Disposable d) {
        if (loadingPopupView != null) {
            loadingPopupView.show();
        }
    }

    @Override
    public void onSuccess(@NonNull T t) {
        onNext(t);
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
                onFinish();
            } else {
                loadingPopupView.dismiss();
            }
        }
    }

    /**
     * On success.
     *
     * @param t the t
     */
    public abstract void onNext(@NonNull T t);

    /**
     * On success.
     */
    public abstract void onFinish();

    /**
     * On fail.
     *
     * @param error the error
     * @param e     the e
     */
    public abstract void onFail(@NonNull String error, Throwable e);
}