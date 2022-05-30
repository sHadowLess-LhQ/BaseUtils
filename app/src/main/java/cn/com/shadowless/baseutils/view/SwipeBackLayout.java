/*
 * Copyright 2015 Eric Liu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.com.shadowless.baseutils.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ScrollView;

import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.viewpager.widget.ViewPager;

/**
 * The type Swipe back layout.
 *
 * @author sHadowLess
 */
public class SwipeBackLayout extends ViewGroup {

    /**
     * The constant TAG.
     */
    private static final String TAG = "SwipeBackLayout";

    /**
     * The enum Drag direct mode.
     */
    public enum DragDirectMode {
        /**
         * Edge drag direct mode.
         */
        EDGE,
        /**
         * Vertical drag direct mode.
         */
        VERTICAL,
        /**
         * Horizontal drag direct mode.
         */
        HORIZONTAL
    }

    /**
     * The enum Drag edge.
     */
    public enum DragEdge {
        /**
         * Left drag edge.
         */
        LEFT,

        /**
         * Top drag edge.
         */
        TOP,

        /**
         * Right drag edge.
         */
        RIGHT,

        /**
         * Bottom drag edge.
         */
        BOTTOM
    }

    /**
     * The Drag direct mode.
     */
    private DragDirectMode dragDirectMode = DragDirectMode.EDGE;

    /**
     * The Drag edge.
     */
    private DragEdge dragEdge = DragEdge.TOP;

    /**
     * Sets drag edge.
     *
     * @param dragEdge the drag edge
     */
    public void setDragEdge(DragEdge dragEdge) {
        this.dragEdge = dragEdge;
    }

    /**
     * Sets drag direct mode.
     *
     * @param dragDirectMode the drag direct mode
     */
    public void setDragDirectMode(DragDirectMode dragDirectMode) {
        this.dragDirectMode = dragDirectMode;
        if (dragDirectMode == DragDirectMode.VERTICAL) {
            this.dragEdge = DragEdge.TOP;
        } else if (dragDirectMode == DragDirectMode.HORIZONTAL) {
            this.dragEdge = DragEdge.LEFT;
        }
    }

    /**
     * The constant AUTO_FINISHED_SPEED_LIMIT.
     */
    private static final double AUTO_FINISHED_SPEED_LIMIT = 2000.0;

    /**
     * The View drag helper.
     */
    private ViewDragHelper viewDragHelper;

    /**
     * The Target.
     */
    private View target;

    /**
     * The Scroll child.
     */
    private View scrollChild;

    /**
     * The Vertical drag range.
     */
    private int verticalDragRange = 0;

    /**
     * The Horizontal drag range.
     */
    private int horizontalDragRange = 0;

    /**
     * The Dragging state.
     */
    private int draggingState = 0;

    /**
     * The Dragging offset.
     */
    private int draggingOffset;

    /**
     * Whether allow to pull this layout.
     */
    private boolean enablePullToBack = true;

    /**
     * The constant BACK_FACTOR.
     */
    private static final float BACK_FACTOR = 0.5f;

    /**
     * the anchor of calling finish.
     */
    private float finishAnchor = 0;

    /**
     * Set the anchor of calling finish.
     *
     * @param offset the offset
     */
    public void setFinishAnchor(float offset) {
        finishAnchor = offset;
    }

    /**
     * The Enable fling back.
     */
    private boolean enableFlingBack = true;

    /**
     * Whether allow to finish activity by fling the layout.
     *
     * @param b the b
     */
    public void setEnableFlingBack(boolean b) {
        enableFlingBack = b;
    }

    /**
     * The Swipe back listener.
     */
    private SwipeBackListener swipeBackListener;

    /**
     * Sets on pull to back listener.
     *
     * @param listener the listener
     */
    @Deprecated
    public void setOnPullToBackListener(SwipeBackListener listener) {
        swipeBackListener = listener;
    }

    /**
     * Sets on swipe back listener.
     *
     * @param listener the listener
     */
    public void setOnSwipeBackListener(SwipeBackListener listener) {
        swipeBackListener = listener;
    }

    /**
     * Instantiates a new Swipe back layout.
     *
     * @param context the context
     */
    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new Swipe back layout.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public SwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelperCallBack());
        chkDragable();
    }

    /**
     * This should be added first than any other setting, because it will overwrite the viewDragHelper
     *
     * @param onFinishListener listener for what to do when view reach the end
     */
    public void setOnFinishListener(OnFinishListener onFinishListener) {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelperCallBack(onFinishListener));
    }

    /**
     * The Last y.
     */
    float lastY = 0;
    /**
     * The New y.
     */
    float newY = 0;
    /**
     * The Offset y.
     */
    float offsetY = 0;

    /**
     * The Last x.
     */
    float lastX = 0;
    /**
     * The New x.
     */
    float newX = 0;
    /**
     * The Offset x.
     */
    float offsetX = 0;

    /**
     * Chk dragable.
     */
    private void chkDragable() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    lastY = motionEvent.getRawY();
                    lastX = motionEvent.getRawX();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    newY = motionEvent.getRawY();
                    lastX = motionEvent.getRawX();

                    offsetY = Math.abs(newY - lastY);
                    lastY = newY;

                    offsetX = Math.abs(newX - lastX);
                    lastX = newX;

                    switch (dragEdge) {
                        case TOP:
                        case BOTTOM:
                            setEnablePullToBack(offsetY > offsetX);
                        case LEFT:
                        case RIGHT:
                            setEnablePullToBack(offsetY < offsetX);
                            break;
                    }
                }

                return false;
            }
        });
    }

    /**
     * Sets scroll child.
     *
     * @param view the view
     */
    public void setScrollChild(View view) {
        scrollChild = view;
    }

    /**
     * Sets enable pull to back.
     *
     * @param b the b
     */
    public void setEnablePullToBack(boolean b) {
        enablePullToBack = b;
        Log.i(TAG, "enablePullToBack:" + enablePullToBack);
    }

    /**
     * Ensure target.
     */
    private void ensureTarget() {
        if (target == null) {
            if (getChildCount() > 1) {
                throw new IllegalStateException("SwipeBackLayout must contains only one direct child");
            }
            target = getChildAt(0);

            if (scrollChild == null && target != null) {
                if (target instanceof ViewGroup) {
                    findScrollView((ViewGroup) target);
                } else {
                    scrollChild = target;
                }

            }
        }
    }

    /**
     * Find out the scrollable child view from a ViewGroup.
     *
     * @param viewGroup the view group
     */
    private void findScrollView(ViewGroup viewGroup) {
        scrollChild = viewGroup;
        if (viewGroup.getChildCount() > 0) {
            int count = viewGroup.getChildCount();
            View child;
            for (int i = 0; i < count; i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof AbsListView || child instanceof ScrollView || child instanceof ViewPager || child instanceof WebView) {
                    scrollChild = child;
                    return;
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (getChildCount() == 0) return;

        View child = getChildAt(0);

        int childWidth = width - getPaddingLeft() - getPaddingRight();
        int childHeight = height - getPaddingTop() - getPaddingBottom();
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int childRight = childLeft + childWidth;
        int childBottom = childTop + childHeight;
        child.layout(childLeft, childTop, childRight, childBottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() > 1) {
            throw new IllegalStateException("SwipeBackLayout must contains only one direct child.");
        }

        if (getChildCount() > 0) {
            int measureWidth = MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY);
            int measureHeight = MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY);
            getChildAt(0).measure(measureWidth, measureHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        verticalDragRange = h;
        horizontalDragRange = w;

        switch (dragEdge) {
            case TOP:
            case BOTTOM:
                finishAnchor = finishAnchor > 0 ? finishAnchor : verticalDragRange * BACK_FACTOR;
                break;
            case LEFT:
            case RIGHT:
                finishAnchor = finishAnchor > 0 ? finishAnchor : horizontalDragRange * BACK_FACTOR;
                break;
        }
    }

    /**
     * Gets drag range.
     *
     * @return the drag range
     */
    private int getDragRange() {
        switch (dragEdge) {
            case TOP:
            case BOTTOM:
                return verticalDragRange;
            case LEFT:
            case RIGHT:
                return horizontalDragRange;
            default:
                return verticalDragRange;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean handled = false;
        ensureTarget();
        if (isEnabled()) {
            handled = viewDragHelper.shouldInterceptTouchEvent(ev);
        } else {
            viewDragHelper.cancel();
        }
        return !handled ? super.onInterceptTouchEvent(ev) : handled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * Can child scroll up boolean.
     *
     * @return the boolean
     */
    public boolean canChildScrollUp() {
        return ViewCompat.canScrollVertically(scrollChild, -1);
    }

    /**
     * Can child scroll down boolean.
     *
     * @return the boolean
     */
    public boolean canChildScrollDown() {
        return ViewCompat.canScrollVertically(scrollChild, 1);
    }

    /**
     * Can child scroll right boolean.
     *
     * @return the boolean
     */
    private boolean canChildScrollRight() {
        return ViewCompat.canScrollHorizontally(scrollChild, -1);
    }

    /**
     * Can child scroll left boolean.
     *
     * @return the boolean
     */
    private boolean canChildScrollLeft() {
        return ViewCompat.canScrollHorizontally(scrollChild, 1);
    }

    /**
     * Finish.
     */
    private void finish() {
        Activity act = (Activity) getContext();
        act.finish();
        act.overridePendingTransition(0, android.R.anim.fade_out);
    }

    /**
     * The type View drag helper call back.
     */
    private class ViewDragHelperCallBack extends ViewDragHelper.Callback {

        /**
         * The On finish listener.
         */
        private OnFinishListener onFinishListener = new OnFinishListener() {
            @Override
            public void onFinishState() {
                finish();
            }
        };

        /**
         * Instantiates a new View drag helper call back.
         */
        public ViewDragHelperCallBack() {
        }

        /**
         * Instantiates a new View drag helper call back.
         *
         * @param onFinishListener the on finish listener
         */
        public ViewDragHelperCallBack(OnFinishListener onFinishListener) {
            this.onFinishListener = onFinishListener;
        }

        /**
         * Try capture view boolean.
         *
         * @param child     the child
         * @param pointerId the pointer id
         * @return the boolean
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == target && enablePullToBack;
        }

        /**
         * Gets view vertical drag range.
         *
         * @param child the child
         * @return the view vertical drag range
         */
        @Override
        public int getViewVerticalDragRange(View child) {
            return verticalDragRange;
        }

        /**
         * Gets view horizontal drag range.
         *
         * @param child the child
         * @return the view horizontal drag range
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return horizontalDragRange;
        }

        /**
         * Clamp view position vertical int.
         *
         * @param child the child
         * @param top   the top
         * @param dy    the dy
         * @return the int
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {

            int result = 0;

            if (dragDirectMode == DragDirectMode.VERTICAL) {
                if (!canChildScrollUp() && top > 0) {
                    dragEdge = DragEdge.TOP;
                } else if (!canChildScrollDown() && top < 0) {
                    dragEdge = DragEdge.BOTTOM;
                }
            }

            if (dragEdge == DragEdge.TOP && !canChildScrollUp() && top > 0) {
                final int topBound = getPaddingTop();
                final int bottomBound = verticalDragRange;
                result = Math.min(Math.max(top, topBound), bottomBound);
            } else if (dragEdge == DragEdge.BOTTOM && !canChildScrollDown() && top < 0) {
                final int topBound = -verticalDragRange;
                final int bottomBound = getPaddingTop();
                result = Math.min(Math.max(top, topBound), bottomBound);
            }

            return result;
        }

        /**
         * Clamp view position horizontal int.
         *
         * @param child the child
         * @param left  the left
         * @param dx    the dx
         * @return the int
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            int result = 0;

            if (dragDirectMode == DragDirectMode.HORIZONTAL) {
                if (!canChildScrollRight() && left > 0) {
                    dragEdge = DragEdge.LEFT;
                } else if (!canChildScrollLeft() && left < 0) {
                    dragEdge = DragEdge.RIGHT;
                }
            }

            if (dragEdge == DragEdge.LEFT && !canChildScrollRight() && left > 0) {
                final int leftBound = getPaddingLeft();
                final int rightBound = horizontalDragRange;
                result = Math.min(Math.max(left, leftBound), rightBound);
            } else if (dragEdge == DragEdge.RIGHT && !canChildScrollLeft() && left < 0) {
                final int leftBound = -horizontalDragRange;
                final int rightBound = getPaddingLeft();
                result = Math.min(Math.max(left, leftBound), rightBound);
            }

            return result;
        }

        /**
         * On view drag state changed.
         *
         * @param state the state
         */
        @Override
        public void onViewDragStateChanged(int state) {
            if (state == draggingState) return;

            if ((draggingState == ViewDragHelper.STATE_DRAGGING || draggingState == ViewDragHelper.STATE_SETTLING) &&
                    state == ViewDragHelper.STATE_IDLE) {
                // the view stopped from moving.
                if (draggingOffset == getDragRange()) {
                    onFinishListener.onFinishState();
                }
            }

            draggingState = state;
        }

        /**
         * On view position changed.
         *
         * @param changedView the changed view
         * @param left        the left
         * @param top         the top
         * @param dx          the dx
         * @param dy          the dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            switch (dragEdge) {
                case TOP:
                case BOTTOM:
                    draggingOffset = Math.abs(top);
                    break;
                case LEFT:
                case RIGHT:
                    draggingOffset = Math.abs(left);
                    break;
                default:
                    break;
            }

            //The proportion of the sliding.
            float fractionAnchor = (float) draggingOffset / finishAnchor;
            if (fractionAnchor >= 1) fractionAnchor = 1;

            float fractionScreen = (float) draggingOffset / (float) getDragRange();
            if (fractionScreen >= 1) fractionScreen = 1;

            if (swipeBackListener != null) {
                swipeBackListener.onViewPositionChanged(fractionAnchor, fractionScreen);
            }
        }

        /**
         * On view released.
         *
         * @param releasedChild the released child
         * @param xvel          the xvel
         * @param yvel          the yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (draggingOffset == 0) return;

            if (draggingOffset == getDragRange()) return;

            boolean isBack = false;

            if (enableFlingBack && backBySpeed(xvel, yvel)) {
                isBack = !canChildScrollUp();
            } else if (draggingOffset >= finishAnchor) {
                isBack = true;
            } else if (draggingOffset < finishAnchor) {
                isBack = false;
            }

            int finalLeft;
            int finalTop;
            switch (dragEdge) {
                case LEFT:
                    finalLeft = isBack ? horizontalDragRange : 0;
                    smoothScrollToX(finalLeft);
                    break;
                case RIGHT:
                    finalLeft = isBack ? -horizontalDragRange : 0;
                    smoothScrollToX(finalLeft);
                    break;
                case TOP:
                    finalTop = isBack ? verticalDragRange : 0;
                    smoothScrollToY(finalTop);
                    break;
                case BOTTOM:
                    finalTop = isBack ? -verticalDragRange : 0;
                    smoothScrollToY(finalTop);
                    break;
                default:
                    break;
            }

        }
    }

    /**
     * Back by speed boolean.
     *
     * @param xvel the xvel
     * @param yvel the yvel
     * @return the boolean
     */
    private boolean backBySpeed(float xvel, float yvel) {
        switch (dragEdge) {
            case TOP:
            case BOTTOM:
                if (Math.abs(yvel) > Math.abs(xvel) && Math.abs(yvel) > AUTO_FINISHED_SPEED_LIMIT) {
                    return dragEdge == DragEdge.TOP ? !canChildScrollUp() : !canChildScrollDown();
                }
                break;
            case LEFT:
            case RIGHT:
                if (Math.abs(xvel) > Math.abs(yvel) && Math.abs(xvel) > AUTO_FINISHED_SPEED_LIMIT) {
                    return dragEdge == DragEdge.LEFT ? !canChildScrollLeft() : !canChildScrollRight();
                }
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * Smooth scroll to x.
     *
     * @param finalLeft the final left
     */
    private void smoothScrollToX(int finalLeft) {
        if (viewDragHelper.settleCapturedViewAt(finalLeft, 0)) {
            ViewCompat.postInvalidateOnAnimation(SwipeBackLayout.this);
        }
    }

    /**
     * Smooth scroll to y.
     *
     * @param finalTop the final top
     */
    private void smoothScrollToY(int finalTop) {
        if (viewDragHelper.settleCapturedViewAt(0, finalTop)) {
            ViewCompat.postInvalidateOnAnimation(SwipeBackLayout.this);
        }
    }

    /**
     * The interface On finish listener.
     */
    public interface OnFinishListener {

        /**
         * On finish state.
         */
        void onFinishState();
    }

    /**
     * The interface Swipe back listener.
     */
    public interface SwipeBackListener {

        /**
         * Return scrolled fraction of the layout.
         *
         * @param fractionAnchor relative to the anchor.
         * @param fractionScreen relative to the screen.
         */
        void onViewPositionChanged(float fractionAnchor, float fractionScreen);

    }

}
