package cn.com.shadowless.baseutils.utils;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用滑动方向判断工具类
 * 支持判断任意视图的一指滑动事件方向
 */
public class SwipeDetectorUtils implements View.OnTouchListener {

    /**
     * 滑动方向枚举（八方向）
     */
    public enum SwipeDirection {
        UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, UNKNOWN
    }

    /**
     * 手指信息类
     */
    public static class FingerInfo {
        public int pointerId;           // 手指ID
        public float startX;            // 起始X坐标
        public float startY;            // 起始Y坐标
        public float currentX;          // 当前X坐标
        public float currentY;          // 当前Y坐标
        public boolean isSwiping;       // 是否正在滑动
        public SwipeDirection direction; // 滑动方向

        public FingerInfo(int pointerId, float x, float y) {
            this.pointerId = pointerId;
            this.startX = x;
            this.startY = y;
            this.currentX = x;
            this.currentY = y;
            this.isSwiping = false;
            this.direction = SwipeDirection.UNKNOWN;
        }

        /**
         * 获取X轴滑动距离
         *
         * @return X轴滑动距离
         */
        public float getDistanceX() {
            return currentX - startX;
        }

        /**
         * 获取Y轴滑动距离
         *
         * @return Y轴滑动距离
         */
        public float getDistanceY() {
            return currentY - startY;
        }

        @NonNull
        @Override
        public String toString() {
            return "FingerInfo{" + "pointerId=" + pointerId + ", startX=" + startX + ", startY=" + startY + ", currentX=" + currentX + ", currentY=" + currentY + ", isSwiping=" + isSwiping + ", direction=" + direction + '}';
        }
    }

    /**
     * 滑动方向监听器
     */
    public interface OnSwipeDirectionListener {

        /**
         * 当滑动开始时回调
         *
         * @param fingerInfo 手指信息
         */
        default void onSwipeStart(View v, MotionEvent event, FingerInfo fingerInfo) {

        }

        /**
         * 当检测到滑动方向时回调
         *
         * @param fingerInfo 手指信息
         */
        void onSwiping(View v, MotionEvent event, FingerInfo fingerInfo);

        /**
         * 超出检测范围限制时回调
         *
         * @param fingerInfo 手指信息
         */
        void onOverSwipeDetect(View v, MotionEvent event, FingerInfo fingerInfo);

        /**
         * 当滑动结束时回调
         *
         * @param fingerInfo 手指信息
         */
        default void onSwipeEnd(View v, MotionEvent event, FingerInfo fingerInfo) {

        }
    }

    // 判定为滑动的最小距离（像素）
    private float SWIPE_THRESHOLD = 1.0f;

    // 滑动检测范围限制
    private float maxDetectDistanceX = -1; // X轴最大检测距离，-1表示不限制
    private float maxDetectDistanceY = -1; // Y轴最大检测距离，-1表示不限制

    // 手指信息映射表
    private final Map<Integer, FingerInfo> fingerMap = new HashMap<>();

    // 滑动方向监听器
    private OnSwipeDirectionListener swipeListener;

    /**
     * 设置滑动方向监听器
     *
     * @param listener 监听器
     */
    public void setOnSwipeDirectionListener(OnSwipeDirectionListener listener) {
        this.swipeListener = listener;
    }

    /**
     * 设置X轴最大检测距离
     *
     * @param maxDistance 最大检测距离，-1表示不限制
     */
    public void setMaxDetectDistanceX(float maxDistance) {
        this.maxDetectDistanceX = maxDistance;
    }

    /**
     * 设置Y轴最大检测距离
     *
     * @param maxDistance 最大检测距离，-1表示不限制
     */
    public void setMaxDetectDistanceY(float maxDistance) {
        this.maxDetectDistanceY = maxDistance;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                // 记录新的手指信息
                FingerInfo fingerInfo = new FingerInfo(pointerId, event.getX(pointerIndex), event.getY(pointerIndex));
                fingerMap.put(pointerId, fingerInfo);
                if (swipeListener != null) {
                    swipeListener.onSwipeStart(v, event, fingerInfo);
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                // 处理所有触摸点的移动事件
                int pointerCount = event.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    int id = event.getPointerId(i);
                    FingerInfo info = fingerMap.get(id);
                    if (info != null) {
                        // 更新当前坐标
                        info.currentX = event.getX(i);
                        info.currentY = event.getY(i);
                        // 检查是否超出检测范围限制
                        if ((maxDetectDistanceX > 0 && info.currentX > maxDetectDistanceX) || (maxDetectDistanceY > 0 && info.currentY > maxDetectDistanceY)) {
                            isOverSwipeDetect(v, event, true, info);
                            continue;
                        }
                        isOverSwipeDetect(v, event, false, info);
                    }
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                FingerInfo info = fingerMap.get(pointerId);
                if (info != null) {
                    if (info.isSwiping && swipeListener != null) {
                        swipeListener.onSwipeEnd(v, event, info);
                    }
                    // 移除手指信息
                    fingerMap.remove(pointerId);
                }
                return true;
        }

        return false;
    }

    private void isOverSwipeDetect(View v, MotionEvent event, boolean isOver, FingerInfo info) {
        if (!info.isSwiping) {
            // 计算移动距离
            float deltaX = info.getDistanceX();
            float deltaY = info.getDistanceY();
            // 判断是否达到滑动阈值
            if (deltaX > SWIPE_THRESHOLD || deltaY > SWIPE_THRESHOLD) {
                info.isSwiping = true;
                // 判断滑动方向
                info.direction = calculateSwipeDirection(deltaX, deltaY);

                // 回调监听器
                if (swipeListener != null) {
                    if (isOver) {
                        swipeListener.onOverSwipeDetect(v, event, info);
                    } else {
                        swipeListener.onSwiping(v, event, info);
                    }
                }
            }
        }
    }

    /**
     * 根据移动距离计算滑动方向（八方向）
     *
     * @param deltaX X轴移动距离
     * @param deltaY Y轴移动距离
     * @return 滑动方向
     */
    private SwipeDirection calculateSwipeDirection(float deltaX, float deltaY) {
        // 计算移动距离的绝对值
        float absDeltaX = Math.abs(deltaX);
        float absDeltaY = Math.abs(deltaY);

        // 根据角度判断方向
        if (absDeltaX >= absDeltaY) { // 更偏向水平方向
            if (absDeltaY / (absDeltaX + 0.0001) < 0.414) { // tan(22.5°) ≈ 0.414
                // 纯水平方向
                return deltaX > 0 ? SwipeDirection.RIGHT : SwipeDirection.LEFT;
            } else {
                // 对角线方向
                if (deltaX > 0 && deltaY < 0) {
                    return SwipeDirection.UP_RIGHT;
                } else if (deltaX > 0 && deltaY > 0) {
                    return SwipeDirection.DOWN_RIGHT;
                } else if (deltaX < 0 && deltaY < 0) {
                    return SwipeDirection.UP_LEFT;
                } else {
                    return SwipeDirection.DOWN_LEFT;
                }
            }
        } else { // 更偏向垂直方向
            if (absDeltaX / (absDeltaY + 0.0001) < 0.414) { // tan(22.5°) ≈ 0.414
                // 纯垂直方向
                return deltaY > 0 ? SwipeDirection.DOWN : SwipeDirection.UP;
            } else {
                // 对角线方向
                if (deltaX > 0 && deltaY < 0) {
                    return SwipeDirection.UP_RIGHT;
                } else if (deltaX > 0 && deltaY > 0) {
                    return SwipeDirection.DOWN_RIGHT;
                } else if (deltaX < 0 && deltaY < 0) {
                    return SwipeDirection.UP_LEFT;
                } else {
                    return SwipeDirection.DOWN_LEFT;
                }
            }
        }
    }

    /**
     * 设置双轴最小滑动距离
     *
     * @param min 最小滑动距离
     */
    public void setThresholdLimit(int min) {
        this.SWIPE_THRESHOLD = min;
    }
}