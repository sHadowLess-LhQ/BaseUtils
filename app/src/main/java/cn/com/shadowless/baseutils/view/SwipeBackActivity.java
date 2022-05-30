package cn.com.shadowless.baseutils.view;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;

import cn.com.shadowless.baseutils.R;

/**
 * The type Swipe back activity.
 *
 * @author sHadowLess
 */
public class SwipeBackActivity extends AppCompatActivity implements SwipeBackLayout.SwipeBackListener {

    /**
     * The constant DEFAULT_DRAG_EDGE.
     */
    private static final SwipeBackLayout.DragEdge DEFAULT_DRAG_EDGE = SwipeBackLayout.DragEdge.LEFT;
    /**
     * The Swipe back layout.
     */
    private SwipeBackLayout swipeBackLayout;
    /**
     * The Iv shadow.
     */
    private ImageView ivShadow;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(getContainer());
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        swipeBackLayout.addView(view);
    }

    /**
     * Gets container.
     *
     * @return the container
     */
    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setDragEdge(DEFAULT_DRAG_EDGE);
        swipeBackLayout.setOnSwipeBackListener(this);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.black_p50));
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        return container;
    }

    /**
     * Sets enable swipe.
     *
     * @param enableSwipe the enable swipe
     */
    public void setEnableSwipe(boolean enableSwipe) {
        swipeBackLayout.setEnablePullToBack(enableSwipe);
    }

    /**
     * Sets drag edge.
     *
     * @param dragEdge the drag edge
     */
    public void setDragEdge(SwipeBackLayout.DragEdge dragEdge) {
        swipeBackLayout.setDragEdge(dragEdge);
    }

    /**
     * Sets drag direct mode.
     *
     * @param dragDirectMode the drag direct mode
     */
    public void setDragDirectMode(SwipeBackLayout.DragDirectMode dragDirectMode) {
        swipeBackLayout.setDragDirectMode(dragDirectMode);
    }

    /**
     * Gets swipe back layout.
     *
     * @return the swipe back layout
     */
    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackLayout;
    }

    /**
     * Sets on finish listener.
     *
     * @param onFinishListener the on finish listener
     */
    public void setOnFinishListener(SwipeBackLayout.OnFinishListener onFinishListener) {
        swipeBackLayout.setOnFinishListener(onFinishListener);
    }

    @Override
    public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
        ivShadow.setAlpha(1 - fractionScreen);
    }

}
