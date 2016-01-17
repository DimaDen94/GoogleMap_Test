package gmaps.dmitrydenezho.com.geoproj.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import gmaps.dmitrydenezho.com.geoproj.fragments.AbstractTabFragment;
import gmaps.dmitrydenezho.com.geoproj.fragments.One;
import gmaps.dmitrydenezho.com.geoproj.fragments.Two;


public class TabPagerFragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    public TabPagerFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabs  = new HashMap<>();
        tabs.put(0, One.getInstance(context));
        tabs.put(1, Two.getInstance(context));

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
