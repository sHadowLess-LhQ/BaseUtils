package cn.com.shadowless.baseutils;

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
        compositeDisposable = new CompositeDisposable();
        RxUtils
                .builder()
                .build()
                .rxCreate(RxUtils.ThreadSign.DEFAULT, this, this);
        return view;
    }

    @Override
    public void onEmitter(ObservableEmitter<Boolean> emitter) {
        if (initData()) {
            emitter.onNext(true);
            emitter.onComplete();
        } else {
            emitter.onError(new Throwable("初始化数据错误"));
        }
    }

    @Override
    public void onSuccess(Boolean aBoolean) {
        if (aBoolean) {
            initView();
        }
    }

    @Override
    public void onFail(Throwable throwable) {
        Log.e(TAG, "onFail: " + throwable);
    }

    @Override
    public void onEnd() {
        Log.e(TAG, "onEnd: " + "初始化数据成功");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        unbinder.unbind();
        Log.e(TAG, "onDestroyView: " + "已销毁");
        super.onDestroyView();
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
}
