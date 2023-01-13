package cn.com.shadowless.baseutils.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;

import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle3.LifecycleProvider;

import cn.com.shadowless.baseutils.R;
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
 * 基类Activity
 *
 * @param <VB> the type 视图
 * @param <T>  the type 传递数据类型
 * @author sHadowLess
 */
public abstract class BaseActivity<VB extends ViewBinding, T> extends AppCompatActivity implements ObservableOnSubscribe<T>, Observer<T> {

    /**
     * The Tag.
     */
    private final String TAG = BaseActivity.class.getSimpleName();
    /**
     * 视图绑定
     */
    private VB bind = null;
    /**
     * 屏幕方向标志
     */
    protected boolean isOrientation = false;
    /**
     * 统一订阅管理
     */
    protected CompositeDisposable mDisposable = null;
    /**
     * Rx生命周期管理
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
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(initTheme());
        super.onCreate(savedInstanceState);
        initOrientation();
        initBindView();
        initListener();
        initPermissions(initDataThreadMod());
    }

    @Override
    protected void onDestroy() {
        if (null != mDisposable && mDisposable.size() != 0 && !mDisposable.isDisposed()) {
            mDisposable.clear();
            mDisposable = null;
        }
        if (null != bind) {
            bind = null;
        }
        super.onDestroy();
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<T> emitter) throws Exception {
        initData(new InitDataCallBack<T>() {
            @Override
            public void initViewWithData(@NonNull T t) {
                emitter.onNext(t);
            }

            @Override
            public void initViewWithOutData() {
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
        Log.e(TAG, "onError: ", e);
    }

    @Override
    public void onComplete() {
        initView(null);
        Log.e(TAG, "onComplete: " + "Activity加载完成");
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
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     *
     * @param initDataCallBack the  回调
     */
    protected abstract void initData(@NonNull InitDataCallBack<T> initDataCallBack);

    /**
     * 初始化视图
     *
     * @param data the 数据表
     */
    protected abstract void initView(@Nullable T data);

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
     * 初始化主题
     *
     * @return the int
     */
    protected int initTheme() {
        return R.style.MyAppTheme;
    }

    /**
     * 初始化数据所在线程
     *
     * @return the 线程模式
     */
    protected int initDataThreadMod() {
        return RxUtils.IO_TO_MAIN;
    }

    /**
     * 初始化权限
     *
     * @param threadMod the 线程模式
     */
    private void initPermissions(int threadMod) {
        mDisposable = new CompositeDisposable();
        provider = AndroidLifecycle.createLifecycleProvider(this);
        String[] permissions = permissionName();
        if (null != permissions && permissions.length != 0) {
            mDisposable.add(new RxPermissions(this).requestEachCombined(permissions)
                    .subscribe(permission -> {
                                if (permission.granted) {
                                    Observable.create(this).compose(RxUtils.dealObservableThread(threadMod)).subscribe(this);
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    showToast(permission.name);
                                } else {
                                    showToast(permission.name);
                                }
                            }
                    ));
        } else {
            Observable.create(this).compose(RxUtils.dealObservableThread(threadMod)).subscribe(this);
        }
    }

    /**
     * 初始化视图
     */
    private void initBindView() {
        bind = setBindView();
        setContentView(bind.getRoot());
    }

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
     * 内部权限提示
     *
     * @param name the 权限名
     */
    private void showToast(String name) {
        String tip = "应用无法使用，请开启%s权限";
        Toast.makeText(this, String.format(tip, name), Toast.LENGTH_SHORT).show();
        ApplicationUtils.startApplicationInfo(this);
    }
}