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
     * @param activity the activity
     */
    public BaseCompletableObserver(Activity activity) {
        loadingPopupView = new XPopup.Builder(activity).asLoading();
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity         the activity
     * @param loadingPopupView the loading popup view
     */
    public BaseCompletableObserver(Activity activity, LoadingPopupView loadingPopupView) {
        this.loadingPopupView = loadingPopupView;
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity    the activity
     * @param isViewModel the is view model
     */
    public BaseCompletableObserver(Activity activity, boolean isViewModel) {
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).asLoading();
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity    the activity
     * @param isViewModel the is view model
     * @param loadName    the load name
     */
    public BaseCompletableObserver(Activity activity, boolean isViewModel, String loadName) {
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
    public BaseCompletableObserver(Activity activity, boolean isViewModel, boolean canBackCancel, String loadName) {
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
    public BaseCompletableObserver(Activity activity, boolean isViewModel, boolean canBackCancel, boolean canOutSideCancel, String loadName) {
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
    public BaseCompletableObserver(Activity activity, boolean isViewModel, boolean canBackCancel, boolean canOutSideCancel, boolean hasBlurBg, String loadName) {
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
    public BaseCompletableObserver(Activity activity, boolean isViewModel, boolean canBackCancel, boolean canOutSideCancel, boolean hasBlurBg, boolean hasShadow, String loadName) {
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
    public BaseCompletableObserver(Activity activity, boolean isViewModel, boolean canBackCancel, boolean canOutSideCancel, boolean hasBlurBg, boolean hasShadow, boolean canCancel, String loadName) {
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
    public BaseCompletableObserver(Activity activity, boolean isViewModel, boolean canBackCancel, boolean canOutSideCancel, boolean hasBlurBg, boolean hasShadow, boolean canCancel, boolean isSmartDismiss, String loadName) {
        this.isSmartDismiss = isSmartDismiss;
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).dismissOnBackPressed(canBackCancel).dismissOnTouchOutside(canOutSideCancel).hasBlurBg(hasBlurBg).hasShadowBg(hasShadow).dismissOnBackPressed(canCancel).dismissOnTouchOutside(canCancel).asLoading(loadName);
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
                onFinish(disposable);
            } else {
                loadingPopupView.dismissWith(() -> onFinish(disposable));
            }
        } else {
            onFinish(disposable);
        }
    }

    /**
     * On success.
     *
     * @param disposable the disposable
     */
    public abstract void onFinish(Disposable disposable);

    /**
     * On fail.
     *
     * @param error the error
     * @param e     the e
     */
    public abstract void onFail(@NonNull String error, Throwable e);
}