package cn.com.shadowless.baseutils.baseView;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.HorizontalAttachPopupView;

/**
 * The type Base horizontal attach pop view.
 */
public abstract class BaseHorizontalAttachPopView extends HorizontalAttachPopupView {
    /**
     * Instantiates a new Base horizontal attach pop view.
     *
     * @param context the context
     */
    public BaseHorizontalAttachPopView(@NonNull Context context) {
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
