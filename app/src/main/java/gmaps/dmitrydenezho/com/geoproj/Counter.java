package gmaps.dmitrydenezho.com.geoproj;

import android.database.Cursor;
import android.location.Location;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dmitry on 19.01.2016.
 */
public class Counter {

    public Float doIt(Cursor cursor) {

        float dis =0;

        if(cursor.moveToNext()) {
            int lat = cursor.getColumnIndex(DBHelper.COLUMN_LAT);
            int lon = cursor.getColumnIndex(DBHelper.COLUMN_LON);
            do {

                double startPointLat = Double.parseDouble(cursor.getString(lat));
                double startPointLon = Double.parseDouble(cursor.getString(lon));

                if (cursor.moveToNext()) {
                    double endPointLat = Double.parseDouble(cursor.getString(lat));
                    double endPointLon = Double.parseDouble(cursor.getString(lon));
                    dis = (float) (dis + CalculationDistanceByCoord(startPointLat, startPointLon, endPointLat, endPointLon));
                    Log.e("my", dis+"");
                }

            } while (cursor.isAfterLast()==false);
        }

        return dis;
    }
    private static double CalculationDistanceByCoord(double startPointLat,double startPointLon,double endPointLat,double endPointLon){
        float[] results = new float[1];
        Location.distanceBetween(startPointLat, startPointLon, endPointLat, endPointLon, results);
        return results[0];
    }
}
