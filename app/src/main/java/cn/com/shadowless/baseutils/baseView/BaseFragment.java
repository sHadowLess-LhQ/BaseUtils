package cn.com.shadowless.baseutils.baseView;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
 * 基类Fragment
 *
 * @author sHadowLess
 */
public abstract class BaseFragment extends Fragment implements RxUtils.ObserverCallBack.EmitterCallBack<Boolean>, RxUtils.ObserverCallBack<Boolean> {

    /**
     * The M activity.
     */
    private final String TAG = BaseFragment.class.getSimpleName();
    /**
     * 黄油刀
     */
    private Unbinder unbinder;
    /**
     * 屏幕方向标志
     */
    protected boolean isOrientation = false;
    /**
     * 依附的activity
     */
    protected Activity mActivity;
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
         */
        void fail();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        this.mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isOrientation = false;
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            isOrientation = true;
        }
        View view = LayoutInflater.from(mActivity).inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        disposable = new RxPermissions(mActivity).requestEachCombined(permissionName())
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
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
        Log.d(TAG, "onDetach");
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
            public void fail() {
                emitter.onNext(false);
                emitter.onComplete();
            }
        });
    }

    @Override
    public void onSuccess(Boolean aBoolean) {
        Log.i(TAG, "onSuccess: " + aBoolean);
        if (!aBoolean) {
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
     * 内部权限提示
     *
     * @param name the 权限名
     */
    private void showToast(String name) {
        String tip = "应用无法使用，请开启%s权限";
        ToastUtils.onWarnShowToast(String.format(tip, name));
        ApplicationUtils.startApplicationInfo(mActivity);
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
     * @return layout id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化数据
     *
     * @param initDataCallBack the init data call back
     */
    protected abstract void initData(InitDataCallBack initDataCallBack);

    /**
     * 初始化视图
     */
    protected abstract void initView(Map<String, Object> map);

    /**
     * 初始化错误视图
     */
    protected abstract void errorView();
}
