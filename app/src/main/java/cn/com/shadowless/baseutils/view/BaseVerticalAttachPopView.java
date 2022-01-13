package cn.com.shadowless.baseutils.view;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.AttachPopupView;

/**
 * The type Base vertical attach pop view.
 */
public abstract class BaseVerticalAttachPopView extends AttachPopupView {
    /**
     * Instantiates a new Base vertical attach pop view.
     *
     * @param context the context
     */
    public BaseVerticalAttachPopView(@NonNull Context context) {
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
