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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import gmaps.dmitrydenezho.com.geoproj.adapters.TabPagerFragmentAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

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
        cor = new ArrayList<InfoImg>();
    }
    private void intTabs() {
        TabPagerFragmentAdapter adapter = new TabPagerFragmentAdapter(getSupportFragmentManager(),this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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

                    boolean is = true;
                    for (int i =0; i< cor.size(); i++){
                        if(cor.get(i).equals(infoImg)){
                            is = false;
                        }

                    }
                    if(is){
                        cor.add(infoImg);
                    }

                } while (cursor.moveToNext());
            }
            return cursor;
        }

    }
}
