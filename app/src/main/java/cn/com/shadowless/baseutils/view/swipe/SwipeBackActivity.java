package cn.com.shadowless.baseutils.view.swipe;

import android.app.Activity;
import android.view.MotionEvent;

import androidx.fragment.app.FragmentActivity;

import cn.com.guoguang.yjy.gg_ssprint.R;


/**
 * Created by fhf11991 on 2016/7/25.
 */
public class SwipeBackActivity extends FragmentActivity implements cn.com.guoguang.yjy.gg_ssprint.view.SwipeBackHelper.SlideBackManager {

    private cn.com.guoguang.yjy.gg_ssprint.view.SwipeBackHelper mSwipeBackHelper;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!supportSlideBack()) {
            return super.dispatchTouchEvent(ev);
        }
        if (mSwipeBackHelper == null) {
            mSwipeBackHelper = new cn.com.guoguang.yjy.gg_ssprint.view.SwipeBackHelper(this, new SlideActivityAdapter());
            mSwipeBackHelper.setOnSlideFinishListener(new cn.com.guoguang.yjy.gg_ssprint.view.SwipeBackHelper.OnSlideFinishListener() {
                @Override
                public void onFinish() {
                    SwipeBackActivity.this.finish();
                    overridePendingTransition(android.R.anim.fade_in, R.anim.hold_on);
                }
            });
        }
        return mSwipeBackHelper.processTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    @Override
    public void finish() {
        if (mSwipeBackHelper != null) {
            mSwipeBackHelper.finishSwipeImmediately();
        }
        super.finish();
    }

    @Override
    public boolean supportSlideBack() {
        return true;
    }

    @Override
    public boolean canBeSlideBack() {
        return true;
    }

    private static class SlideActivityAdapter implements cn.com.guoguang.yjy.gg_ssprint.view.SlideActivityCallback {

        @Override
        public Activity getPreviousActivity() {
            return cn.com.guoguang.yjy.gg_ssprint.view.ActivityLifecycleHelper.getPreviousActivity();
        }
    }
}
