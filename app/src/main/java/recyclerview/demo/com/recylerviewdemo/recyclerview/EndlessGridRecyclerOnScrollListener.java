package recyclerview.demo.com.recylerviewdemo.recyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * <p/>
 * 自定义RecylcerView上拉加载处理
 */
public abstract class EndlessGridRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private PullLoadHeadFootGridRecyclerView mPullLoadHeadFootRecyclerView;

    protected EndlessGridRecyclerOnScrollListener( PullLoadHeadFootGridRecyclerView pullLoadHeadFootRecyclerView) {
        this.mPullLoadHeadFootRecyclerView = pullLoadHeadFootRecyclerView;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);


        distanceY(recyclerView.computeVerticalScrollOffset());


        //新的监听滑动到底部
        if (isSlideToBottom(recyclerView) && !mPullLoadHeadFootRecyclerView.isLoadMore() && !mPullLoadHeadFootRecyclerView.isNoMore()) {
            mPullLoadHeadFootRecyclerView.setLoadMore(true);
            int currentPage = mPullLoadHeadFootRecyclerView.getCurrentPage();
            currentPage++;
            onLoadMore(currentPage);
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