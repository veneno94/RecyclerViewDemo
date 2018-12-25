package recyclerview.demo.com.recylerviewdemo.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import recyclerview.demo.com.recylerviewdemo.R;


/**
 * Created by lenovo on 2017/7/27.
 */

public class PullLoadHeadFootGridRecyclerView extends LinearLayout implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Context mContext;
    private HeaderViewGridRecyclerAdapter mAdapter;
    private View loadMoreView;
    private boolean isLoading = true;

    private int previousTotal = 0;
    private int currentPage = 1;

    private boolean isLoadMore = false;
    private boolean isNoMore = false;//没有更多了
    private ProgressBar mProgressBar;
    private TextView mLoadTv;
    private LinearLayout loadMoreLl;


    public PullLoadHeadFootGridRecyclerView(Context context) {
        this(context, null);
    }

    public PullLoadHeadFootGridRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullLoadHeadFootGridRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pull_loadmore_head_foot_layout, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);


        mRecyclerView.addOnScrollListener(new EndlessGridRecyclerOnScrollListener(this) {
            @Override
            public void onLoadMore(int i) {
                if (mPullLoadMoreListener != null) {
                    loadMoreLl.setVisibility(View.VISIBLE);
                    mPullLoadMoreListener.onLoadMore();
                }
            }

            @Override
            public void distanceY(int offsetY) {
                if (distanceListener != null) {
                    distanceListener.distanceY(offsetY);
                }
            }
        });

        this.addView(view);
    }

    @Override
    public void onRefresh() {
        setNoMore(false);
        setIsLoading(true);
        setPreviousTotal(0);
        setCurrentPage(0);
        if (mProgressBar != null && mLoadTv != null) {
            loadMoreLl.setVisibility(GONE);
            mProgressBar.setVisibility(VISIBLE);
            mLoadTv.setText("正在加载中...");
        }
        if (mPullLoadMoreListener != null) {
            mPullLoadMoreListener.onRefresh();
        }
    }


    public void setLinearLayout() {
        CrashLinearLayoutManager linearLayoutManager = new CrashLinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }


    /**
     * GridLayoutManager
     */

    public void setGridLayout(int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, spanCount);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    /**
     * StaggeredGridLayoutManager
     */
    public void setStaggeredGridLayout(int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mAdapter = new HeaderViewGridRecyclerAdapter(adapter);
        mRecyclerView.setAdapter(mAdapter);
        loadMoreView = LayoutInflater.from(mContext).inflate(R.layout.layout_load_more, mRecyclerView, false);
        loadMoreLl = (LinearLayout) loadMoreView.findViewById(R.id.load_more_ll);
        mProgressBar = (ProgressBar) loadMoreView.findViewById(R.id.load_pro);
        mLoadTv = (TextView) loadMoreView.findViewById(R.id.load_tv);
        mAdapter.addFooterView(loadMoreView);
        loadMoreLl.setVisibility(View.GONE);

    }


    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        if (this.mRecyclerView != null) {
            this.mRecyclerView.addItemDecoration(itemDecoration);
        }

    }

    public int getPreviousTotal() {
        return previousTotal;
    }

    public void setPreviousTotal(int previousTotal) {
        this.previousTotal = previousTotal;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void addHeadView(View headView) {
        if (mAdapter != null) {
            mAdapter.addHeaderView(headView);
        }
    }


    public void setRefreshing(boolean isRefreshing) {
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
        if (mPullLoadMoreListener != null) {
            mPullLoadMoreListener.onRefresh();
        }
    }

    public void scrollToTop() {
        mRecyclerView.scrollToPosition(0);
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public boolean isNoMore() {
        return isNoMore;
    }

    public void setNoMore(boolean noMore) {
        isNoMore = noMore;
        if (!isNoMore) { //当是false的时候改变文字和显示圆圈
            if (mProgressBar != null && mLoadTv != null) {
                mProgressBar.setVisibility(VISIBLE);
                mLoadTv.setText("正在加载中...");
            }
        }
    }

    //设置没有更多数据了
    public void setNoMore(String text) {
        setNoMore(true);
        setLoadMore(false);
        mSwipeRefreshLayout.setRefreshing(false);
        loadMoreLl.setVisibility(VISIBLE);
        mProgressBar.setVisibility(GONE);
        mLoadTv.setText(text);
    }


    public void setPullLoadMoreCompleted() {
        setLoadMore(false);
        mSwipeRefreshLayout.setRefreshing(false);
        loadMoreLl.setVisibility(View.GONE);
    }

    public interface PullLoadMoreListener {
        void onRefresh();

        void onLoadMore();
    }

    public interface DistanceListener {
        void distanceY(int offsetY);

    }

    private DistanceListener distanceListener;

    public void setDistanceListener(DistanceListener distanceListener) {
        this.distanceListener = distanceListener;
    }

    public void setOnPullLoadMoreListener(PullLoadMoreListener listener) {
        mPullLoadMoreListener = listener;
    }

    private PullLoadMoreListener mPullLoadMoreListener;
}
