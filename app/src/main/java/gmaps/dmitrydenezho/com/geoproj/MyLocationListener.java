package gmaps.dmitrydenezho.com.geoproj;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.Date;

import gmaps.dmitrydenezho.com.geoproj.fragments.FragmentMark;

/**
 * Created by Dmitry on 16.01.2016.
 */
public class MyLocationListener implements LocationListener {
    Context context;
    LocationManager locationManager;


    public MyLocationListener(Context context, LocationManager locationManager) {
        this.context = context;
        this.locationManager = locationManager;

    }

    @Override
    public void onLocationChanged(Location location) {
        showLocation(location);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        showLocation(locationManager.getLastKnownLocation(provider));
    }
    @Override
    public void onProviderDisabled(String provider) {

    }
    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            FragmentMark.longitude = location.getLongitude();
            FragmentMark.latitude = location.getLatitude();
        } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
            FragmentMark.longitude = location.getLongitude();
            FragmentMark.latitude = location.getLatitude();

        }
    }



}
