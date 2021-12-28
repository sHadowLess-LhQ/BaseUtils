package cn.com.shadowless.baseutils.baseView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mengpeng.mphelper.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.shadowless.baseutils.utils.ApplicationUtils;
import cn.com.shadowless.baseutils.utils.RxUtils;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;

/**
 * 基类Activity
 *
 * @author sHadowLess
 */
public abstract class BaseActivity extends AppCompatActivity implements RxUtils.ObserverCallBack.EmitterCallBack<Map<String, Object>>, RxUtils.ObserverCallBack<Map<String, Object>> {

    /**
     * TAG
     */
    private final String TAG = BaseActivity.class.getSimpleName();
    /**
     * 黄油刀
     */
    private Unbinder unbinder = null;
    /**
     * 屏幕方向标志
     */
    protected boolean isOrientation = false;
    /**
     * 数据存储表
     */
    protected Map<String, Object> mData = new HashMap<>();
    /**
     * 订阅
     */
    private Disposable disposable = null;

    /**
     * 初始化数据回调接口
     */
    protected interface InitDataCallBack {
        /**
         * 成功
         *
         * @param map the map
         */
        void success(Map<String, Object> map);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isOrientation = false;
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            isOrientation = true;
        }
        setContentView(setLayout());
        unbinder = ButterKnife.bind(this);
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
        unbinder.unbind();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        Log.d("TAG", "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onEmitter(ObservableEmitter<Map<String, Object>> emitter) {
        mData.clear();
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
    public void onFail(Throwable throwable) {
        Log.e(TAG, "onFail: " + throwable);
    }

    @Override
    public void onEnd(Disposable disposable) {
        disposable.dispose();
        Log.i(TAG, "onEnd: " + "初始化数据成功");
    }

    /**
     * 内部权限提示
     *
     * @param name the 权限名
     */
    private void showToast(String name) {
        String tip = "应用无法使用，请开启%s权限";
        ToastUtils.onWarnShowToast(String.format(tip, name));
        ApplicationUtils.startApplicationInfo(this);
    }

    /**
     * 需要申请的权限
     *
     * @return the string [ ]
     */
    @Nullable
    protected abstract String[] permissionName();

    /**
     * 设置布局
     *
     * @return the layout
     */
    protected abstract int setLayout();

    /**
     * 初始化数据
     *
     * @param mData            the m data
     * @param initDataCallBack the init data call back
     */
    protected abstract void initData(@NonNull Map<String, Object> mData, @NonNull InitDataCallBack initDataCallBack);

    /**
     * 初始化视图
     *
     * @param data the data
     */
    protected abstract void initView(@NonNull Map<String, Object> data);
}