package cn.com.shadowless.baseutils.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.mengpeng.mphelper.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.shadowless.baseutils.utils.ApplicationUtils;
import cn.com.shadowless.baseutils.utils.RxUtils;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;

/**
 * The type Base activity.
 *
 * @author sHadowLess
 */
public abstract class BaseActivity extends AppCompatActivity implements RxUtils.ObserverCallBack.EmitterCallBack<Boolean>, RxUtils.ObserverCallBack<Boolean> {

    /**
     * The Tag.
     */
    private final String TAG = BaseActivity.class.getSimpleName();
    /**
     * The Unbinder.
     */
    private Unbinder unbinder = null;
    /**
     * The Is orientation.
     */
    protected boolean isOrientation = false;
    /**
     * The Disposable.
     */
    private Disposable disposable;

    /**
     * The interface Init data call back.
     */
    private interface InitDataCallBack {
        /**
         * Success.
         */
        void success();

        /**
         * Fail.
         */
        void fail();
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
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "onStop");
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
            public void success() {
                emitter.onNext(true);
                emitter.onComplete();
            }

            @Override
            public void fail() {
                emitter.onNext(false);
                emitter.onComplete();
            }
        });
    }

    @Override
    public void onSuccess(Boolean aBoolean) {
        Log.i(TAG, "onSuccess: " + aBoolean);
        if (aBoolean) {
            initView();
        } else {
            errorView();
        }
    }

    @Override
    public void onFail(Throwable throwable) {
        Log.e(TAG, "onFail: " + throwable);
        errorView();
    }

    @Override
    public void onEnd() {
        Log.i(TAG, "onEnd: " + "初始化数据成功");
    }

    /**
     * Show toast.
     *
     * @param name the name
     */
    private void showToast(String name) {
        String tip = "应用无法使用，请开启%s权限";
        ToastUtils.onWarnShowToast(String.format(tip, name));
        ApplicationUtils.startApplicationInfo(this);
    }

    /**
     * Permission name string [ ].
     *
     * @return the string [ ]
     */
    protected abstract String[] permissionName();

    /**
     * Sets layout.
     *
     * @return the layout
     */
    protected abstract int setLayout();

    /**
     * Sets data.
     *
     * @param initDataCallBack the init data call back
     */
    protected abstract void initData(InitDataCallBack initDataCallBack);

    /**
     * Init view.
     */
    protected abstract void initView();

    /**
     * Error view.
     */
    protected abstract void errorView();

}