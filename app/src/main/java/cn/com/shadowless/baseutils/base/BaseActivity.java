package cn.com.shadowless.baseutils.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import java.util.HashMap;
import java.util.Map;

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
 * @param <T>  the type parameter
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
     * 当前碎片
     */
    private Fragment currentFragment = null;
    /**
     * 屏幕方向标志
     */
    protected boolean isOrientation = false;
    /**
     * 统一订阅管理
     */
    protected CompositeDisposable mDisposable = null;

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
        void successWithData(@NonNull T t);

        /**
         * 成功不带数据
         */
        void successWithOutData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int customTheme = theme();
        if (0 != customTheme) {
            setTheme(customTheme);
        }
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isOrientation = false;
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            isOrientation = true;
        }
        mDisposable = new CompositeDisposable();
        bind = setBindView();
        setContentView(bind.getRoot());
        initListener();
        String[] permissions = permissionName();
        if (null != permissions && permissions.length != 0) {
            mDisposable.add(new RxPermissions(this).requestEachCombined(permissions)
                    .subscribe(permission -> {
                                if (permission.granted) {
                                    Observable.create(this).compose(RxUtils.dealThread(RxUtils.ThreadSign.DEFAULT)).subscribe(this);
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    showToast(permission.name);
                                } else {
                                    showToast(permission.name);
                                }
                            }
                    ));
        } else {
            Observable.create(this).compose(RxUtils.dealThread(RxUtils.ThreadSign.DEFAULT)).subscribe(this);
        }
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
            public void successWithData(@NonNull T t) {
                emitter.onNext(t);
                emitter.onComplete();
            }

            @Override
            public void successWithOutData() {
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
        Log.e(TAG, "onFail: " + e);
    }

    @Override
    public void onComplete() {
        initView(null);
        Log.e(TAG, "onEnd: " + "Activity加载成功");
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
     * Sets theme.
     *
     * @return the theme
     */
    protected abstract int theme();


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
     * 初始化监听
     */
    protected abstract void initListener();

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
     * 显示碎片
     *
     * @param fragment the 碎片
     * @param layout   the 布局
     */
    protected void showFragment(Fragment fragment, int layout) {
        show(fragment, layout, null);
    }

    /**
     * 显示碎片
     *
     * @param fragment  the 碎片
     * @param layout    the 布局
     * @param animation the 动画
     */
    protected void showFragment(Fragment fragment, int layout, int... animation) {
        show(fragment, layout, animation);
    }

    /**
     * 替换碎片
     *
     * @param fragment the 碎片
     * @param layout   the 布局
     */
    protected void replaceFragment(Fragment fragment, int layout) {
        replace(fragment, layout, null);
    }

    /**
     * 替换碎片
     *
     * @param fragment  the 碎片
     * @param layout    the 布局
     * @param animation the 动画
     */
    protected void replaceFragment(Fragment fragment, int layout, int... animation) {
        replace(fragment, layout, animation);
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

    /**
     * 显示
     *
     * @param fragment  the 碎片
     * @param layout    the 布局
     * @param animation the 动画
     */
    private void show(Fragment fragment, int layout, int... animation) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        currentFragment = fragment;
        if (!fragment.isAdded()) {
            if (animation != null && animation.length != 0) {
                transaction.add(layout, fragment).show(fragment).setCustomAnimations(
                        animation[0],
                        animation[1],
                        animation[2],
                        animation[3]
                ).commit();
            } else {
                transaction.add(layout, fragment).show(fragment).commit();
            }
        } else {
            if (animation != null && animation.length != 0) {
                transaction.show(fragment).setCustomAnimations(
                        animation[0],
                        animation[1],
                        animation[2],
                        animation[3]
                ).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }

    /**
     * 替换
     *
     * @param fragment  the 碎片
     * @param layout    the 布局
     * @param animation the 动画
     */
    private void replace(Fragment fragment, int layout, int... animation) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (animation != null && animation.length != 0) {
            transaction
                    .setCustomAnimations(
                            animation[0],
                            animation[1],
                            animation[2],
                            animation[3]
                    )
                    .replace(layout, fragment)
                    .commit();
        } else {
            transaction
                    .replace(layout, fragment)
                    .commit();
        }
    }
}