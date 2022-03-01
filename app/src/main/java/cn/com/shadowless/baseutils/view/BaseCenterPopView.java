package cn.com.shadowless.baseutils.view;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.lxj.xpopup.core.CenterPopupView;

/**
 * The type Base center pop view.
 *
 * @param <T> the type parameter
 * @author sHadowLess
 */
public abstract class BaseCenterPopView<T extends ViewBinding> extends CenterPopupView {

    /**
     * The Bind.
     */
    private T bind = null;

    /**
     * Instantiates a new Base center pop view.
     *
     * @param context the context
     */
    public BaseCenterPopView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return setLayout();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        bind = setBindView();
        initView();
    }

    @Override
    protected void onShow() {
        super.onShow();
        initListener();
    }

    /**
     * Gets bind view.
     *
     * @return the bind view
     */
    protected T getBindView() {
        return bind;
    }

    /**
     * Sets layout.
     *
     * @return the layout
     */
    protected abstract int setLayout();

    /**
     * Init view.
     */
    protected abstract void initView();

    /**
     * Init listener.
     */
    protected abstract void initListener();

    /**
     * Sets bind view.
     *
     * @return the bind view
     */
    @NonNull
    protected abstract T setBindView();
}
