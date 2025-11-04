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
 * <p>
 * 使用方法：
 * 1. 创建SwipeDetectorUtils实例
 * 2. 设置监听器setOnSwipeDirectionListener
 * 3. 将该实例设置为View的OnTouchListener
 * <p>
 * 功能特点：
 * - 支持8个方向的滑动检测（上下左右和四个对角线方向）
 * - 支持多点触控
 * - 可设置滑动阈值
 * - 可限制检测范围
 * - 可统计各方向滑动手指数量
 */
public class SwipeDetectorUtils implements View.OnTouchListener {

    /**
     * 滑动方向枚举（八方向）
     * 包括四个基本方向和四个对角线方向
     */
    public enum SwipeDirection {
        /** 上滑 */
        UP,
        /** 下滑 */
        DOWN,
        /** 左滑 */
        LEFT,
        /** 右滑 */
        RIGHT,
        /** 左上滑 */
        UP_LEFT,
        /** 右上滑 */
        UP_RIGHT,
        /** 左下滑 */
        DOWN_LEFT,
        /** 右下滑 */
        DOWN_RIGHT,
        /** 未知方向 */
        UNKNOWN
    }

    /**
     * 手指信息类
     * 用于存储和跟踪每个触摸点的信息
     */
    public static class FingerInfo {
        /** 手指ID，用于区分多点触控中的不同手指 */
        public int pointerId;
        /** 触摸起始X坐标 */
        public float startX;
        /** 触摸起始Y坐标 */
        public float startY;
        /** 当前X坐标 */
        public float currentX;
        /** 当前Y坐标 */
        public float currentY;
        /** 是否正在滑动 */
        public boolean isSwiping;
        /** 滑动方向 */
        public SwipeDirection direction;

        /**
         * 构造函数
         *
         * @param pointerId 手指ID
         * @param x         X坐标
         * @param y         Y坐标
         */
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
     * 用于接收滑动事件的回调
     */
    public interface OnSwipeDirectionListener {

        /**
         * 当滑动开始时回调
         * 在手指按下屏幕时触发
         *
         * @param v          触发事件的View
         * @param event      MotionEvent对象
         * @param fingerInfo 手指信息
         */
        default void onSwipeStart(View v, MotionEvent event, FingerInfo fingerInfo) {

        }

        /**
         * 当检测到滑动方向时回调
         * 在手指移动且达到滑动阈值时触发
         *
         * @param v          触发事件的View
         * @param event      MotionEvent对象
         * @param fingerInfo 手指信息
         */
        void onSwiping(View v, MotionEvent event, FingerInfo fingerInfo);

        /**
         * 超出检测范围限制时回调
         * 当手指移动超出设定的检测范围时触发
         *
         * @param v          触发事件的View
         * @param event      MotionEvent对象
         * @param fingerInfo 手指信息
         */
        void onOverSwipeDetect(View v, MotionEvent event, FingerInfo fingerInfo);

        /**
         * 当滑动结束时回调
         * 在手指离开屏幕时触发
         *
         * @param v          触发事件的View
         * @param event      MotionEvent对象
         * @param fingerInfo 手指信息
         */
        default void onSwipeEnd(View v, MotionEvent event, FingerInfo fingerInfo) {

        }

        /**
         * 当方向手指更新时回调
         * 当各方向的滑动手指发生变化时触发
         *
         * @param direction 滑动方向
         * @param count     该方向上的手指数量
         */
        default void onDirectionFingerCountUpdated(int touchType, SwipeDirection direction, int count) {

        }
    }

    /** 滑动检测阈值，默认为1.0f */
    private float SWIPE_THRESHOLD = 1.0f;
    /** X轴最大检测距离，-1表示不限制 */
    private float maxDetectDistanceX = -1;
    /** Y轴最大检测距离，-1表示不限制 */
    private float maxDetectDistanceY = -1;
    /** 存储手指信息的映射表，键为手指ID，值为手指信息 */
    private final Map<Integer, FingerInfo> fingerMap = new HashMap<>();
    /** 存储各方向滑动次数的映射表，键为滑动方向，值为次数 */
    private final Map<SwipeDirection, Integer> directionCountMap = new HashMap<>();
    /** 滑动方向监听器 */
    private OnSwipeDirectionListener swipeListener;
    /** 是否为长距离检测模式 */
    private boolean isLongDetect = false;

    /**
     * 设置是否为长距离检测模式
     * 长距离检测模式下，即使已经开始滑动也会继续检测方向变化
     *
     * @param longDetect true为长距离检测模式，false为普通模式
     */
    public void setIsLongDetect(boolean longDetect) {
        isLongDetect = longDetect;
    }

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

    /**
     * 处理触摸事件
     * 根据不同的触摸动作执行相应的处理逻辑
     *
     * @param v     触发事件的View
     * @param event MotionEvent对象
     * @return 是否消费该事件
     */
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
                    // 减少对应方向的计数
                    if (info.isSwiping && info.direction != null && info.direction != SwipeDirection.UNKNOWN) {
                        updateDirectionCount(action, info.direction, false);
                    }

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

    /**
     * 更新方向计数
     * 增加或减少指定方向的滑动计数
     *
     * @param direction 滑动方向
     * @param increment true为增加计数，false为减少计数
     */
    private void updateDirectionCount(int touchType, SwipeDirection direction, boolean increment) {
        if (direction == null || direction == SwipeDirection.UNKNOWN) {
            return;
        }
        int count = 0;
        Integer temp = directionCountMap.get(direction);
        if (temp != null) {
            count = temp;
        }
        if (increment) {
            count++;
        } else {
            count = Math.max(0, count - 1);
        }
        directionCountMap.put(direction, count);
        // 通知监听器方向计数已更新
        if (swipeListener != null) {
            swipeListener.onDirectionFingerCountUpdated(touchType, direction, count);
        }
    }

    /**
     * 检查是否超出滑动检测范围
     * 根据是否为长距离检测模式决定处理方式
     *
     * @param v      触发事件的View
     * @param event  MotionEvent对象
     * @param isOver 是否超出检测范围
     * @param info   手指信息
     */
    private void isOverSwipeDetect(View v, MotionEvent event, boolean isOver, FingerInfo info) {
        if (isLongDetect) {
            calculate(v, event, isOver, info);
            return;
        }
        if (!info.isSwiping) {
            calculate(v, event, isOver, info);
        }
    }

    /**
     * 计算滑动方向
     * 根据手指移动距离判断滑动方向并触发相应回调
     *
     * @param v      触发事件的View
     * @param event  MotionEvent对象
     * @param isOver 是否超出检测范围
     * @param info   手指信息
     */
    private void calculate(View v, MotionEvent event, boolean isOver, FingerInfo info) {
        // 计算移动距离
        float deltaX = info.getDistanceX();
        float deltaY = info.getDistanceY();
        int touchType = event.getActionMasked();
        // 判断是否达到滑动阈值
        if (Math.abs(deltaX) > SWIPE_THRESHOLD || Math.abs(deltaY) > SWIPE_THRESHOLD) {
            // 如果之前未滑动，需要减少原方向计数
            if (!info.isSwiping && info.direction != null && info.direction != SwipeDirection.UNKNOWN) {
                updateDirectionCount(touchType, info.direction, false);
            }

            info.isSwiping = true;
            // 保存旧方向
            SwipeDirection oldDirection = info.direction;
            // 判断滑动方向
            info.direction = calculateSwipeDirection(deltaX, deltaY);

            // 如果方向改变，更新计数
            if (oldDirection != info.direction) {
                if (oldDirection != null && oldDirection != SwipeDirection.UNKNOWN) {
                    updateDirectionCount(touchType, oldDirection, false);
                }
                if (info.direction != null && info.direction != SwipeDirection.UNKNOWN) {
                    updateDirectionCount(touchType, info.direction, true);
                }
            }

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

    /**
     * 根据移动距离计算滑动方向（八方向）
     * 使用角度判断法确定滑动方向
     *
     * @param deltaX X轴移动距离（带符号）
     * @param deltaY Y轴移动距离（带符号）
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
     * 设置双轴最小滑动距离阈值
     * 当手指移动距离超过该阈值时才被认为是滑动
     *
     * @param min 最小滑动距离
     */
    public void setThresholdLimit(int min) {
        this.SWIPE_THRESHOLD = min;
    }
}