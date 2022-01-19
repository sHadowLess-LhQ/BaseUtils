package cn.com.shadowless.baseutils.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import cn.com.shadowless.baseutils.utils.ApplicationUtils;
import cn.com.shadowless.baseutils.utils.RxUtils;
import cn.com.shadowless.baseutils.utils.ToastUtils;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;

/**
 * 基类Activity
 *
 * @param <T> the type parameter
 * @author sHadowLess
 */
public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity implements RxUtils.ObserverCallBack.EmitterCallBack<Map<String, Object>>, RxUtils.ObserverCallBack<Map<String, Object>> {

    /**
     * 视图绑定
     */
    private T bind = null;
    /**
     * 屏幕方向标志
     */
    protected boolean isOrientation = false;

    /**
     * 初始化数据回调接口
     */
    protected interface InitDataCallBack {
        /**
         * 成功
         *
         * @param map the 数据表
         */
        void success(Map<String, Object> map);
    }

    /**
     * 订阅
     */
    private Disposable disposable = null;


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
        bind = setBindView();
        setContentView(bind.getRoot());
        String[] permissions = permissionName();
        if (null != permissions && permissions.length != 0) {
            disposable = new RxPermissions(this).requestEachCombined(permissions)
                    .subscribe(permission -> {
                                if (permission.granted) {
                                    RxUtils.rxCreate(RxUtils.ThreadSign.DEFAULT, this, this);
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    showToast(permission.name);
                                } else {
                                    showToast(permission.name);
                                }
                            }
                    );
        } else {
            RxUtils.rxCreate(RxUtils.ThreadSign.DEFAULT, this, this);
        }
    }

    @Override
    protected void onDestroy() {
        if (null != disposable && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (null != bind) {
            bind = null;
        }
        super.onDestroy();
    }

    @Override
    public void onEmitter(ObservableEmitter<Map<String, Object>> emitter) {
        Map<String, Object> mData = new HashMap<>();
        initData(mData, map -> {
            emitter.onNext(map);
            emitter.onComplete();
        });
    }

    @Override
    public void onSuccess(Map<String, Object> mData) {
        initView(mData);
    }

    @Override
    public void onEnd(Disposable disposable) {
        disposable.dispose();
    }

    /**
     * 获取绑定的视图
     *
     * @return the 视图
     */
    public T getBindView() {
        return bind;
    }

    /**
     * 内部权限提示
     *
     * @param name the 权限名
     */
    private void showToast(String name) {
        String tip = "应用无法使用，请开启%s权限";
        ToastUtils.warning(this, String.format(tip, name));
        ApplicationUtils.startApplicationInfo(this);
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
    protected abstract T setBindView();

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
    protected abstract void initData(@NonNull Map<String, Object> mData, @NonNull InitDataCallBack initDataCallBack);

    /**
     * 初始化视图
     *
     * @param data the 数据表
     */
    protected abstract void initView(@NonNull Map<String, Object> data);
}