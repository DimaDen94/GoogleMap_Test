package gmaps.dmitrydenezho.com.geoproj;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import gmaps.dmitrydenezho.com.geoproj.adapters.TabPagerFragmentAdapter;

public class MainActivity extends FragmentActivity{

    private TabLayout tabLayout;
    ViewPager viewPager;
    static DB db;



    public static ArrayList<InfoImg> cor;
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
        db.open();
        cor = new ArrayList<InfoImg>();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private void intTabs() {
        TabPagerFragmentAdapter adapter = new TabPagerFragmentAdapter(getSupportFragmentManager(),this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }




    public static class MyCursorLoader extends CursorLoader {

        DB db;

        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            cor.clear();
            if(cursor.moveToNext()){
                int lat = cursor.getColumnIndex(DB.COLUMN_LAT);
                int lon = cursor.getColumnIndex(DB.COLUMN_LON);
                int data = cursor.getColumnIndex(DB.COLUMN_DATA);
                int path = cursor.getColumnIndex(DB.COLUMN_IMG);


                do {
                    String l1 = cursor.getString(lat);
                    String l2 = cursor.getString(lon);
                    double d1 = Double.parseDouble(l1);
                    double d2 = Double.parseDouble(l2);


                    InfoImg infoImg = new InfoImg();
                    infoImg.setLat(d1);
                    infoImg.setLon(d2);

                    String p = cursor.getString(path);
                    String t = cursor.getString(data);
                    infoImg.setPath(p);
                    infoImg.setData(t);

                    cor.add(infoImg);


                } while (cursor.moveToNext());
            }
            return cursor;
        }

    }
}