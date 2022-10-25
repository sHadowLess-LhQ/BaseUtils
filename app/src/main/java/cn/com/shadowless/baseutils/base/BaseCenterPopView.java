package cn.com.shadowless.baseutils.base;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;


import com.lxj.xpopup.core.CenterPopupView;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle3.LifecycleProvider;

import cn.com.shadowless.baseutils.R;

/**
 * 居中弹窗
 *
 * @param <VB> the type 绑定视图
 * @author sHadowLess
 */
public abstract class BaseCenterPopView<VB extends ViewBinding> extends CenterPopupView {

    /**
     * 绑定视图
     */
    private VB bind = null;
    /**
     * 上下文
     */
    private final Context context;
    /**
     * 布局编号
     */
    private int layoutId;
    /**
     * 生命周期
     */
    protected LifecycleProvider<Lifecycle.Event> provider;

    /**
     * 构造
     *
     * @param context the 上下文
     */
    public BaseCenterPopView(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    /**
     * 构造
     *
     * @param context  the 上下文
     * @param layoutId the 布局编号
     */
    public BaseCenterPopView(@NonNull Context context, int layoutId) {
        super(context);
        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    protected int getImplLayoutId() {
        return layoutId;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate() {
        super.onCreate();
        bind = setBindView();
        provider = AndroidLifecycle.createLifecycleProvider(this);
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
     * 设置布局编号
     *
     * @param layoutId the layout id
     */
    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    /**
     * 获取视图绑定
     *
     * @return the bind view
     */
    @NonNull
    protected VB getBindView() {
        return bind;
    }

    /**
     * 初始化视图控件
     */
    protected abstract void initView();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 是否使用默认背景
     *
     * @return the boolean
     */
    protected abstract boolean isDefaultBackground();

    /**
     * 设置绑定视图
     *
     * @return the bind view
     */
    @NonNull
    protected abstract VB setBindView();
}
