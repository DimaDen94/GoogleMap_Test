package gmaps.dmitrydenezho.com.geoproj;

import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.concurrent.TimeUnit;

import gmaps.dmitrydenezho.com.geoproj.adapters.TabPagerFragmentAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    ViewPager viewPager;
    static DB db;
    public static DB getDb() {
        return db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intTabs();
        db = new DB(this);

    }
    private void intTabs() {
        TabPagerFragmentAdapter adapter = new TabPagerFragmentAdapter(getSupportFragmentManager(),this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }




}
