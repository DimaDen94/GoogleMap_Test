package gmaps.dmitrydenezho.com.geoproj.fragments;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import gmaps.dmitrydenezho.com.geoproj.DB;
import gmaps.dmitrydenezho.com.geoproj.InfoImg;
import gmaps.dmitrydenezho.com.geoproj.MainActivity;
import gmaps.dmitrydenezho.com.geoproj.R;


/**
 * Created by Dmitry on 26.12.2015.
 */
public class FragmentList extends AbstractTabFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CM_DELETE_ID = 1;
    private static final int LAYOUT = R.layout.list_fragment;
    DB database;
    SimpleCursorAdapter scAdapter;
    ListView lvData;
    public static ArrayList<InfoImg> cor;
    TextView distance;


    public static FragmentList getInstance(Context context) {
        Bundle args = new Bundle();
        FragmentList fragment = new FragmentList();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle("list");
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
        database= MainActivity.getDb();
        database.open();
        String[] from = new String[] { DB.COLUMN_IMG, DB.COLUMN_LAT, DB.COLUMN_LON, DB.COLUMN_DATA };
        int[] to = new int[] { R.id.image, R.id.tv1, R.id.tv2, R.id.tv3};
        scAdapter = new SimpleCursorAdapter(context, R.layout.item, null, from, to, 0);
        lvData = (ListView) getActivity().findViewById(R.id.image_list);
        lvData.setAdapter(scAdapter);
        registerForContextMenu(lvData);
        getActivity().getSupportLoaderManager().initLoader(0, null, this);



        cor = MainActivity.cor;
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


        distance = (TextView) getActivity().findViewById(R.id.tv_distance);
        distance.setText(""+dis);

    }

    private static double CalculationDistanceByCoord(double startPointLat,double startPointLon,double endPointLat,double endPointLon){
        float[] results = new float[1];
        Location.distanceBetween(startPointLat, startPointLon, endPointLat, endPointLon, results);
        return results[0];
    }
    public static boolean isSameDay(Date date1, Date date2) {
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


    @Override
    public void onResume() {
        super.onResume();

    }

    public void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        database.close();
    }

    @Override
    public void onPause() {
        super.onPause();

    }
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "удалить запись");
    }


    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            database.delRec(acmi.id);
            // получаем новый курсор с данными
            getActivity().getSupportLoaderManager().getLoader(0).forceLoad();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MainActivity.MyCursorLoader(context, database);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }



}
