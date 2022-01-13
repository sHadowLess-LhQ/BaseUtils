package cn.com.shadowless.baseutils.view;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BubbleHorizontalAttachPopupView;

/**
 * The type Base bubble horizontal attach popup view.
 */
public abstract class BaseBubbleHorizontalAttachPopupView extends BubbleHorizontalAttachPopupView {
    /**
     * Instantiates a new Base bubble horizontal attach popup view.
     *
     * @param context the context
     */
    public BaseBubbleHorizontalAttachPopupView(@NonNull Context context) {
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
