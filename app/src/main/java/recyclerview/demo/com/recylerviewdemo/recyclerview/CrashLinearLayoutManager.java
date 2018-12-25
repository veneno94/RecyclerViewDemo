package recyclerview.demo.com.recylerviewdemo.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by lenovo on 2018/1/8.
 */

public class CrashLinearLayoutManager extends LinearLayoutManager {

    public CrashLinearLayoutManager(Context context) {
        super(context);
    }

    public CrashLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CrashLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
