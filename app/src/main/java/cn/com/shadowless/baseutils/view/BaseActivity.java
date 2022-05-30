package cn.com.shadowless.baseutils.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import java.util.HashMap;
import java.util.Map;

import cn.com.shadowless.baseutils.permission.RxPermissions;
import cn.com.shadowless.baseutils.utils.ApplicationUtils;
import cn.com.shadowless.baseutils.utils.RxUtils;
import cn.com.shadowless.baseutils.view.swipe.SwipeBackActivity;
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
 * @param <K>  the type Key值类型
 * @param <V>  the type Value类型
 * @author sHadowLess
 */
public abstract class BaseActivity<VB extends ViewBinding, K, V> extends SwipeBackActivity implements ObservableOnSubscribe<Map<K, V>>, Observer<Map<K, V>> {

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
     * @param <K> the type parameter
     * @param <V> the type parameter
     */
    protected interface InitDataCallBack<K, V> {
        /**
         * 成功
         *
         * @param map the 数据表
         */
        void success(Map<K, V> map);
    }

    /**
     * 订阅
     */
    private Disposable disposable = null;
    /**
     * 订阅
     */
    private Disposable temp = null;


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
        temp = null;
        setContentView(bind.getRoot());
        String[] permissions = permissionName();
        if (null != permissions && permissions.length != 0) {
            disposable = new RxPermissions(this).requestEachCombined(permissions)
                    .subscribe(permission -> {
                                if (permission.granted) {
                                    Observable.create(this).compose(RxUtils.dealThread(RxUtils.ThreadSign.DEFAULT)).subscribe(this);
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    showToast(permission.name);
                                } else {
                                    showToast(permission.name);
                                }
                            }
                    );
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
        if (null != disposable && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (null != bind) {
            bind = null;
        }
        super.onDestroy();
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<Map<K, V>> emitter) throws Exception {
        try {
            Map<K, V> mData = new HashMap<>();
            initData(mData, map -> {
                emitter.onNext(map);
                emitter.onComplete();
            });
        } catch (Exception e) {
            emitter.onError(e);
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        temp = d;
    }

    @Override
    public void onNext(@NonNull Map<K, V> mData) {
        initView(mData);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.e(TAG, "onFail: " + e);
    }

    @Override
    public void onComplete() {
        temp.dispose();
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
     * @param mData            the  数据表
     * @param initDataCallBack the  回调
     */
    protected abstract void initData(@NonNull Map<K, V> mData, @NonNull InitDataCallBack<K, V> initDataCallBack);

    /**
     * 初始化视图
     *
     * @param data the 数据表
     */
    protected abstract void initView(@NonNull Map<K, V> data);

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
        show(fragment, layout, false, null);
    }

    /**
     * 显示碎片
     *
     * @param fragment  the 碎片
     * @param layout    the 布局
     * @param animation the 动画
     */
    protected void showFragment(Fragment fragment, int layout, int... animation) {
        show(fragment, layout, true, animation);
    }

    /**
     * 替换碎片
     *
     * @param fragment the 碎片
     * @param layout   the 布局
     */
    protected void replaceFragment(Fragment fragment, int layout) {
        replace(fragment, layout, false, null);
    }

    /**
     * 替换碎片
     *
     * @param fragment  the 碎片
     * @param layout    the 布局
     * @param animation the 动画
     */
    protected void replaceFragment(Fragment fragment, int layout, int... animation) {
        replace(fragment, layout, true, animation);
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
     * @param anim      the 是否动画
     * @param animation the 动画
     */
    private void show(Fragment fragment, int layout, boolean anim, int... animation) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment == null) {
            currentFragment = fragment;
        } else if (currentFragment != fragment) {
            transaction.hide(currentFragment);
            currentFragment = fragment;
        }
        if (!fragment.isAdded()) {
            if (anim) {
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
            if (anim) {
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
     * @param anim      the 是否动画
     * @param animation the 动画
     */
    private void replace(Fragment fragment, int layout, boolean anim, int... animation) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (anim) {
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