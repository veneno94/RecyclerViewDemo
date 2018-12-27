package recyclerview.demo.com.recylerviewdemo.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import recyclerview.demo.com.recylerviewdemo.R;
import recyclerview.demo.com.recylerviewdemo.adapter.BasePagerAdapter;
import recyclerview.demo.com.recylerviewdemo.event.DistanceEvent;
import recyclerview.demo.com.recylerviewdemo.event.SelectTypeEvent;
import recyclerview.demo.com.recylerviewdemo.fragment.WeiboFragment;
import recyclerview.demo.com.recylerviewdemo.view.UserTitle;

public class WeiboActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Fragment> list;
    private UserTitle userTitle;
    private UserTitle userTitle2;
    private LinearLayout headLl;
    private TextView headTv;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo);
        EventBus.getDefault().register(this);

        viewPager = findViewById(R.id.view_pager);
        userTitle = findViewById(R.id.user_title);
        userTitle2 = findViewById(R.id.user_title2);
        headTv = findViewById(R.id.head_tv);
        headLl = findViewById(R.id.head_ll);

        headTv.post(new Runnable() {
            @Override
            public void run() {
                height = headTv.getHeight();
            }
        });


        list = new ArrayList<>();


        list.add(WeiboFragment.getFragment(1));
        list.add(WeiboFragment.getFragment(2));
        list.add(WeiboFragment.getFragment(3));


        viewPager.setOffscreenPageLimit(3);

        BasePagerAdapter mAdapter = new BasePagerAdapter(getSupportFragmentManager(), list);

        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    userTitle.setTabActivity();
                    userTitle2.setTabActivity();
                } else if (i == 1) {
                    userTitle.setTabSalt();
                    userTitle2.setTabSalt();
                } else {
                    userTitle.setTabWeibo();
                    userTitle2.setTabWeibo();
                }

                EventBus.getDefault().post(new SelectTypeEvent(i));

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        userTitle.setOnTitleClickListener(new UserTitle.OnTitleClickListener() {
            @Override
            public void onClickActivies(View view) {
                viewPager.setCurrentItem(0);
            }

            @Override
            public void onClickSalt(View view) {
                viewPager.setCurrentItem(1);
            }

            @Override
            public void onClickWeibo(View view) {
                viewPager.setCurrentItem(2);
            }
        });
        userTitle2.setOnTitleClickListener(new UserTitle.OnTitleClickListener() {
            @Override
            public void onClickActivies(View view) {
                viewPager.setCurrentItem(0);
            }

            @Override
            public void onClickSalt(View view) {
                viewPager.setCurrentItem(1);
            }

            @Override
            public void onClickWeibo(View view) {
                viewPager.setCurrentItem(2);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectedChange(DistanceEvent event) {
        int distanceY = event.getDistanceY();
        headLl.layout(0, -distanceY, 0, 0);
        if (distanceY >= height) {
            userTitle2.setVisibility(View.VISIBLE);
        } else {
            userTitle2.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
