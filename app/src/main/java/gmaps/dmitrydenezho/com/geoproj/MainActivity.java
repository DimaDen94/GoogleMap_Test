package gmaps.dmitrydenezho.com.geoproj;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.app.DialogFragment;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import gmaps.dmitrydenezho.com.geoproj.Loaders.ImageLoaderGallery;
import gmaps.dmitrydenezho.com.geoproj.adapter.TabPagerFragmentAdapter;

public class MainActivity extends AppCompatActivity {
    static final int GALLERY_REQUEST = 1;

    private TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intTabs();

    }
    private void intTabs() {
        TabPagerFragmentAdapter adapter = new TabPagerFragmentAdapter(getSupportFragmentManager(),this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
