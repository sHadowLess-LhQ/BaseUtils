package cn.com.shadowless.baseutils.baseView;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.PositionPopupView;

/**
 * The type Base position popup view.
 */
public abstract class BasePositionPopupView extends PositionPopupView {
    /**
     * Instantiates a new Base position popup view.
     *
     * @param context the context
     */
    public BasePositionPopupView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return setLayout();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
    }

    @Override
    protected void onShow() {
        super.onShow();
        initListener();
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
}
