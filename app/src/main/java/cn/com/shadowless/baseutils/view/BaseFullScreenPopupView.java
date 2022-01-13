package cn.com.shadowless.baseutils.view;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.impl.FullScreenPopupView;

/**
 * The type Base full screen popup view.
 */
public abstract class BaseFullScreenPopupView extends FullScreenPopupView {
    /**
     * Instantiates a new Base full screen popup view.
     *
     * @param context the context
     */
    public BaseFullScreenPopupView(@NonNull Context context) {
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
