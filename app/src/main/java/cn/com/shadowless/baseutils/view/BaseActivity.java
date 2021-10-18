package cn.com.shadowless.baseutils.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.shadowless.baseutils.utils.RxUtils;
import io.reactivex.ObservableEmitter;

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
        RxUtils
                .builder()
                .build()
                .rxCreate(RxUtils.ThreadSign.DEFAULT, this, this);
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
        Log.d("TAG", "onDestroy");
        super.onDestroy();
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
     * Sets layout.
     *
     * @return the layout
     */
    protected abstract int setLayout();

    /**
     * Sets data.
     *
     * @return the data
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