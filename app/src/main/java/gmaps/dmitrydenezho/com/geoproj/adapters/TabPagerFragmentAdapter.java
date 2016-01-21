package gmaps.dmitrydenezho.com.geoproj.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;
import gmaps.dmitrydenezho.com.geoproj.fragments.AbstractTabFragment;
import gmaps.dmitrydenezho.com.geoproj.fragments.FragmentList;
import gmaps.dmitrydenezho.com.geoproj.fragments.FragmentMark;
import gmaps.dmitrydenezho.com.geoproj.fragments.FragmentMap;


public class TabPagerFragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    public TabPagerFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabs  = new HashMap<>();
        tabs.put(0, FragmentMark.getInstance(context));
        tabs.put(1, FragmentMap.getInstance(context));
        tabs.put(2, FragmentList.getInstance(context));

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {

        return tabs.get(position);

    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
