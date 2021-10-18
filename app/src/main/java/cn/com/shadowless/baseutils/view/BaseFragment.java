package cn.com.shadowless.baseutils.view;

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

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.shadowless.baseutils.utils.RxUtils;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.CompositeDisposable;


/**
 * The type Base fragment.
 *
 * @author sHadowLess
 */
public abstract class BaseFragment extends Fragment implements RxUtils.ObserverCallBack.EmitterCallBack<Boolean>, RxUtils.ObserverCallBack<Boolean> {

    /**
     * The M activity.
     */
    private final String TAG = BaseFragment.class.getSimpleName();
    /**
     * The Unbinder.
     */
    private Unbinder unbinder;
    /**
     * The Is orientation.
     */
    protected boolean isOrientation = false;
    /**
     * The M activity.
     */
    protected Activity mActivity;
    /**
     * The Composite disposable.
     */
    protected CompositeDisposable compositeDisposable;

    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */
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
        compositeDisposable = new CompositeDisposable();
        RxUtils
                .builder()
                .build()
                .rxCreate(RxUtils.ThreadSign.DEFAULT, this, this);
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
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        unbinder.unbind();
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
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
        if (initData()) {
            emitter.onNext(true);
        } else {
            emitter.onNext(false);
        }
        emitter.onComplete();
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
     * 该抽象方法就是 onCreateView中需要的layoutID
     *
     * @return layout id
     */
    protected abstract int getLayoutId();

    /**
     * 执行数据的加载
     *
     * @return the string
     */
    protected abstract boolean initData();

    /**
     * Init view.
     */
    protected abstract void initView();

    /**
     * Error view.
     */
    protected abstract void errorView();
}
