package recyclerview.demo.com.recylerviewdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import recyclerview.demo.com.recylerviewdemo.R;
import recyclerview.demo.com.recylerviewdemo.adapter.StaggeredGridLayoutAdapter;
import recyclerview.demo.com.recylerviewdemo.event.DistanceEvent;
import recyclerview.demo.com.recylerviewdemo.event.SelectTypeEvent;
import recyclerview.demo.com.recylerviewdemo.recyclerview.PullLoadHeadFootGridRecyclerView;
import recyclerview.demo.com.recylerviewdemo.recyclerview.SpaceItemDecoration;
import recyclerview.demo.com.recylerviewdemo.touch.ItemTouchCallback;
import recyclerview.demo.com.recylerviewdemo.utils.DisplayTool;
import recyclerview.demo.com.recylerviewdemo.view.UserTitle;

public class WeiboFragment extends Fragment {

    private PullLoadHeadFootGridRecyclerView mFindLaunchRv;

    private View mHeaderView;

    private StaggeredGridLayoutAdapter adapter;

    private int mPage = 1;

    private List<String> mList = new ArrayList<>();
    private View view;
    private int type;
    private UserTitle userTitle;

    public static WeiboFragment getFragment(int type) {
        WeiboFragment weiboFragment = new WeiboFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        weiboFragment.setArguments(bundle);
        return weiboFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        type = getArguments().getInt("type");
        view = inflater.inflate(R.layout.fragment_weibo, container, false);
        initView();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectedChange(SelectTypeEvent event) {
        if (userTitle != null) {
            int type = event.getType();
            if(type==0){
                userTitle.setTabActivity();
            }else if (type==1){
                userTitle.setTabSalt();
            }else {
                userTitle.setTabWeibo();
            }
        }

    }

    private void initView() {

        mFindLaunchRv = view.findViewById(R.id.finds_launch_rv);

        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_rec_head, null);
        userTitle = mHeaderView.findViewById(R.id.user_title);

        adapter = new StaggeredGridLayoutAdapter(getActivity(), mList);
        mFindLaunchRv.setStaggeredGridLayout(2);
        mFindLaunchRv.addItemDecoration(new SpaceItemDecoration(new DisplayTool(getActivity()).dip2px(12), new DisplayTool(getActivity()).dip2px(10), new DisplayTool(getActivity()).dip2px(5), 1, SpaceItemDecoration.STAGGEREDGRIDLAYOUT));
        mFindLaunchRv.setAdapter(adapter);

        mFindLaunchRv.addHeadView(mHeaderView);


        mFindLaunchRv.setOnPullLoadMoreListener(new PullLoadHeadFootGridRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                postFind();

            }

            @Override
            public void onLoadMore() {
                postFind();
            }
        });

        mFindLaunchRv.setRefreshing(true);

        mFindLaunchRv.setDistanceListener(new PullLoadHeadFootGridRecyclerView.DistanceListener() {
            @Override
            public void distanceY(int offsetY) {
                EventBus.getDefault().post(new DistanceEvent(offsetY));


            }
        });


    }

    private Handler handler = new Handler();


    private void postFind() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //模拟网络请求 加载数据
                    Thread.sleep(500);
                    final List<String> data = new ArrayList<>();

                    if (mPage <= 5) { //5页后没有数据

                        for (int i = 0; i < 20; i++) {
                            if (type == 1) {
                                data.add("敏雷 " + i);
                            } else if (type == 2) {
                                data.add("敏 " + i);
                            } else {
                                data.add("雷 " + i);
                            }
                        }
                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            if (mPage == 1) {

                                mList.clear();

                                mFindLaunchRv.setNoMore(false);


                                if (data == null || data.size() == 0) {
                                    mFindLaunchRv.setNoMore("还没有数据");
                                } else {

                                    mList.addAll(data);
                                    mPage++;
                                    mFindLaunchRv.setPullLoadMoreCompleted();
                                }
                            } else {

                                if (data == null || data.size() == 0) {
                                    mFindLaunchRv.setNoMore("没有更多数据");
                                } else {
                                    mList.addAll(data);
                                    mPage++;
                                    mFindLaunchRv.setPullLoadMoreCompleted();
                                }
                            }

                            adapter.notifyDataSetChanged();

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
