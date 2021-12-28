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
import io.reactivex.disposables.Disposable;


/**
 * 基类Fragment
 *
 * @author sHadowLess
 */
public abstract class BaseFragment extends Fragment {

    /**
     * The M activity.
     */
    private final String TAG = BaseFragment.class.getSimpleName();
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
     * The M data.
     */
    protected Map<String, Object> mData = new HashMap<>();
    /**
     * 订阅
     */
    private Disposable disposable = null;

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
     * @param mData the m data
     */
    protected abstract void initData(@NonNull Map<String, Object> mData);

    /**
     * 初始化视图
     *
     * @param map the map
     */
    protected abstract void initView(@NonNull Map<String, Object> map);
}
