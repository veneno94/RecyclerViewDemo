package recyclerview.demo.com.recylerviewdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

/**
 * ================================================
 * 作    者：Cate Emial:liuh@80pm.com
 * 版    本：1.0
 * 创建日期：2016/11/22
 * 描    述：滑动的fragment
 * 修订历史：
 * ================================================
 */
public class BasePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> list_fragment;                         //fragment列表

    public BasePagerAdapter(FragmentManager fm, List<Fragment> list_fragment) {
        super(fm);
        this.list_fragment = list_fragment;
    }




    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_fragment==null?0:list_fragment.size();
    }


    @Override
    public void finishUpdate(ViewGroup container) {
        try{
            super.finishUpdate(container);
        } catch (NullPointerException nullPointerException){
            Log.i("fdfdfdfdeeee","Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
        }
    }
}
