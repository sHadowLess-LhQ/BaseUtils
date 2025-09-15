package cn.com.shadowless.baseutils.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 拖拽视图工具类
 */
public class DragViewUtils extends ItemTouchHelper.SimpleCallback {

    private DragDataCallback dataCallback;
    private OnItemDragListener dragCallback;
    private boolean isDrag = false;
    private final ItemTouchHelper helper;

    /**
     * 拖拽数据回调接口
     */
    public interface DragDataCallback {
        /**
         * 数据移动
         *
         * @param fromPosition 起始位置
         * @param toPosition   目标位置
         */
        void dataMove(int fromPosition, int toPosition);
    }

    /**
     * 项目拖拽监听器接口
     */
    public interface OnItemDragListener {
        /**
         * 项目拖拽开始
         *
         * @param viewHolder 视图持有者
         * @param pos        位置
         */
        void onItemDragStart(@Nullable RecyclerView.ViewHolder viewHolder, int pos);

        /**
         * 项目拖拽移动中
         *
         * @param source 源视图持有者
         * @param from   起始位置
         * @param target 目标视图持有者
         * @param to     目标位置
         */
        void onItemDragMoving(@NonNull RecyclerView.ViewHolder source, int from, @NonNull RecyclerView.ViewHolder target, int to);

        /**
         * 项目拖拽结束
         *
         * @param viewHolder 视图持有者
         * @param pos        位置
         */
        void onItemDragEnd(@NonNull RecyclerView.ViewHolder viewHolder, int pos);
    }

    /**
     * 构造函数
     *
     * @param dragDirs 拖拽方向
     */
    public DragViewUtils(int dragDirs) {
        super(dragDirs, 0);
        this.helper = new ItemTouchHelper(this);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            isDrag = true;
            if (dragCallback != null) {
                dragCallback.onItemDragStart(viewHolder, getViewHolderPosition(viewHolder));
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return viewHolder.getItemViewType() == target.getItemViewType();
    }

    @Override
    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
        int fromPosition = viewHolder.getBindingAdapterPosition();
        int toPosition = target.getBindingAdapterPosition();

        if (fromPosition == RecyclerView.NO_POSITION || toPosition == RecyclerView.NO_POSITION) {
            return;
        }

        if (dataCallback != null) {
            dataCallback.dataMove(fromPosition, toPosition);
        }
        if (dragCallback != null) {
            dragCallback.onItemDragMoving(viewHolder, fromPosition, target, toPosition);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (isDrag) {
            if (dragCallback != null) {
                dragCallback.onItemDragEnd(viewHolder, getViewHolderPosition(viewHolder));
            }
            isDrag = false;
        }
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    public void attachToRecyclerView(RecyclerView recyclerView) {
        this.helper.attachToRecyclerView(recyclerView);
    }

    public void setDataCallback(DragDataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

    public void setDragCallback(OnItemDragListener dragCallback) {
        this.dragCallback = dragCallback;
    }

    private int getViewHolderPosition(RecyclerView.ViewHolder holder) {
        if (holder == null) {
            return RecyclerView.NO_POSITION;
        }
        return holder.getAbsoluteAdapterPosition();
    }
}
