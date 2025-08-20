package cn.com.shadowless.baseutils.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.atomic.AtomicBoolean;

public class SwipeViewUtils implements
        RecyclerView.OnItemTouchListener, SwipeDetectorUtils.OnSwipeDirectionListener {

    private RecyclerView recyclerView;
    private final SwipeDetectorUtils detector;
    private final AtomicBoolean isSwiping = new AtomicBoolean(false);
    private int contentId = -1;
    private int swipeViewId = -1;
    private final SwipeDetectorUtils.SwipeDirection swipeDirection;
    private SwipeDetectorUtils.SwipeDirection cancelSwipeDirection;
    private RecyclerView.ViewHolder viewHolder = null;

    public SwipeViewUtils(SwipeDetectorUtils.SwipeDirection swipeDirection) {
        this.swipeDirection = swipeDirection;
        setCancelSwipeDirection(swipeDirection);
        this.detector = new SwipeDetectorUtils();
        this.detector.setOnSwipeDirectionListener(this);
    }

    public void setThresholdLimit(int limit) {
        this.detector.setThresholdLimit(limit);
    }

    public void setMaxDetectDistanceX(int x) {
        this.detector.setMaxDetectDistanceX(x);
    }

    private void setCancelSwipeDirection(SwipeDetectorUtils.SwipeDirection swipeDirection) {
        switch (swipeDirection) {
            case LEFT:
                cancelSwipeDirection = SwipeDetectorUtils.SwipeDirection.RIGHT;
                break;
            case RIGHT:
                cancelSwipeDirection = SwipeDetectorUtils.SwipeDirection.LEFT;
                break;
            case UP:
            case DOWN:
                throw new IllegalArgumentException("swipeDirection cannot be UP or DOWN");
            default:
                throw new IllegalArgumentException("swipeDirection cannot be null");
        }
    }

    public void attachToRecyclerView(RecyclerView recyclerView, int swipeViewId) {
        attachToRecyclerView(recyclerView, -1, swipeViewId);
    }

    public void attachToRecyclerView(RecyclerView recyclerView, int contentId, int swipeViewId) {
        if (swipeViewId == 0 || swipeViewId == -1) {
            throw new IllegalArgumentException("swipeViewId cannot be 0 or -1");
        }
        this.recyclerView = recyclerView;
        this.contentId = contentId;
        this.swipeViewId = swipeViewId;
        this.recyclerView.addOnItemTouchListener(this);
    }

    public boolean isSwiping() {
        return isSwiping.get();
    }

    public void resetViewStateByPosition(int position) {
        RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
        if (holder == null) {
            return;
        }
        View v = holder.itemView;
        View swipeView = v.findViewById(swipeViewId);
        View contentView = null;
        if (contentId != 0 || swipeViewId != -1) {
            contentView = v.findViewById(contentId);
        }
        if (contentView != null) {
            translationXAnimateEvent(contentView, 0);
        }
        translationXAnimateEvent(swipeView, 0);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (view != null) {
                    viewHolder = rv.getChildViewHolder(view);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                viewHolder = null;
                break;
        }
        if (viewHolder != null) {
            detector.onTouch(viewHolder.itemView, e);
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public void onSwiping(View v, MotionEvent event, SwipeDetectorUtils.FingerInfo fingerInfo) {
        View swipeView = v.findViewById(swipeViewId);
        int swipeLength = swipeView.getMeasuredWidth();
        View contentView = null;
        if (contentId != 0 || swipeViewId != -1) {
            contentView = v.findViewById(contentId);
        }
        if (fingerInfo.direction == swipeDirection) {
            setValue(fingerInfo.direction, contentView, swipeView, swipeLength);
        } else if (fingerInfo.direction == cancelSwipeDirection) {
            setValue(fingerInfo.direction, contentView, swipeView, 0);
        }
    }

    @Override
    public void onOverSwipeDetect(View v, MotionEvent event, SwipeDetectorUtils.FingerInfo fingerInfo) {

    }

    private void setValue(SwipeDetectorUtils.SwipeDirection direction, View contentView, View swipeView, int swipeLength) {
        switch (direction) {
            case LEFT:
                if (contentView != null) {
                    translationXAnimateEvent(contentView, -swipeLength);
                }
                translationXAnimateEvent(swipeView, -swipeLength);
                break;
            case RIGHT:
                if (contentView != null) {
                    translationXAnimateEvent(contentView, swipeLength);
                }
                translationXAnimateEvent(swipeView, swipeLength);
                break;
            default:
                break;
        }
    }

    private void translationXAnimateEvent(View view, int swipeLength) {
        view
                .animate()
                .translationX(swipeLength)
                .setInterpolator(new LinearInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        isSwiping.set(true);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isSwiping.set(false);
                    }
                })
                .setDuration(200)
                .start();
    }
}
