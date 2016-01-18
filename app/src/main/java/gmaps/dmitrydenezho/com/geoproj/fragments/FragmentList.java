package gmaps.dmitrydenezho.com.geoproj.fragments;

import android.content.Context;
import android.database.Cursor;
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


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import gmaps.dmitrydenezho.com.geoproj.DB;
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(context, database);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    static class MyCursorLoader extends CursorLoader {

        DB db;

        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            if(cursor.moveToNext()){
                int lat = cursor.getColumnIndex(DB.COLUMN_LAT);
                int lon = cursor.getColumnIndex(DB.COLUMN_LON);

                //int lon = Double.parseDouble(String.valueOf(cursor.getColumnIndex(DB.COLUMN_LON)));
                do {
                    String l1 = cursor.getString(lat);
                    double d1 = Double.parseDouble(l1);
                    int i1 = (int) d1;
                    String l2 = cursor.getString(lon);
                    double d2 = Double.parseDouble(l2);
                    int i2 = (int) d2;

                    MainActivity.cor.put(d1,d2);
                } while (cursor.moveToNext());
            }else {

            }

            return cursor;
        }

    }

}
