package gmaps.dmitrydenezho.com.geoproj;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dmitry on 20.01.2016.
 */
public class PointFinder {
    ArrayList<InfoImg> array;


    public void thisDayFinder(GoogleMap map,ArrayList<InfoImg> cor){
        array = new ArrayList<InfoImg>();

        for (InfoImg iI: cor) {
            String time = iI.getData();
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy/MM/dd HH:mm:ss");
            try {
                Date past= format.parse(time);
                Date now = new Date();
                if(isSameDay(past,now)){
                    array.add(iI);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            finder(map, array);
        }





    }
    public void finder(GoogleMap map,ArrayList<InfoImg> cor) {
        double maxlat = -85;
        double maxlon = -180;
        double minlat = 85;
        double minlon = 180;
        for (InfoImg info : cor) {

            double lat = info.getLat();
            double lon = info.getLon();
            String time = info.getData();

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
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(
                new LatLngBounds(new LatLng(maxlat, minlon), new LatLng(maxlat, maxlon)),
                80);
        map.animateCamera(cameraUpdate);
    }
    private static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }
}
