package recyclerview.demo.com.recylerviewdemo.touch;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.Collections;
import java.util.List;

/**
 */
public class ItemTouchCallback<T> extends ItemTouchHelper.SimpleCallback {
    private RecyclerView.Adapter mAdapter;
    private List<T> dataList;

    public ItemTouchCallback(RecyclerView.Adapter mAdapter, List<T> dataList, int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        this.mAdapter = mAdapter;
        this.dataList = dataList;
    }

    /**
     * @param recyclerView
     * @param viewHolder   拖动的ViewHolder
     * @param target       目标位置的ViewHolder
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
        int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
        if (fromPosition < toPosition) {
            //分别把中间所有的item的位置重新交换
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(dataList, i, target.getAdapterPosition());
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(dataList, i, target.getAdapterPosition());
            }
        }
        mAdapter.notifyItemMoved(fromPosition, toPosition);

        //mAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        dataList.remove(position);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //左右滑动时改变Item的透明度
            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        //当选中Item时候会调用该方法，重写此方法可以实现选中时候的一些动画逻辑
        Log.v("zxy", "onSelectedChanged");
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        //当动画已经结束的时候调用该方法，重写此方法可以实现恢复Item的初始状态
        Log.v("zxy", "clearView");
        mAdapter.notifyDataSetChanged();
    }
}
