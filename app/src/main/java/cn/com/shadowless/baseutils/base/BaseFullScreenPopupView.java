package cn.com.shadowless.baseutils.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;


import com.lxj.xpopup.impl.FullScreenPopupView;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle3.LifecycleProvider;

import cn.com.shadowless.baseutils.R;

/**
 * The type Base full screen popup view.
 *
 * @param <VB> the type parameter
 * @author sHadowLess
 */
public abstract class BaseFullScreenPopupView<VB extends ViewBinding> extends FullScreenPopupView {

    /**
     * The Bind.
     */
    private VB bind = null;
    /**
     * The Context.
     */
    private final Context context;
    /**
     * The Provider.
     */
    protected LifecycleProvider<Lifecycle.Event> provider;

    /**
     * Instantiates a new Base full screen popup view.
     *
     * @param context the context
     */
    public BaseFullScreenPopupView(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getImplLayoutId() {
        return setLayoutId();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        bind = setBindView();
        provider = AndroidLifecycle.createLifecycleProvider(this);
        initView();
        if (isDefaultBackground()) {
            getPopupImplView().setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_base_pop_full_view));
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
    protected VB getBindView() {
        return bind;
    }

    /**
     * Sets bind view.
     *
     * @return the bind view
     */
    @NonNull
    protected abstract VB setBindView();

    /**
     * Sets layout id.
     *
     * @return the layout id
     */
    protected abstract int setLayoutId();

    /**
     * Is default background boolean.
     *
     * @return the boolean
     */
    protected abstract boolean isDefaultBackground();

    /**
     * Init view.
     */
    protected abstract void initView();

    /**
     * Init listener.
     */
    protected abstract void initListener();
}
