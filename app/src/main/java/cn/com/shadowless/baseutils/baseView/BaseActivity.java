package cn.com.shadowless.baseutils.baseView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.mengpeng.mphelper.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

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
public abstract class BaseActivity extends AppCompatActivity implements RxUtils.ObserverCallBack.EmitterCallBack<Boolean>, RxUtils.ObserverCallBack<Boolean> {

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
     * 订阅
     */
    private Disposable disposable;

    /**
     * 初始化数据回调接口
     */
    protected interface InitDataCallBack {
        /**
         * Success.
         *
         * @param map the map
         */
        void success(Map<String, Object> map);

        /**
         * Fail.
         *
         * @param error the error
         */
        void fail(String error);
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
        disposable = new RxPermissions(this).requestEachCombined(permissionName())
                .subscribe(permission -> {
                            if (permission.granted) {
                                RxUtils
                                        .builder()
                                        .build()
                                        .rxCreate(RxUtils.ThreadSign.DEFAULT, this, this);
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                showToast(permission.name);
                            } else {
                                showToast(permission.name);
                            }
                        }
                );
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
    public void onEmitter(ObservableEmitter<Boolean> emitter) {
        initData(new InitDataCallBack() {
            @Override
            public void success(Map<String, Object> map) {
                initView(map);
                emitter.onNext(true);
                emitter.onComplete();
            }

            @Override
            public void fail(String error) {
                errorView(error);
                emitter.onNext(false);
                emitter.onComplete();
            }
        });
    }

    @Override
    public void onSuccess(Boolean aBoolean) {
        Log.i(TAG, "onSuccess: " + aBoolean);
    }

    @Override
    public void onFail(Throwable throwable) {
        Log.e(TAG, "onFail: " + throwable);
    }

    @Override
    public void onEnd() {
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
     * @param initDataCallBack the init data call back
     */
    protected abstract void initData(InitDataCallBack initDataCallBack);

    /**
     * 初始化视图
     *
     * @param objects the objects
     */
    protected abstract void initView(Object... objects);

    /**
     * 初始化错误视图
     *
     * @param error the error
     */
    protected abstract void errorView(String error);

}