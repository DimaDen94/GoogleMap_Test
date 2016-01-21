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



    public static ArrayList<InfoImg> imgArrayList;
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
        imgArrayList = new ArrayList<InfoImg>();

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
            Cursor cursorAll = db.getAllData();
            Cursor cursor = db.getData();
            imgArrayList.clear();
            if(cursorAll.moveToNext()){
                int lat = cursorAll.getColumnIndex(DB.COLUMN_LAT);
                int lon = cursorAll.getColumnIndex(DB.COLUMN_LON);
                int data = cursorAll.getColumnIndex(DB.COLUMN_DATA);
                int path = cursorAll.getColumnIndex(DB.COLUMN_IMG);


                do {
                    String l1 = cursorAll.getString(lat);
                    String l2 = cursorAll.getString(lon);
                    double d1 = Double.parseDouble(l1);
                    double d2 = Double.parseDouble(l2);


                    InfoImg infoImg = new InfoImg();
                    infoImg.setLat(d1);
                    infoImg.setLon(d2);

                    String p = cursorAll.getString(path);
                    String t = cursorAll.getString(data);
                    infoImg.setPath(p);
                    infoImg.setData(t);

                    imgArrayList.add(infoImg);


                } while (cursorAll.moveToNext());
            }
            return cursor;
        }

    }
}