package cn.com.shadowless.baseutils.utils;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 视图动画边框工具类
 *
 * @author sHadowLess
 */
public class ViewAnimBorderUtils {

    /**
     * 效果视图
     */
    private View effectView;

    /**
     * 边框颜色
     */
    private String borderColor;

    /**
     * 动画时长
     */
    private int duration;

    /**
     * 圆角半径
     */
    private float cornerRadius;

    /**
     * 边框宽度
     */
    private float borderWidth;

    /**
     * 边框画笔
     */
    private Paint borderPaint;

    /**
     * 边框路径
     */
    private Path borderPath;

    /**
     * 路径测量
     */
    private PathMeasure pathMeasure;

    /**
     * 动画值
     */
    private float animatedValue = 0;

    /**
     * 动画器
     */
    private ValueAnimator animator;

    /**
     * Instantiates a new Anim view border helper.
     *
     * @param effectView   the effect view
     * @param borderColor  the border color
     * @param duration     the duration
     * @param cornerRadius the corner radius
     * @param borderWidth  the border width
     */
    private ViewAnimBorderUtils(View effectView, String borderColor, int duration, float cornerRadius, float borderWidth) {
        this.effectView = effectView;
        this.borderColor = borderColor;
        this.duration = duration;
        this.cornerRadius = cornerRadius;
        this.borderWidth = borderWidth;
    }


    /**
     * Builder anim view border helper builder.
     *
     * @return the anim view border helper builder
     */
    public static AnimViewBorderHelperBuilder builder() {
        return new AnimViewBorderHelperBuilder();
    }


    /**
     * The type Anim view border helper builder.
     */
    public static class AnimViewBorderHelperBuilder {
        /**
         * The Effect view.
         */
        private View effectView;

        /**
         * The Border color.
         */
        private String borderColor;

        /**
         * The Duration.
         */
        private int duration;

        /**
         * 圆角半径
         */
        private float cornerRadius;

        /**
         * 边框宽度
         */
        private float borderWidth;

        /**
         * Base url net utils . net utils builder.
         *
         * @param effectView the effect view
         * @return the net utils . net utils builder
         */
        public AnimViewBorderHelperBuilder effectView(View effectView) {
            this.effectView = effectView;
            return this;
        }

        /**
         * Border color anim view border helper builder.
         *
         * @param borderColor the border color
         * @return the anim view border helper builder
         */
        public AnimViewBorderHelperBuilder borderColor(String borderColor) {
            this.borderColor = borderColor;
            return this;
        }

        /**
         * Duration anim view border helper builder.
         *
         * @param duration the duration
         * @return the anim view border helper builder
         */
        public AnimViewBorderHelperBuilder duration(int duration) {
            this.duration = duration;
            return this;
        }

        /**
         * Corner radius anim view border helper builder.
         *
         * @param cornerRadius the corner radius
         * @return the anim view border helper builder
         */
        public AnimViewBorderHelperBuilder cornerRadius(float cornerRadius) {
            this.cornerRadius = cornerRadius;
            return this;
        }

        /**
         * Border width anim view border helper builder.
         *
         * @param borderWidth the border width
         * @return the anim view border helper builder
         */
        public AnimViewBorderHelperBuilder borderWidth(float borderWidth) {
            this.borderWidth = borderWidth;
            return this;
        }

        /**
         * Build anim view border helper.
         *
         * @return the anim view border helper
         */
        public ViewAnimBorderUtils build() {
            return new ViewAnimBorderUtils(this.effectView, this.borderColor, this.duration, this.cornerRadius, this.borderWidth);
        }
    }

    /**
     * Init.
     */
    public void init() {
        borderPaint = new Paint();
        borderPaint.setColor(TextUtils.isEmpty(borderColor) ? Color.parseColor("#818181") : Color.parseColor(borderColor));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth == 0 ? dp2px(3f) : borderWidth);
        borderPaint.setAntiAlias(true);
        borderPaint.setStrokeJoin(Paint.Join.ROUND);
        borderPath = new Path();
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(duration == 0 ? 2000 : duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            animatedValue = (float) animation.getAnimatedValue();
            effectView.invalidate();
        });
    }

    /**
     * Sets effect view.
     *
     * @param effectView the effect view
     */
    public void setEffectView(View effectView) {
        this.effectView = effectView;
    }

    /**
     * Sets corner radius.
     *
     * @param cornerRadius the corner radius
     */
    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    /**
     * Sets border width.
     *
     * @param borderWidth the border width
     */
    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * Sets border color.
     *
     * @param borderColor the border color
     */
    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Show border.
     */
    public void showBorder() {
        animator.start();
    }

    /**
     * Hide border.
     */
    public void hideBorder() {
        animator.reverse();
    }

    /**
     * On size changed.
     *
     * @param w the w
     * @param h the h
     */
    public void onSizeChanged(int w, int h) {
        RectF rect = new RectF(borderWidth / 2, borderWidth / 2, w - borderWidth / 2, h - borderWidth / 2);
        float realCorner = dp2px(cornerRadius);
        borderPath.addRoundRect(rect, realCorner, realCorner, Path.Direction.CW);
        pathMeasure = new PathMeasure(borderPath, false);
    }

    /**
     * On draw.
     *
     * @param hasBorder the has border
     * @param canvas    the canvas
     */
    public void onDraw(boolean hasBorder, Canvas canvas) {
        if (!hasBorder) {
            return;
        }
        if (pathMeasure != null) {
            float length = pathMeasure.getLength();
            float stop = animatedValue * length;
            Path dst = new Path();
            pathMeasure.getSegment(0, stop, dst, true);
            canvas.drawPath(dst, borderPaint);
        }
    }

    /**
     * Dp 2 px float.
     *
     * @param dpVal the dp val
     * @return the float
     */
    private float dp2px(float dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, Resources.getSystem().getDisplayMetrics());
    }
}
