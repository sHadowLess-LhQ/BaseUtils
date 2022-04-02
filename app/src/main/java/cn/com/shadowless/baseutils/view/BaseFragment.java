package cn.com.shadowless.baseutils.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import cn.com.shadowless.baseutils.utils.ApplicationUtils;
import cn.com.shadowless.baseutils.utils.RxUtils;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;


/**
 * 基类Fragment
 *
 * @param <T> the type parameter
 * @author sHadowLess
 */
public abstract class BaseFragment<T extends ViewBinding> extends Fragment implements RxUtils.ObserverCallBack.EmitterCallBack<Map<String, Object>>, RxUtils.ObserverCallBack<Map<String, Object>> {

    /**
     * The Tag.
     */
    private final String TAG = BaseFragment.class.getSimpleName();
    /**
     * 视图绑定
     */
    private T bind = null;
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
     * 订阅
     */
    private Disposable temp = null;


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
        bind = setBindView();
        temp = null;
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
        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        if (null != disposable && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (bind != null) {
            bind = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
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
    public void onSubscribe(Disposable disposable) {
        temp = disposable;
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
    public void onEnd() {
        temp.dispose();
    }

    /**
     * 获取绑定的视图
     *
     * @return the 视图
     */
    protected T getBindView() {
        return bind;
    }

    /**
     * 内部权限提示
     *
     * @param name the 权限名
     */
    private void showToast(String name) {
        String tip = "应用无法使用，请开启%s权限";
        Toast.makeText(mActivity, String.format(tip, name), Toast.LENGTH_SHORT).show();
        ApplicationUtils.startApplicationInfo(mActivity);
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
     * 初始化数据
     *
     * @param mData            the 数据表
     * @param initDataCallBack the 数据回调
     */
    protected abstract void initData(@NonNull Map<String, Object> mData, @NonNull InitDataCallBack initDataCallBack);

    /**
     * 初始化视图
     *
     * @param map the 数据表
     */
    protected abstract void initView(@NonNull Map<String, Object> map);
}
