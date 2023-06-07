package cn.com.shadowless.baseutils.observer;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import cn.com.shadowless.baseutils.utils.RetrofitUtils;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

/**
 * The type Base observer.
 *
 * @author sHadowLess
 */
public abstract class BaseCompletableObserver implements CompletableObserver {

    /**
     * The Disposable.
     */
    private Disposable disposable = null;
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
    public BaseCompletableObserver() {
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param loadingPopupView the loading popup view
     */
    public BaseCompletableObserver(LoadingPopupView loadingPopupView, boolean isSmartDismiss) {
        this.isSmartDismiss = isSmartDismiss;
        this.loadingPopupView = loadingPopupView;
    }

    /**
     * Instantiates a new Base life observer.
     *
     * @param activity the activity
     * @param config   the config
     */
    public BaseCompletableObserver(Activity activity, LoadingConfig config) {
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
        disposable = d;
        if (loadingPopupView != null) {
            loadingPopupView.show();
        }
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
                loadingPopupView.dismissWith(this::onFinish);
            }
        } else {
            onFinish();
        }
        disposable.dispose();
    }

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