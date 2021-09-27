package cn.com.shadowless.baseutils.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.shadowless.baseutils.R;
import cn.com.shadowless.baseutils.adpater.BaseCustomAdapter;
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
        BaseCustomAdapter<List<String>, BaseViewHolder> baseCustomAdapter = new BaseCustomAdapter<>(
                R.layout._xpopup_adapter_text,
                new ArrayList<>(),
                (viewHolder, strings) -> {

                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        Log.e("TAG", "onDestroy: ");
        super.onDestroy();
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

}