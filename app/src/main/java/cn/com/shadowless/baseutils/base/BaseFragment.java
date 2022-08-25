package cn.com.shadowless.baseutils.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle3.LifecycleProvider;

import cn.com.shadowless.baseutils.permission.RxPermissions;
import cn.com.shadowless.baseutils.utils.ApplicationUtils;
import cn.com.shadowless.baseutils.utils.RxUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * 基类Fragment
 *
 * @param <VB> the type 视图
 * @param <T>  the type parameter
 * @author sHadowLess
 */
public abstract class BaseFragment<VB extends ViewBinding, T> extends Fragment implements ObservableOnSubscribe<T>, Observer<T> {

    /**
     * The Tag.
     */
    private final String TAG = BaseFragment.class.getSimpleName();
    /**
     * 视图绑定
     */
    private VB bind = null;
    /**
     * 屏幕方向标志
     */
    protected boolean isOrientation = false;
    /**
     * 依附的activity
     */
    protected Activity mActivity = null;
    /**
     * 统一订阅管理
     */
    protected CompositeDisposable mDisposable = null;
    /**
     * Rx声明周期管理
     */
    protected LifecycleProvider<Lifecycle.Event> provider = null;

    /**
     * 初始化数据回调接口
     *
     * @param <T> the type parameter
     */
    protected interface InitDataCallBack<T> {
        /**
         * 成功且带数据
         *
         * @param t the t
         */
        void initViewWithData(@NonNull T t);

        /**
         * 成功不带数据
         */
        void initViewWithOutData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        initOrientation();
        bind = setBindView();
        initListener();
        initPermissions();
        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        if (null != mDisposable && mDisposable.size() != 0 && !mDisposable.isDisposed()) {
            mDisposable.clear();
            mDisposable = null;
        }
        if (bind != null) {
            bind = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<T> emitter) throws Exception {
        initData(new InitDataCallBack<T>() {
            @Override
            public void initViewWithData(@NonNull T t) {
                emitter.onNext(t);
                emitter.onComplete();
            }

            @Override
            public void initViewWithOutData() {
                initView(null);
                emitter.onComplete();
            }
        });
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mDisposable.add(d);
    }

    @Override
    public void onNext(@NonNull T mData) {
        initView(mData);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        initView(null);
        Log.e(TAG, "onError: " + e);
    }

    @Override
    public void onComplete() {
        Log.e(TAG, "onComplete: " + "Fragment加载完成");
    }

    /**
     * 获取绑定的视图
     *
     * @return the 视图
     */
    @NonNull
    protected VB getBindView() {
        return bind;
    }

    /**
     * 需要申请的权限
     *
     * @return the 权限组
     */
    @Nullable
    protected abstract String[] permissionName();

    /**
     * 设置绑定视图
     *
     * @return the 视图
     */
    @NonNull
    protected abstract VB setBindView();

    /**
     * 初始化数据
     *
     * @param initDataCallBack the 数据回调
     */
    protected abstract void initData(@NonNull InitDataCallBack<T> initDataCallBack);

    /**
     * 初始化视图
     *
     * @param t the t
     */
    protected abstract void initView(@Nullable T t);

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 初始化方向变量
     */
    private void initOrientation() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isOrientation = false;
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            isOrientation = true;
        }
    }

    /**
     * 初始化权限
     */
    private void initPermissions() {
        mDisposable = new CompositeDisposable();
        provider = AndroidLifecycle.createLifecycleProvider(this);
        String[] permissions = permissionName();
        if (null != permissions && permissions.length != 0) {
            mDisposable.add(new RxPermissions(this).requestEachCombined(permissions)
                    .subscribe(permission -> {
                                if (permission.granted) {
                                    Observable.create(this).compose(RxUtils.dealObservableThread(RxUtils.ThreadSign.DEFAULT)).subscribe(this);
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    showToast(permission.name);
                                } else {
                                    showToast(permission.name);
                                }
                            }
                    ));
        } else {
            Observable.create(this).compose(RxUtils.dealObservableThread(RxUtils.ThreadSign.DEFAULT)).subscribe(this);
        }
    }

    /**
     * 内部权限提示
     *
     * @param name the 权限名
     */
    private void showToast(String name) {
        String tip = "应用无法使用，请开启%s权限";
        Toast.makeText(mActivity, String.format(tip, name), Toast.LENGTH_SHORT).show();
        ApplicationUtils.startApplicationInfo(mActivity);
    }

    /**
     * 跳转
     *
     * @param path the 路由路径
     * @return the postcard
     */
    protected Postcard jump(String path) {
        return ARouter.getInstance().build(path);
    }
}
