package cn.com.shadowless.baseutils.anim;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import androidx.annotation.IntRange;

import java.util.ArrayList;
import java.util.List;

import cn.com.shadowless.baseutils.utils.ViewAnimatorUtils;

/**
 * The type Animation builder.
 */
public class AnimationBuilder {
    /**
     * The View animator.
     */
    private final ViewAnimatorUtils viewAnimatorUtils;
    /**
     * The Views.
     */
    private final View[] views;
    /**
     * The Animator list.
     */
    private final List<Animator> animatorList = new ArrayList<>();
    /**
     * The Wait for height.
     */
    private boolean waitForHeight;
    /**
     * The Next value will be dp.
     */
    private boolean nextValueWillBeDp = false;
    /**
     * The Single interpolator.
     */
    private Interpolator singleInterpolator = null;

    /**
     * Instantiates a new Animation builder.
     *
     * @param viewAnimatorUtils the view animator
     * @param views        the views
     */
    public AnimationBuilder(ViewAnimatorUtils viewAnimatorUtils, View... views) {
        this.viewAnimatorUtils = viewAnimatorUtils;
        this.views = views;
    }

    /**
     * Dp animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder dp() {
        nextValueWillBeDp = true;
        return this;
    }

    /**
     * Add animation builder.
     *
     * @param animator the animator
     * @return the animation builder
     */
    protected AnimationBuilder add(Animator animator) {
        this.animatorList.add(animator);
        return this;
    }

    /**
     * To dp float.
     *
     * @param px the px
     * @return the float
     */
    protected float toDp(final float px) {
        return px / views[0].getContext().getResources().getDisplayMetrics().density;
    }

    /**
     * To px float.
     *
     * @param dp the dp
     * @return the float
     */
    protected float toPx(final float dp) {
        return dp * views[0].getContext().getResources().getDisplayMetrics().density;
    }

    /**
     * Get values float [ ].
     *
     * @param values the values
     * @return the float [ ]
     */
    protected float[] getValues(float... values) {
        if (!nextValueWillBeDp) {
            return values;
        }
        float[] pxValues = new float[values.length];
        for (int i = 0; i < values.length; ++i) {
            pxValues[i] = toPx(values[i]);
        }
        return pxValues;
    }

    /**
     * Property animation builder.
     *
     * @param propertyName the property name
     * @param values       the values
     * @return the animation builder
     */
    public AnimationBuilder property(String propertyName, float... values) {
        for (View view : views) {
            this.animatorList.add(ObjectAnimator.ofFloat(view, propertyName, getValues(values)));
        }
        return this;
    }

    /**
     * Translation y animation builder.
     *
     * @param y the y
     * @return the animation builder
     */
    public AnimationBuilder translationY(float... y) {
        return property("translationY", y);
    }

    /**
     * Translation x animation builder.
     *
     * @param x the x
     * @return the animation builder
     */
    public AnimationBuilder translationX(float... x) {
        return property("translationX", x);
    }

    /**
     * Alpha animation builder.
     *
     * @param alpha the alpha
     * @return the animation builder
     */
    public AnimationBuilder alpha(float... alpha) {
        return property("alpha", alpha);
    }

    /**
     * Scale x animation builder.
     *
     * @param scaleX the scale x
     * @return the animation builder
     */
    public AnimationBuilder scaleX(float... scaleX) {
        return property("scaleX", scaleX);
    }

    /**
     * Scale y animation builder.
     *
     * @param scaleY the scale y
     * @return the animation builder
     */
    public AnimationBuilder scaleY(float... scaleY) {
        return property("scaleY", scaleY);
    }

    /**
     * Scale animation builder.
     *
     * @param scale the scale
     * @return the animation builder
     */
    public AnimationBuilder scale(float... scale) {
        scaleX(scale);
        scaleY(scale);
        return this;
    }

    /**
     * Pivot x animation builder.
     *
     * @param pivotX the pivot x
     * @return the animation builder
     */
    public AnimationBuilder pivotX(float pivotX) {
        for (View view : views) {
            view.setPivotX(pivotX);
        }
        return this;
    }

    /**
     * Pivot y animation builder.
     *
     * @param pivotY the pivot y
     * @return the animation builder
     */
    public AnimationBuilder pivotY(float pivotY) {
        for (View view : views) {
            view.setPivotY(pivotY);
        }
        return this;
    }

    /**
     * Pivot x animation builder.
     *
     * @param pivotX the pivot x
     * @return the animation builder
     */
    public AnimationBuilder pivotX(float... pivotX) {
        ObjectAnimator.ofFloat(getView(), "pivotX", getValues(pivotX));
        return this;
    }

    /**
     * Pivot y animation builder.
     *
     * @param pivotY the pivot y
     * @return the animation builder
     */
    public AnimationBuilder pivotY(float... pivotY) {
        ObjectAnimator.ofFloat(getView(), "pivotY", getValues(pivotY));
        return this;
    }

    /**
     * Rotation x animation builder.
     *
     * @param rotationX the rotation x
     * @return the animation builder
     */
    public AnimationBuilder rotationX(float... rotationX) {
        return property("rotationX", rotationX);
    }

    /**
     * Rotation y animation builder.
     *
     * @param rotationY the rotation y
     * @return the animation builder
     */
    public AnimationBuilder rotationY(float... rotationY) {
        return property("rotationY", rotationY);
    }

    /**
     * Rotation animation builder.
     *
     * @param rotation the rotation
     * @return the animation builder
     */
    public AnimationBuilder rotation(float... rotation) {
        return property("rotation", rotation);
    }

    /**
     * Background color animation builder.
     *
     * @param colors the colors
     * @return the animation builder
     */
    public AnimationBuilder backgroundColor(int... colors) {
        for (View view : views) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view, "backgroundColor", colors);
            objectAnimator.setEvaluator(new ArgbEvaluator());
            this.animatorList.add(objectAnimator);
        }
        return this;
    }

    /**
     * Text color animation builder.
     *
     * @param colors the colors
     * @return the animation builder
     */
    public AnimationBuilder textColor(int... colors) {
        for (View view : views) {
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(textView, "textColor", colors);
                objectAnimator.setEvaluator(new ArgbEvaluator());
                this.animatorList.add(objectAnimator);
            }
        }
        return this;
    }

    /**
     * Custom animation builder.
     *
     * @param update the update
     * @param values the values
     * @return the animation builder
     */
    public AnimationBuilder custom(final AnimationListener.Update update, float... values) {
        for (final View view : views) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(getValues(values));
            if (update != null) {
                valueAnimator.addUpdateListener(animation -> {
                    //noinspection unchecked
                    update.update(view, (Float) animation.getAnimatedValue());
                });
            }
            add(valueAnimator);
        }
        return this;
    }

    /**
     * Height animation builder.
     *
     * @param height the height
     * @return the animation builder
     */
    public AnimationBuilder height(float... height) {
        return custom((view, value) -> {
            view.getLayoutParams().height = (int) value;
            view.requestLayout();
        }, height);
    }

    /**
     * Width animation builder.
     *
     * @param width the width
     * @return the animation builder
     */
    public AnimationBuilder width(float... width) {
        return custom((view, value) -> {
            view.getLayoutParams().width = (int) value;
            view.requestLayout();
        }, width);
    }

    /**
     * Wait for height animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder waitForHeight() {
        waitForHeight = true;
        return this;
    }

    /**
     * Create animators list.
     *
     * @return the list
     */
    public List<Animator> createAnimators() {
        return animatorList;
    }

    /**
     * And animate animation builder.
     *
     * @param views the views
     * @return the animation builder
     */
    public AnimationBuilder andAnimate(View... views) {
        return viewAnimatorUtils.addAnimationBuilder(views);
    }

    /**
     * Then animate animation builder.
     *
     * @param views the views
     * @return the animation builder
     */
    public AnimationBuilder thenAnimate(View... views) {
        return viewAnimatorUtils.thenAnimate(views);
    }

    /**
     * Duration animation builder.
     *
     * @param duration the duration
     * @return the animation builder
     */
    public AnimationBuilder duration(@IntRange(from = 1) long duration) {
        viewAnimatorUtils.duration(duration);
        return this;
    }

    /**
     * Start delay animation builder.
     *
     * @param startDelay the start delay
     * @return the animation builder
     */
    public AnimationBuilder startDelay(@IntRange(from = 0) long startDelay) {
        viewAnimatorUtils.startDelay(startDelay);
        return this;
    }

    /**
     * Repeat count animation builder.
     *
     * @param repeatCount the repeat count
     * @return the animation builder
     */
    public AnimationBuilder repeatCount(@IntRange(from = -1) int repeatCount) {
        viewAnimatorUtils.repeatCount(repeatCount);
        return this;
    }

    /**
     * Repeat mode animation builder.
     *
     * @param repeatMode the repeat mode
     * @return the animation builder
     */
    public AnimationBuilder repeatMode(@ViewAnimatorUtils.RepeatMode int repeatMode) {
        viewAnimatorUtils.repeatMode(repeatMode);
        return this;
    }

    /**
     * On start animation builder.
     *
     * @param startListener the start listener
     * @return the animation builder
     */
    public AnimationBuilder onStart(AnimationListener.Start startListener) {
        viewAnimatorUtils.onStart(startListener);
        return this;
    }

    /**
     * On stop animation builder.
     *
     * @param stopListener the stop listener
     * @return the animation builder
     */
    public AnimationBuilder onStop(AnimationListener.Stop stopListener) {
        viewAnimatorUtils.onStop(stopListener);
        return this;
    }

    /**
     * Interpolator animation builder.
     *
     * @param interpolator the interpolator
     * @return the animation builder
     */
    public AnimationBuilder interpolator(Interpolator interpolator) {
        viewAnimatorUtils.interpolator(interpolator);
        return this;
    }

    /**
     * Single interpolator animation builder.
     *
     * @param interpolator the interpolator
     * @return the animation builder
     */
    public AnimationBuilder singleInterpolator(Interpolator interpolator) {
        singleInterpolator = interpolator;
        return this;
    }

    /**
     * Gets single interpolator.
     *
     * @return the single interpolator
     */
    public Interpolator getSingleInterpolator() {
        return singleInterpolator;
    }

    /**
     * Accelerate view animator.
     *
     * @return the view animator
     */
    public ViewAnimatorUtils accelerate() {
        return viewAnimatorUtils.interpolator(new AccelerateInterpolator());
    }

    /**
     * Decelerate view animator.
     *
     * @return the view animator
     */
    public ViewAnimatorUtils decelerate() {
        return viewAnimatorUtils.interpolator(new DecelerateInterpolator());
    }

    /**
     * Start.
     *
     * @return the view animator
     */
    public ViewAnimatorUtils start() {
        viewAnimatorUtils.start();
        return viewAnimatorUtils;
    }

    /**
     * Get views view [ ].
     *
     * @return the view [ ]
     */
    public View[] getViews() {
        return views;
    }

    /**
     * Gets view.
     *
     * @return the view
     */
    public View getView() {
        return views[0];
    }

    /**
     * Is wait for height boolean.
     *
     * @return the boolean
     */
    public boolean isWaitForHeight() {
        return waitForHeight;
    }

    /**
     * Bounce animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder bounce() {
        return translationY(0, 0, -30, 0, -15, 0, 0);
    }

    /**
     * Bounce in animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder bounceIn() {
        alpha(0, 1, 1, 1);
        scaleX(0.3f, 1.05f, 0.9f, 1);
        scaleY(0.3f, 1.05f, 0.9f, 1);
        return this;
    }

    /**
     * Bounce out animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder bounceOut() {
        scaleY(1, 0.9f, 1.05f, 0.3f);
        scaleX(1, 0.9f, 1.05f, 0.3f);
        alpha(1, 1, 1, 0);
        return this;
    }

    /**
     * Fade in animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder fadeIn() {
        return alpha(0, 0.25f, 0.5f, 0.75f, 1);
    }

    /**
     * Fade out animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder fadeOut() {
        return alpha(1, 0.75f, 0.5f, 0.25f, 0);
    }

    /**
     * Flash animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder flash() {
        return alpha(1, 0, 1, 0, 1);
    }

    /**
     * Flip horizontal animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder flipHorizontal() {
        return rotationX(90, -15, 15, 0);
    }

    /**
     * Flip vertical animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder flipVertical() {
        return rotationY(90, -15, 15, 0);
    }

    /**
     * Pulse animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder pulse() {
        scaleY(1, 1.1f, 1);
        scaleX(1, 1.1f, 1);
        return this;
    }

    /**
     * Roll in animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder rollIn() {
        for (View view : views) {
            alpha(0, 1);
            translationX(-(view.getWidth() - view.getPaddingLeft() - view.getPaddingRight()), 0);
            rotation(-120, 0);
        }
        return this;
    }

    /**
     * Roll out animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder rollOut() {
        for (View view : views) {
            alpha(1, 0);
            translationX(0, view.getWidth());
            rotation(0, 120);
        }
        return this;
    }

    /**
     * Rubber animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder rubber() {
        scaleX(1, 1.25f, 0.75f, 1.15f, 1);
        scaleY(1, 0.75f, 1.25f, 0.85f, 1);
        return this;
    }

    /**
     * Shake animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder shake() {
        translationX(0, 25, -25, 25, -25, 15, -15, 6, -6, 0);
        interpolator(new CycleInterpolator(5));
        return this;
    }

    /**
     * Stand up animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder standUp() {
        for (View view : views) {
            float x = (view.getWidth() - view.getPaddingLeft() - view.getPaddingRight()) / 2
                    + view.getPaddingLeft();
            float y = view.getHeight() - view.getPaddingBottom();
            pivotX(x, x, x, x, x);
            pivotY(y, y, y, y, y);
            rotationX(55, -30, 15, -15, 0);
        }
        return this;
    }

    /**
     * Swing animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder swing() {
        return rotation(0, 10, -10, 6, -6, 3, -3, 0);
    }

    /**
     * Tada animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder tada() {
        scaleX(1, 0.9f, 0.9f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1);
        scaleY(1, 0.9f, 0.9f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1);
        rotation(0, -3, -3, 3, -3, 3, -3, 3, -3, 0);
        return this;
    }

    /**
     * Wave animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder wave() {
        for (View view : views) {
            float x = (view.getWidth() - view.getPaddingLeft() - view.getPaddingRight()) / 2
                    + view.getPaddingLeft();
            float y = view.getHeight() - view.getPaddingBottom();
            rotation(12, -12, 3, -3, 0);
            pivotX(x, x, x, x, x);
            pivotY(y, y, y, y, y);
        }
        return this;
    }

    /**
     * Wobble animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder wobble() {
        for (View view : views) {
            float width = view.getWidth();
            float one = (float) (width / 100.0);
            translationX(0 * one, -25 * one, 20 * one, -15 * one, 10 * one, -5 * one, 0 * one, 0);
            rotation(0, -5, 3, -3, 2, -1, 0);
        }
        return this;
    }

    /**
     * Zoom in animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder zoomIn() {
        scaleX(0.45f, 1);
        scaleY(0.45f, 1);
        alpha(0, 1);
        return this;
    }

    /**
     * Zoom out animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder zoomOut() {
        scaleX(1, 0.3f, 0);
        scaleY(1, 0.3f, 0);
        alpha(1, 0, 0);
        return this;
    }

    /**
     * Fall animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder fall() {
        rotation(1080, 720, 360, 0);
        return this;
    }

    /**
     * News paper animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder newsPaper() {
        alpha(0, 1);
        scaleX(0.1f, 0.5f, 1);
        scaleY(0.1f, 0.5f, 1);
        return this;
    }

    /**
     * Slit animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder slit() {
        rotationY(90, 88, 88, 45, 0);
        alpha(0, 0.4f, 0.8f, 1);
        scaleX(0, 0.5f, 0.9f, 0.9f, 1);
        scaleY(0, 0.5f, 0.9f, 0.9f, 1);
        return this;
    }

    /**
     * Slide left in animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder slideLeftIn() {
        translationX(-300, 0);
        alpha(0, 1);
        return this;
    }

    /**
     * Slide right in animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder slideRightIn() {
        translationX(300, 0);
        alpha(0, 1);
        return this;
    }

    /**
     * Slide top in animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder slideTopIn() {
        translationY(-300, 0);
        alpha(0, 1);
        return this;
    }

    /**
     * Slide bottom in animation builder.
     *
     * @return the animation builder
     */
    public AnimationBuilder slideBottomIn() {
        translationY(300, 0);
        alpha(0, 1);
        return this;
    }

    /**
     * Path animation builder.
     *
     * @param path the path
     * @return the animation builder
     */
    public AnimationBuilder path(Path path) {
        if (path == null) {
            return this;
        }
        final PathMeasure pathMeasure = new PathMeasure(path, false);
        return custom((view, value) -> {
            if (view == null) {
                return;
            }
            float[] currentPosition = new float[2];
            pathMeasure.getPosTan(value, currentPosition, null);
            final float x = currentPosition[0];
            final float y = currentPosition[1];
            view.setX(x);
            view.setY(y);
            Log.d(null, "path: value=" + value + ", x=" + x + ", y=" + y);
        }, 0, pathMeasure.getLength());
    }

}
