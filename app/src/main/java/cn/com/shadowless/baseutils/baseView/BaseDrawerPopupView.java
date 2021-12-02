package cn.com.shadowless.baseutils.baseView;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.DrawerPopupView;

/**
 * The type Base drawer popup view.
 */
public  abstract class BaseDrawerPopupView extends DrawerPopupView {
    /**
     * Instantiates a new Base drawer popup view.
     *
     * @param context the context
     */
    public BaseDrawerPopupView(@NonNull Context context) {
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
