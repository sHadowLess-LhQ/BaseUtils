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

import java.util.HashMap;
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
public abstract class BaseFragment extends Fragment implements RxUtils.ObserverCallBack.EmitterCallBack<Map<String, Object>>, RxUtils.ObserverCallBack<Map<String, Object>> {

    /**
     * TAG
     */
    private final String TAG = BaseFragment.class.getSimpleName();
    /**
     * 数据存储表
     */
    private static Map<String, Object> mData = new HashMap<>();
    /**
     * 黄油刀
     */
    private Unbinder unbinder = null;
    /**
     * 屏幕方向标志
     */
    protected boolean isOrientation = false;
    /**
     * 依附的activity
     */
    protected Activity mActivity = null;
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        this.mActivity = (Activity) context;
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
        String[] permissions = permissionName();
        if (null != permissions && permissions.length != 0) {
            disposable = new RxPermissions(mActivity).requestEachCombined(permissions)
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
        return view;
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
        ApplicationUtils.startApplicationInfo(mActivity);
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
     * @return layout id
     */
    protected abstract int getLayoutId();

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
     * @param map the map
     */
    protected abstract void initView(@NonNull Map<String, Object> map);
}
