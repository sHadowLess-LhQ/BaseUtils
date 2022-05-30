package cn.com.shadowless.baseutils.view.swipe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;


/**
 * The type Temporary view group.
 *
 * @author sHadowLess
 */
public class TemporaryViewGroup extends FrameLayout {

    /**
     * The constant SHADOW_WIDTH.
     */
    private static final int SHADOW_WIDTH = 50;
    /**
     * The M shadow drawable.
     */
    private final Drawable mShadowDrawable;
    /**
     * The M screen width.
     */
    private final int mScreenWidth;

    /**
     * The M previous view.
     */
    private WeakReference<View> mPreviousView;
    /**
     * The M status bar offset.
     */
    private int mStatusBarOffset;
    /**
     * The M distance x.
     */
    private float mDistanceX;

    /**
     * Instantiates a new Temporary view group.
     *
     * @param context the context
     */
    public TemporaryViewGroup(Context context) {
        super(context);
        int colors[] = {0x00000000, 0x17000000, 0x43000000};
        mShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        mScreenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * Add previous view.
     *
     * @param currentContentView  the current content view
     * @param previousContentView the previous content view
     * @param previousView        the previous view
     */
    public void addPreviousView(ViewGroup currentContentView, ViewGroup previousContentView, View previousView) {
        mPreviousView = new WeakReference<>(previousView);

        int height = getContext().getResources().getDisplayMetrics().heightPixels;
        int previousViewHeight = previousContentView.getMeasuredHeight();
        int currentViewHeight = currentContentView.getMeasuredHeight();
        boolean isCurrentFull = currentViewHeight == height;
        boolean isPreviousFull = previousViewHeight == height;
        if (isCurrentFull) {
            mStatusBarOffset = isPreviousFull ? 0 : (height - previousViewHeight);
        } else {
            mStatusBarOffset = isPreviousFull ? -(height - currentViewHeight) : 0;
        }

        final MarginLayoutParams previousParams = (MarginLayoutParams) previousView.getLayoutParams();
        previousParams.topMargin = previousParams.topMargin + mStatusBarOffset;
        addView(previousView, 0, previousParams);
        previousView.setTranslationX(-(float) mScreenWidth / 3);
    }

    /**
     * Clear previous view.
     *
     * @param previewContentView the preview content view
     * @param previousView       the previous view
     */
    public void clearPreviousView(ViewGroup previewContentView, View previousView) {
        final MarginLayoutParams previousParams = (MarginLayoutParams) previousView.getLayoutParams();
        previousParams.topMargin = previousParams.topMargin - mStatusBarOffset;
        removeView(previousView);
        if (previewContentView != null) {
            previewContentView.addView(previousView, previousParams);
        }
    }

    /**
     * Sets distance x.
     *
     * @param distanceX the distance x
     */
    public void setDistanceX(float distanceX) {
        mDistanceX = distanceX;

        invalidate();
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (mDistanceX < mScreenWidth && mPreviousView != null && mPreviousView.get() == child) {
            // draw previous view
            final int restoreCount = canvas.save();
            canvas.clipRect(0, 0, mDistanceX, getMeasuredHeight());
            boolean result = super.drawChild(canvas, child, drawingTime);

            // draw shadow view
            canvas.translate(mDistanceX - SHADOW_WIDTH, 0);
            mShadowDrawable.setBounds(0, 0, SHADOW_WIDTH, getMeasuredHeight());
            mShadowDrawable.draw(canvas);

            canvas.restoreToCount(restoreCount);
            return result;
        }

        return super.drawChild(canvas, child, drawingTime);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        // draw cached view
        if (mDistanceX >= mScreenWidth && mPreviousView != null && mPreviousView.get() != null) {
            final int restoreCount = canvas.save();
            canvas.translate(0, mStatusBarOffset);
            mPreviousView.get().draw(canvas);
            canvas.restoreToCount(restoreCount);
        }
    }
}
