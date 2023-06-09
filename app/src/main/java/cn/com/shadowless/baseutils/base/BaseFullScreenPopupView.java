package cn.com.shadowless.baseutils.base;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;


import com.lxj.xpopup.impl.FullScreenPopupView;
import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.RxLifecycle;

import cn.com.shadowless.baseutils.R;
import cn.com.shadowless.baseutils.utils.ClickUtils;
import cn.com.shadowless.baseutils.utils.RxUtils;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * The type Base full screen popup view.
 *
 * @param <VB> the type parameter
 * @author sHadowLess
 */
public abstract class BaseFullScreenPopupView<VB extends ViewBinding> extends FullScreenPopupView implements LifecycleProvider<Lifecycle.Event>, View.OnClickListener{

    /**
     * 绑定视图
     */
    private VB bind = null;
    /**
     * 上下文
     */
    private final Context context;
    /**
     * 订阅行为
     */
    private final BehaviorSubject<Lifecycle.Event> lifecycleSubject = BehaviorSubject.create();

    /**
     * 构造
     *
     * @param context the 上下文
     */
    public BaseFullScreenPopupView(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public Observable<Lifecycle.Event> lifecycle() {
        return lifecycleSubject.hide();
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull Lifecycle.Event event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxUtils.bindLifecycle(lifecycleSubject);
    }

    @Override
    protected int getImplLayoutId() {
        return setLayoutId();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        bind = setBindView(getPopupImplView());
        initView();
        if (isDefaultBackground()) {
            getPopupImplView().setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_base_pop_full_shape));
        }
    }

    @Override
    protected void onShow() {
        super.onShow();
        initListener();
    }

    @Override
    public void onClick(View v) {
        if (!ClickUtils.isFastClick()) {
            click(v);
        }
    }

    /**
     * 获取绑定视图控件
     *
     * @return the bind view
     */
    protected VB getBindView() {
        return bind;
    }

    /**
     * 设置布局编号
     *
     * @return the layout id
     */
    protected abstract int setLayoutId();

    /**
     * 设置绑定视图
     *
     * @param v the v
     * @return the bind view
     */
    @NonNull
    protected abstract VB setBindView(View v);

    /**
     * 是否默认背景颜色
     *
     * @return the boolean
     */
    protected abstract boolean isDefaultBackground();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 点击
     *
     * @param v the v
     */
    protected abstract void click(@NonNull View v);
}
