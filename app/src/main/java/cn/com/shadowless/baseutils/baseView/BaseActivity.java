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
import io.reactivex.disposables.Disposable;

/**
 * 基类Activity
 *
 * @author sHadowLess
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * The Tag.
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
     * The Data.
     */
    protected Map<String, Object> mData = new HashMap<>();
    /**
     * 订阅
     */
    private Disposable disposable = null;

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
                                    init();
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    showToast(permission.name);
                                } else {
                                    showToast(permission.name);
                                }
                            }
                    );
        } else {
            init();
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    /**
     * 初始化
     */
    private void init() {
        mData.clear();
        initData(mData);
        initView(mData);
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
     * @param mData the m data
     */
    protected abstract void initData(@NonNull Map<String, Object> mData);

    /**
     * 初始化视图
     *
     * @param data the data
     */
    protected abstract void initView(@NonNull Map<String, Object> data);
}