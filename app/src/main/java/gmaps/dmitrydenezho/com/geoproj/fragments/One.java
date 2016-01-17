package gmaps.dmitrydenezho.com.geoproj.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import gmaps.dmitrydenezho.com.geoproj.DialogForURL;
import gmaps.dmitrydenezho.com.geoproj.Loaders.ImageLoaderGallery;
import gmaps.dmitrydenezho.com.geoproj.MyLocationListener;
import gmaps.dmitrydenezho.com.geoproj.R;


/**
 * Created by Dmitry on 26.12.2015.
 */
public class One extends AbstractTabFragment {
    static final int GALLERY_REQUEST = 1;
    Button btnForGallery;
    Button btnForURL;
    DialogFragment dialog;
    ImageView imageView;
    Context context;
    LocationManager locationManager;


    public static Double latitude;
    public static Double longitude;

    TextView tvLat;

    TextView tvLon;
    TextView tvDate;
    DateFormat dateFormat;
    static Date date;

    private static final int LAYOUT = R.layout.first_fragment;

    public static One getInstance(Context context) {
        Bundle args = new Bundle();
        One fragment = new One();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle("one");
        return fragment;
    }
        @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT,container,false);




        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        btnForGallery = (Button) getActivity().findViewById(R.id.btn_gallery);
        btnForGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgPickerIntent = new Intent(Intent.ACTION_PICK);
                imgPickerIntent.setType("image/*");
                startActivityForResult(imgPickerIntent, GALLERY_REQUEST);
            }
        });

        btnForURL = (Button) getActivity().findViewById(R.id.btn_url);
        btnForURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(getActivity().getFragmentManager(),"Dialog");
            }
        });
        context = getActivity().getApplicationContext();

        dialog = new DialogForURL();

        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

        tvLat = (TextView) getActivity().findViewById(R.id.tvLocationLatitude);
        tvLon = (TextView) getActivity().findViewById(R.id.tvLocationLongitude);
        tvDate= (TextView) getActivity().findViewById(R.id.tvLocationData);

        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        date = new Date();
    }
    @Override
    public void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);
        init();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    LocationListener locationListener = new MyLocationListener(context,locationManager);


    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        imageView = (ImageView) getActivity().findViewById(R.id.img);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == getActivity().RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    new ImageLoaderGallery(imageView, getActivity().getContentResolver(),context)
                            .execute(String.valueOf(selectedImage));
                }
        }
    }

    private void init(){
        tvLat.setText("Широта = " + latitude);
        tvLon.setText("Долгота =" + longitude);
        tvDate.setText("Время :" + dateFormat.format(new Date()));
    }
    public static Date getDate() {
        return date;
    }

    public static Double getLongitude() {
        return longitude;
    }

    public static Double getLatitude() {
        return latitude;
    }

}
