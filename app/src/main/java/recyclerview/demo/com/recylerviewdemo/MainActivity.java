package recyclerview.demo.com.recylerviewdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PullLoadHeadFootGridRecyclerView mFindLaunchRv;

    private View mHeaderView;

    private StaggeredGridLayoutAdapter adapter;

    private int mPage = 1;

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFindLaunchRv = (PullLoadHeadFootGridRecyclerView) findViewById(R.id.finds_launch_rv);

        mHeaderView = LayoutInflater.from(this).inflate(R.layout.fragment_rec_head, null);

        adapter = new StaggeredGridLayoutAdapter(this, mList);
        mFindLaunchRv.setStaggeredGridLayout(2);
        mFindLaunchRv.addItemDecoration(new SpaceItemDecoration(new DisplayTool(this).dip2px(12), new DisplayTool(this).dip2px(10), new DisplayTool(this).dip2px(5), 1, SpaceItemDecoration.STAGGEREDGRIDLAYOUT));
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
                            if(i%5==0){
                                data.add("敏敏我爱你 雷宝宝");
                            }else  if(i%5==1){
                                data.add("如果有来世，我一定要和你再续前缘，我要一直爱着你 护着你 想着你");
                            }else  if(i%5==2){
                                data.add("如果有来世，我一定要和你再续前缘，我要一直爱着你 抱着你 护着你 佛说：五百年的回眸，才换来今生的一次擦肩而过");
                            }else  if(i%5==3){
                                data.add("如果有来世，我一定要和你再续前缘，我要一直爱着你 抱着你 护着你 佛说：五百年的回眸，才换来今生的一次擦肩而过，我要在三生石上刻上我们的名字，来世我要找到你");
                            }else  if(i%5==4){
                                data.add("如果有来世，我一定要和你再续前缘，我要一直爱着你 抱着你 护着你 佛说：五百年的回眸，才换来今生的一次擦肩而过，我要在三生石上刻上我们的名字，来世我要找到你");
                            }else {
                                data.add("敏敏我爱你 雷宝宝");
                            }

                        }
                    }


                    runOnUiThread(new Runnable() {
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
}
