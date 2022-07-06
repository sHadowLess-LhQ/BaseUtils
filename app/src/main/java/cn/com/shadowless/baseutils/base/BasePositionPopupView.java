package cn.com.shadowless.baseutils.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewbinding.ViewBinding;


import com.lxj.xpopup.core.PositionPopupView;

import cn.com.shadowless.baseutils.R;

/**
 * The type Base position popup view.
 *
 * @param <T> the type parameter
 * @author sHadowLess
 */
public abstract class BasePositionPopupView<T extends ViewBinding> extends PositionPopupView {

    /**
     * The Bind.
     */
    private T bind = null;
    /**
     * The Context.
     */
    private Context context = null;

    /**
     * Instantiates a new Base position popup view.
     *
     * @param context the context
     */
    public BasePositionPopupView(@NonNull Context context) {
        super(context);
        this.context = context;
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
        if (isDefaultBackground()) {
            getPopupImplView().setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_base_pop_view));
        }
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
     * Is default background boolean.
     *
     * @return the boolean
     */
    protected abstract boolean isDefaultBackground();

    /**
     * Sets bind view.
     *
     * @return the bind view
     */
    @NonNull
    protected abstract T setBindView();
}
