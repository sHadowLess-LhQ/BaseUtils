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
 * The type View animator.
 *
 * @author sHadowLess
 */
public class ViewAnimatorUtils {
    /**
     * The constant RESTART.
     */
    public static final int RESTART = ValueAnimator.RESTART;
    /**
     * The constant REVERSE.
     */
    public static final int REVERSE = ValueAnimator.REVERSE;
    /**
     * The constant INFINITE.
     */
    public static final int INFINITE = ValueAnimator.INFINITE;
    /**
     * The constant DEFAULT_DURATION.
     */
    private static final long DEFAULT_DURATION = 3000;

    /**
     * The Animation list.
     */
    private final List<AnimationBuilder> animationList = new ArrayList<>();
    /**
     * The Duration.
     */
    private long duration = DEFAULT_DURATION;
    /**
     * The Start delay.
     */
    private long startDelay = 0;
    /**
     * The Interpolator.
     */
    private Interpolator interpolator = null;

    /**
     * The Repeat count.
     */
    private int repeatCount = 0;
    /**
     * The Repeat mode.
     */
    private int repeatMode = RESTART;

    /**
     * The Animator set.
     */
    private AnimatorSet animatorSet;
    /**
     * The Wait for this view height.
     */
    private View waitForThisViewHeight = null;

    /**
     * The Start listener.
     */
    private AnimationListener.Start startListener;
    /**
     * The Stop listener.
     */
    private AnimationListener.Stop stopListener;

    /**
     * The Prev.
     */
    private ViewAnimatorUtils prev = null;
    /**
     * The Next.
     */
    private ViewAnimatorUtils next = null;

    /**
     * Create animator set animator set.
     *
     * @return the animator set
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
     * Animate animation builder.
     *
     * @param view the view
     * @return the animation builder
     */
    public static AnimationBuilder animate(View... view) {
        ViewAnimatorUtils viewAnimatorUtils = new ViewAnimatorUtils();
        return viewAnimatorUtils.addAnimationBuilder(view);
    }

    /**
     * Then animate animation builder.
     *
     * @param views the views
     * @return the animation builder
     */
    public AnimationBuilder thenAnimate(View... views) {
        ViewAnimatorUtils nextViewAnimatorUtils = new ViewAnimatorUtils();
        this.next = nextViewAnimatorUtils;
        nextViewAnimatorUtils.prev = this;
        return nextViewAnimatorUtils.addAnimationBuilder(views);
    }

    /**
     * Add animation builder animation builder.
     *
     * @param views the views
     * @return the animation builder
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
     * Start.
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
     * Cancel.
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
     * Duration view animator.
     *
     * @param duration the duration
     * @return the view animator
     */
    public ViewAnimatorUtils duration(@IntRange(from = 1) long duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Start delay view animator.
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
     * Repeat mode view animator.
     *
     * @param repeatMode the repeat mode
     * @return the view animator
     */
    public ViewAnimatorUtils repeatMode(@RepeatMode int repeatMode) {
        this.repeatMode = repeatMode;
        return this;
    }

    /**
     * On start view animator.
     *
     * @param startListener the start listener
     * @return the view animator
     */
    public ViewAnimatorUtils onStart(AnimationListener.Start startListener) {
        this.startListener = startListener;
        return this;
    }

    /**
     * On stop view animator.
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
