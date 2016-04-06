package gmaps.dmitrydenezho.com.geoproj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Gallery;

import gmaps.dmitrydenezho.com.geoproj.adapters.CustomCursorAdapterForBigPictures;

public class GalleryActivity extends AppCompatActivity {

    Gallery gallery;
    DB database;
    CustomCursorAdapterForBigPictures adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        database = DB.getInstance(this);
        database.open();

        gallery = (Gallery)findViewById(R.id.gallery);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        int w = metricsB.widthPixels;
        int h = metricsB.heightPixels;


        adapter = new CustomCursorAdapterForBigPictures(this,database.getAllData(),3,R.layout.itemgallery,w,h);

        gallery.setAdapter(adapter);



    }

}
