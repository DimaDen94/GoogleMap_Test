package gmaps.dmitrydenezho.com.geoproj;

import android.location.Location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dmitry on 19.01.2016.
 */
public class Counter {



    public Float doIt(ArrayList<InfoImg> cor) {

        float dis =0;

        for (int i = 0; i < cor.size()-1; i++) {

            double startPointLat = cor.get(i).getLat();
            double startPointLon =cor.get(i).getLon();
            double endPointLat = cor.get(i+1).getLat();
            double endPointLon =cor.get(i+1).getLon();

            String time = cor.get(i).getData();

            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy/MM/dd HH:mm:ss");
            try {
                Date past= format.parse(time);
                Date now = new Date();
                if(isSameDay(past,now)){
                    dis = (float) (dis +CalculationDistanceByCoord(startPointLat, startPointLon, endPointLat, endPointLon));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return dis;
    }
    private static double CalculationDistanceByCoord(double startPointLat,double startPointLon,double endPointLat,double endPointLon){
        float[] results = new float[1];
        Location.distanceBetween(startPointLat, startPointLon, endPointLat, endPointLon, results);
        return results[0];
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
