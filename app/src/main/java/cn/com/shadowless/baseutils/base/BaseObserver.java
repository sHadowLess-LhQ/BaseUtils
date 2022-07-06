package cn.com.shadowless.baseutils.base;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import cn.com.shadowless.baseutils.utils.NetUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * The type Base observer.
 *
 * @param <T> the type parameter
 * @author sHadowLess
 */
public abstract class BaseObserver<T> implements Observer<T> {

    /**
     * The Disposable.
     */
    private Disposable disposable = null;
    /**
     * The Loading popup view.
     */
    private LoadingPopupView loadingPopupView = null;

    /**
     * Instantiates a new Base observer.
     */
    public BaseObserver() {
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity the activity
     */
    public BaseObserver(Activity activity) {
        loadingPopupView = new XPopup.Builder(activity).asLoading();
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity    the activity
     * @param isViewModel the is view model
     */
    public BaseObserver(Activity activity, boolean isViewModel) {
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).asLoading();
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity    the activity
     * @param isViewModel the is view model
     * @param loadName    the load name
     */
    public BaseObserver(Activity activity, boolean isViewModel, String loadName) {
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).asLoading(loadName);
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity    the activity
     * @param isViewModel the is view model
     * @param hasBlurBg   the has blur bg
     * @param loadName    the load name
     */
    public BaseObserver(Activity activity, boolean isViewModel, boolean hasBlurBg, String loadName) {
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).hasBlurBg(hasBlurBg).asLoading(loadName);
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity    the activity
     * @param isViewModel the is view model
     * @param hasBlurBg   the has blur bg
     * @param hasShadow   the has shadow
     * @param loadName    the load name
     */
    public BaseObserver(Activity activity, boolean isViewModel, boolean hasBlurBg, boolean hasShadow, String loadName) {
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).hasBlurBg(hasBlurBg).hasShadowBg(hasShadow).asLoading(loadName);
    }

    /**
     * Instantiates a new Base observer.
     *
     * @param activity    the activity
     * @param isViewModel the is view model
     * @param hasBlurBg   the has blur bg
     * @param hasShadow   the has shadow
     * @param canCancel   the can cancel
     * @param loadName    the load name
     */
    public BaseObserver(Activity activity, boolean isViewModel, boolean hasBlurBg, boolean hasShadow, boolean canCancel, String loadName) {
        loadingPopupView = new XPopup.Builder(activity).isViewMode(isViewModel).hasBlurBg(hasBlurBg).hasShadowBg(hasShadow).dismissOnBackPressed(canCancel).dismissOnTouchOutside(canCancel).asLoading(loadName);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        disposable = d;
        if (loadingPopupView != null) {
            loadingPopupView.show();
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        if (loadingPopupView != null) {
            loadingPopupView.dismissWith(() -> onSuccess(t));
        } else {
            onSuccess(t);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (loadingPopupView != null) {
            loadingPopupView.dismissWith(() -> onFail(NetUtils.getExceptionMessage(e), e));
        } else {
            onFail(NetUtils.getExceptionMessage(e), e);
        }
    }

    @Override
    public void onComplete() {
        disposable.dispose();
    }

    /**
     * On success.
     *
     * @param t the t
     */
    public abstract void onSuccess(@NonNull T t);

    /**
     * On fail.
     *
     * @param error the error
     * @param e     the e
     */
    public abstract void onFail(@NonNull String error, Throwable e);
}