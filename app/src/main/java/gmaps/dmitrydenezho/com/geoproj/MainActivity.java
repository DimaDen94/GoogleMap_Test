package gmaps.dmitrydenezho.com.geoproj;

import android.app.DialogFragment;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    static final int GALLERY_REQUEST = 1;
    ImageButton btnPhoto;
    ImageButton btnForGallery;
    ImageButton btnForLink;
    ImageButton btnMap;
    ImageButton btnView;
    DialogFragment dialog;
    LocationManager locationManager;
    public static Double latitude;
    public static Double longitude;
    static DB database;
    DateFormat dateFormat;
    Toolbar toolbar;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Загрузка из галереи
        btnForGallery = (ImageButton) findViewById(R.id.btn_gallery);
        btnForGallery.setOnClickListener(this);

        //Загрузка по ссылке
        btnForLink = (ImageButton) findViewById(R.id.btn_link);
        btnForLink.setOnClickListener(this);

        //Открыть карту
        btnMap = (ImageButton) findViewById(R.id.btn_map);
        btnMap.setOnClickListener(this);
        //Сделать фото
        btnPhoto = (ImageButton) findViewById(R.id.btn_photo);


        //
        btnView =(ImageButton) findViewById(R.id.btn_view);
        btnView.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        //Берем базу данных
        database = new DB(this);
        database.open();

        //иницыализация Toolbar
        initToolbar();

        //инициализация NavigationView
        initNavigationView();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);
    }

    LocationListener locationListener = new MyLocationListener(this, locationManager);

    public static DB getDatabase() {
        return database;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    database.addRec("" + latitude, "" + longitude, "" + dateFormat.format(new Date()), String.valueOf(selectedImage));
                }
        }
    }
    public static Double getLongitude() {
        return longitude;
    }
    public static Double getLatitude() {
        return latitude;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_photo:

                break;
            case R.id.btn_gallery:
                Intent imgPickIntent = new Intent(Intent.ACTION_PICK);
                imgPickIntent.setType("image/*");
                startActivityForResult(imgPickIntent, GALLERY_REQUEST);
                break;

            case R.id.btn_link:
                dialog = new DialogForURL();
                dialog.show(getFragmentManager(), "Dialog");
                break;

            case R.id.btn_map:
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_view:
                Intent intentView = new Intent(this, ListActivity.class);
                startActivity(intentView);
                break;
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu_main);
    }
    private void initNavigationView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.view_navigation_open,R.string.view_navigation_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawer.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.notification:

                        break;
                }
                return true;
            }
        });
    }
}