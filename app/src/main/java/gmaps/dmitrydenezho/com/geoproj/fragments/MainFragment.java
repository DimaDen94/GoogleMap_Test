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
import android.widget.ImageButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import gmaps.dmitrydenezho.com.geoproj.DB;
import gmaps.dmitrydenezho.com.geoproj.DialogForURL;
import gmaps.dmitrydenezho.com.geoproj.MainActivity;
import gmaps.dmitrydenezho.com.geoproj.MapActivity;
import gmaps.dmitrydenezho.com.geoproj.MyLocationListener;
import gmaps.dmitrydenezho.com.geoproj.R;


/**
 * Created by Dmitry on 26.12.2015.
 */
public class MainFragment extends AbstractTabFragment implements View.OnClickListener {
    static final int GALLERY_REQUEST = 1;
    ImageButton btnPhoto;
    ImageButton btnForGallery;
    ImageButton btnForLink;
    ImageButton btnMap;
    DialogFragment dialog;
    LocationManager locationManager;
    public static Double latitude;
    public static Double longitude;
    DB database;
    DateFormat dateFormat;


    private static final int LAYOUT = R.layout.main_fragment;

    public static MainFragment getInstance(Context context) {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle("mark");
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

    @Override
    public void onStart() {

        super.onStart();

        //Загрузка из галереи
        btnForGallery = (ImageButton) getActivity().findViewById(R.id.btn_gallery);
        btnForGallery.setOnClickListener(this);

        //Загрузка по ссылке
        btnForLink = (ImageButton) getActivity().findViewById(R.id.btn_link);
        btnForLink.setOnClickListener(this);

        //Открыть карту
        btnMap = (ImageButton) getActivity().findViewById(R.id.btn_map);
        btnMap.setOnClickListener(this);
        //Сделать фото
        btnPhoto = (ImageButton) getActivity().findViewById(R.id.btn_photo);


        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);


        //Берем базу данных
        database = MainActivity.getDb();

    }

    @Override
    public void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);
    }

    LocationListener locationListener = new MyLocationListener(context, locationManager);


    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    MainActivity.getDb().addRec("" + latitude, "" + longitude, "" + dateFormat.format(new Date()), String.valueOf(selectedImage));
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
                dialog.show(getActivity().getFragmentManager(), "Dialog");
                break;

            case R.id.btn_map:
                Intent intent = new Intent(context, MapActivity.class);
                startActivity(intent);
                break;
        }
    }
}
