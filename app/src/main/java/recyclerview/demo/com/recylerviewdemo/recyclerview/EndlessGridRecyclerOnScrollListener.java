package recyclerview.demo.com.recylerviewdemo.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * <p/>
 * 自定义RecylcerView上拉加载处理
 */
public abstract class EndlessGridRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private PullLoadHeadFootGridRecyclerView mPullLoadHeadFootRecyclerView;

    int offsetY = 0;
    int viewHeight = 0;

    protected EndlessGridRecyclerOnScrollListener( PullLoadHeadFootGridRecyclerView pullLoadHeadFootRecyclerView) {
        this.mPullLoadHeadFootRecyclerView = pullLoadHeadFootRecyclerView;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            offsetY+=dy;

            int first = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            int last = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            View firstview = linearLayoutManager.findViewByPosition(first);
            if(viewHeight == 0){
                viewHeight = firstview.getHeight();
            }
            int offseta = firstview.getTop();
            float sx = 1f+(float) offseta/viewHeight;
            if(offsetY == 0){
                View view = linearLayoutManager.findViewByPosition(first+1);
                view.setScaleX(2);
            }

            firstview.setScaleX(sx);
            View lastview = linearLayoutManager.findViewByPosition(last);
            offseta = recyclerView.getHeight()-lastview.getBottom();
            sx = 1f+(float) offseta/viewHeight;
            lastview.setScaleX(sx);
        }


        distanceY(recyclerView.computeVerticalScrollOffset());

        if(dy<=0)return;


        //新的监听滑动到底部
        if (isSlideToBottom(recyclerView) && !mPullLoadHeadFootRecyclerView.isLoadMore() && !mPullLoadHeadFootRecyclerView.isNoMore()) {
            mPullLoadHeadFootRecyclerView.setLoadMore(true);
            int currentPage = mPullLoadHeadFootRecyclerView.getCurrentPage();
            currentPage++;
            onLoadMore(currentPage);
        }
    }


    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if(newState == 0){
                int postion = linearLayoutManager.findFirstVisibleItemPosition();
                View view = linearLayoutManager.findViewByPosition(postion);
                int top = view.getTop();
                int offset = 0;
                if(viewHeight == 0){
                    viewHeight = view.getHeight();
                }
                if(top == 0){
                    return;
                }
                else if(-top < viewHeight/2){
                    offset = top;
                }
                else {
                    offset = viewHeight+top;
                }
                recyclerView.smoothScrollBy(0, offset);
            }

        }

    }

    //监听是否到底部
    private boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }


    public abstract void onLoadMore(int currentPage);
    public abstract void distanceY(int offsetY);


    public int getScollYDistance(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

}