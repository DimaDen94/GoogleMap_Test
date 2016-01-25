package gmaps.dmitrydenezho.com.geoproj;

import android.database.Cursor;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import java.text.SimpleDateFormat;
/**
 * Created by Dmitry on 20.01.2016.
 */
public class PointFinder {
    double maxlat = -85;
    double maxlon = -180;
    double minlat = 85;
    double minlon = 180;
    SimpleDateFormat format = new SimpleDateFormat();

    public void thisDayFinder(GoogleMap map,DB database){
        Cursor cursor = database.getData();
        worker(cursor, map);

    }
    public void finderAllDAy(GoogleMap map, DB database) {
        map.clear();
        Cursor cursor = database.getAllData();
        worker(cursor, map);

    }

    private void worker(Cursor cursor, GoogleMap map){
        map.clear();

        format.applyPattern("yyyy/MM/dd HH:mm:ss");
        if(cursor.moveToNext()) {
            int lat = cursor.getColumnIndex(DB.COLUMN_LAT);
            int lon = cursor.getColumnIndex(DB.COLUMN_LON);
            int data = cursor.getColumnIndex(DB.COLUMN_DATA);

            do {
                String l1 = cursor.getString(lat);
                String l2 = cursor.getString(lon);
                double d1 = Double.parseDouble(l1);
                double d2 = Double.parseDouble(l2);
                String time = cursor.getString(data);

                calc(map,d1,d2,time);

            } while (cursor.moveToNext());
        }
        if (cursor.moveToFirst()) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(
                    new LatLngBounds(new LatLng(minlat, minlon), new LatLng(maxlat, maxlon)),
                    80);
            map.animateCamera(cameraUpdate);
        }
    }

    private void calc(GoogleMap map, double lat, double lon, String time){


        map.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lon))
                .title(time));
        if (maxlat < lat) {
            maxlat = lat;
        }
        if (minlat > lat) {
            minlat = lat;
        }
        if (maxlon < lon) {
            maxlon = lon;
        }
        if (minlon > lon) {
            minlon = lon;
        }

    }
}
