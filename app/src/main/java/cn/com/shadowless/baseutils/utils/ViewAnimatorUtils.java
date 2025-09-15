package cn.com.shadowless.baseutils.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;

import androidx.annotation.IntDef;
import androidx.annotation.IntRange;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import cn.com.shadowless.baseutils.anim.AnimationBuilder;
import cn.com.shadowless.baseutils.anim.AnimationListener;


/**
 * 视图动画工具类
 *
 * @author sHadowLess
 */
public class ViewAnimatorUtils {
    /**
     * 重新开始常量
     */
    public static final int RESTART = ValueAnimator.RESTART;
    /**
     * 反向常量
     */
    public static final int REVERSE = ValueAnimator.REVERSE;
    /**
     * 无限循环常量
     */
    public static final int INFINITE = ValueAnimator.INFINITE;
    /**
     * 默认时长
     */
    private static final long DEFAULT_DURATION = 3000;

    /**
     * 动画列表
     */
    private final List<AnimationBuilder> animationList = new ArrayList<>();
    /**
     * 动画时长
     */
    private long duration = DEFAULT_DURATION;
    /**
     * 开始延迟
     */
    private long startDelay = 0;
    /**
     * 插值器
     */
    private Interpolator interpolator = null;

    /**
     * 重复次数
     */
    private int repeatCount = 0;
    /**
     * 重复模式
     */
    private int repeatMode = RESTART;

    /**
     * 动画集合
     */
    private AnimatorSet animatorSet;
    /**
     * 等待视图高度
     */
    private View waitForThisViewHeight = null;

    /**
     * 开始监听器
     */
    private AnimationListener.Start startListener;
    /**
     * 结束监听器
     */
    private AnimationListener.Stop stopListener;

    /**
     * 前一个动画
     */
    private ViewAnimatorUtils prev = null;
    /**
     * 后一个动画
     */
    private ViewAnimatorUtils next = null;

    /**
     * 创建动画集合
     *
     * @return 动画集合
     */
    protected AnimatorSet createAnimatorSet() {
        List<Animator> animators = new ArrayList<>();
        for (AnimationBuilder animationBuilder : animationList) {
            List<Animator> animatorList = animationBuilder.createAnimators();
            if (animationBuilder.getSingleInterpolator() != null) {
                for (Animator animator : animatorList) {
                    animator.setInterpolator(animationBuilder.getSingleInterpolator());
                }
            }
            animators.addAll(animatorList);
        }

        for (AnimationBuilder animationBuilder : animationList) {
            if (animationBuilder.isWaitForHeight()) {
                waitForThisViewHeight = animationBuilder.getView();
                break;
            }
        }

        for (Animator animator : animators) {
            if (animator instanceof ValueAnimator) {
                ValueAnimator valueAnimator = (ValueAnimator) animator;
                valueAnimator.setRepeatCount(repeatCount);
                valueAnimator.setRepeatMode(repeatMode);
            }
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animators);

        animatorSet.setDuration(duration);
        animatorSet.setStartDelay(startDelay);
        if (interpolator != null) {
            animatorSet.setInterpolator(interpolator);
        }

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (startListener != null) {
                    startListener.onStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (stopListener != null) {
                    stopListener.onStop();
                }
                if (next != null) {
                    next.prev = null;
                    next.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        return animatorSet;
    }

    /**
     * 创建动画构建器
     *
     * @param view 视图
     * @return 动画构建器
     */
    public static AnimationBuilder animate(View... view) {
        ViewAnimatorUtils viewAnimatorUtils = new ViewAnimatorUtils();
        return viewAnimatorUtils.addAnimationBuilder(view);
    }

    /**
     * 创建下一个动画构建器
     *
     * @param views 视图
     * @return 动画构建器
     */
    public AnimationBuilder thenAnimate(View... views) {
        ViewAnimatorUtils nextViewAnimatorUtils = new ViewAnimatorUtils();
        this.next = nextViewAnimatorUtils;
        nextViewAnimatorUtils.prev = this;
        return nextViewAnimatorUtils.addAnimationBuilder(views);
    }

    /**
     * 添加动画构建器
     *
     * @param views 视图
     * @return 动画构建器
     */
    public AnimationBuilder addAnimationBuilder(View... views) {
        AnimationBuilder animationBuilder = new AnimationBuilder(this, views);
        animationList.add(animationBuilder);
        return animationBuilder;
    }

    /**
     * -1 or INFINITE will repeat forever
     *
     * @param repeatCount the repeat count
     * @return the view animator
     */
    public ViewAnimatorUtils repeatCount(@IntRange(from = -1) int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    /**
     * 开始动画
     */
    public void start() {
        if (prev != null) {
            prev.start();
        } else {
            animatorSet = createAnimatorSet();

            if (waitForThisViewHeight != null) {
                waitForThisViewHeight.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        animatorSet.start();
                        waitForThisViewHeight.getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                });
            } else {
                animatorSet.start();
            }
        }
    }

    /**
     * 取消动画
     */
    public void cancel() {
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        if (next != null) {
            next.cancel();
            next = null;
        }
    }

    /**
     * 设置动画时长
     *
     * @param duration the duration
     * @return the view animator
     */
    public ViewAnimatorUtils duration(@IntRange(from = 1) long duration) {
        this.duration = duration;
        return this;
    }

    /**
     * 设置开始延迟
     *
     * @param startDelay the start delay
     * @return the view animator
     */
    public ViewAnimatorUtils startDelay(@IntRange(from = 0) long startDelay) {
        this.startDelay = startDelay;
        return this;
    }

    /**
     * The interface Repeat mode.
     */
    @IntDef(value = {RESTART, REVERSE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {
        // use this instead enum, I heard that the enumeration will take a lot of memory
    }

    /**
     * 设置重复模式
     *
     * @param repeatMode the repeat mode
     * @return the view animator
     */
    public ViewAnimatorUtils repeatMode(@RepeatMode int repeatMode) {
        this.repeatMode = repeatMode;
        return this;
    }

    /**
     * 设置开始监听器
     *
     * @param startListener the start listener
     * @return the view animator
     */
    public ViewAnimatorUtils onStart(AnimationListener.Start startListener) {
        this.startListener = startListener;
        return this;
    }

    /**
     * 设置结束监听器
     *
     * @param stopListener the stop listener
     * @return the view animator
     */
    public ViewAnimatorUtils onStop(AnimationListener.Stop stopListener) {
        this.stopListener = stopListener;
        return this;
    }

    /**
     * see https://github.com/cimi-chen/EaseInterpolator
     *
     * @param interpolator the interpolator
     * @return the view animator
     */
    public ViewAnimatorUtils interpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

}
