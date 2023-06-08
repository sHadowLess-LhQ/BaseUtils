package cn.com.shadowless.baseutils.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.RxLifecycle;
import com.trello.rxlifecycle3.android.FragmentEvent;
import com.trello.rxlifecycle3.android.RxLifecycleAndroid;

import cn.com.shadowless.baseutils.permission.RxPermissions;
import cn.com.shadowless.baseutils.utils.ApplicationUtils;
import cn.com.shadowless.baseutils.utils.ClickUtils;
import cn.com.shadowless.baseutils.utils.RxUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;


/**
 * 基类Fragment
 *
 * @param <VB> the type 视图
 * @param <T>  the type parameter
 * @author sHadowLess
 */
public abstract class BaseFragment<VB extends ViewBinding, T> extends Fragment implements LifecycleProvider<FragmentEvent>, ObservableOnSubscribe<T>, Observer<T>, View.OnClickListener {

    /**
     * The Tag.
     */
    private final String TAG = BaseFragment.class.getSimpleName();
    /**
     * 视图绑定
     */
    private VB bind = null;
    /**
     * 依附的activity
     */
    private Activity mActivity = null;
    /**
     * 行为订阅
     */
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    /**
     * 初始化数据回调接口
     *
     * @param <T> the type parameter
     */
    protected interface InitDataCallBack<T> {
        /**
         * 成功且带数据
         *
         * @param t the t
         */
        void initViewWithData(@NonNull T t);

        /**
         * 成功不带数据
         */
        void initViewWithOutData();
    }


    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <LT> LifecycleTransformer<LT> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <LT> LifecycleTransformer<LT> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bind = setBindView();
        initListener();
        initPermissions(initDataThreadMod());
        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        if (bind != null) {
            bind = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<T> emitter) throws Exception {
        initData(new InitDataCallBack<T>() {
            @Override
            public void initViewWithData(@NonNull T t) {
                emitter.onNext(t);
            }

            @Override
            public void initViewWithOutData() {
                emitter.onComplete();
            }
        });
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T mData) {
        initView(mData);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        initView(null);
        Log.e(TAG, "onError: ", e);
    }

    @Override
    public void onComplete() {
        initView(null);
        Log.e(TAG, "onComplete: " + "Activity加载完成");
    }

    @Override
    public void onClick(View v) {
        if (!ClickUtils.isFastClick()) {
            click(v);
        }
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
    protected abstract VB setBindView();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     *
     * @param callBack the  回调
     */
    protected abstract void initData(@NonNull InitDataCallBack<T> callBack);

    /**
     * 初始化视图
     *
     * @param data the 数据表
     */
    protected abstract void initView(@Nullable T data);

    /**
     * 点击
     *
     * @param v the v
     */
    protected abstract void click(@NonNull View v);

    /**
     * 初始化权限
     *
     * @param threadMod the thread mod
     */
    protected void initPermissions(int threadMod) {
        String[] permissions = permissionName();
        if (null != permissions && permissions.length != 0) {
            new RxPermissions(this)
                    .requestEachCombined(permissions)
                    .compose(RxUtils.bindFragmentTransformer(this))
                    .subscribe(permission -> {
                                if (permission.granted) {
                                    Observable.create(this).compose(RxUtils.bindFragmentTransformer(this)).compose(RxUtils.dealObservableThread(threadMod)).subscribe(this);
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    showToast(permission.name);
                                } else {
                                    showToast(permission.name);
                                }
                            }
                    );
        } else {
            Observable.create(this).compose(RxUtils.bindFragmentTransformer(this)).compose(RxUtils.dealObservableThread(threadMod)).subscribe(this);
        }
    }

    /**
     * 初始化数据所在线程
     *
     * @return the 线程模式
     */
    protected int initDataThreadMod() {
        return RxUtils.IO_TO_MAIN;
    }

    /**
     * 获取绑定视图
     *
     * @return the bind
     */
    @NonNull
    public VB getBindView() {
        return bind;
    }

    /**
     * 获取绑定的activity
     *
     * @return the bind activity
     */
    protected Activity getBindActivity() {
        return mActivity;
    }

    /**
     * 内部权限提示
     *
     * @param name the 权限名
     */
    private void showToast(String name) {
        String tip = "应用无法使用，请开启%s权限";
        Toast.makeText(mActivity, String.format(tip, name), Toast.LENGTH_LONG).show();
        ApplicationUtils.startApplicationInfo(getBindActivity());
    }
}
