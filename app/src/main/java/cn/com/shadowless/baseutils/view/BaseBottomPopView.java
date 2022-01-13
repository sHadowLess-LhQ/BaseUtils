package cn.com.shadowless.baseutils.view;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;

/**
 * The type Base bottom pop view.
 *
 * @author sHadowLess
 */
public abstract class BaseBottomPopView extends BottomPopupView {
    /**
     * Instantiates a new Base bottom pop view.
     *
     * @param context the context
     */
    public BaseBottomPopView(@NonNull Context context) {
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
