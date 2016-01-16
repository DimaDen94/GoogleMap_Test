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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.app.DialogFragment;
import android.widget.TextView;

import java.util.Date;

import gmaps.dmitrydenezho.com.geoproj.Loaders.ImageLoaderGallery;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final int GALLERY_REQUEST = 1;
    Button btnForGallery;
    Button btnForURL;
    DialogFragment dialog;
    ImageView imageView;
    Context context;
    LocationManager locationManager;
    TextView tv;
    static TextView tvloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnForGallery = (Button) findViewById(R.id.btn_gallery);
        btnForGallery.setOnClickListener(this);

        btnForURL = (Button) findViewById(R.id.btn_urly);
        btnForURL.setOnClickListener(this);
        context = getApplicationContext();

        dialog = new DialogForURL();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        tvloc = (TextView) findViewById(R.id.tvLocationNet);

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


    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    public  static TextView getTvloc() {
        return tvloc;
    }

    LocationListener locationListener = new MyLocationListener(context,locationManager);



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_gallery:
                Intent imgPickerIntent = new Intent(Intent.ACTION_PICK);
                imgPickerIntent.setType("image/*");
                startActivityForResult(imgPickerIntent, GALLERY_REQUEST);
                break;
            case R.id.btn_urly:
                dialog.show(getFragmentManager(), "URL");
                break;
            case R.id.button:

                break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        imageView = (ImageView) findViewById(R.id.img);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    new ImageLoaderGallery(imageView, getContentResolver(),context)
                            .execute(String.valueOf(selectedImage));
                }
        }
    }

}
