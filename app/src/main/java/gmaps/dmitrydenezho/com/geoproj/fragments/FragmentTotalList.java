package gmaps.dmitrydenezho.com.geoproj.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import gmaps.dmitrydenezho.com.geoproj.AllPhotoActivity;
import gmaps.dmitrydenezho.com.geoproj.Counter;
import gmaps.dmitrydenezho.com.geoproj.DB;
import gmaps.dmitrydenezho.com.geoproj.Loaders.MyCursorLoaderAll;
import gmaps.dmitrydenezho.com.geoproj.MainActivity;
import gmaps.dmitrydenezho.com.geoproj.R;
import gmaps.dmitrydenezho.com.geoproj.ShowActivity;
import gmaps.dmitrydenezho.com.geoproj.adapters.CustomCursorAdapter;

/**
 * Created by Dmitry on 26.12.2015.
 */public class FragmentTotalList extends AbstractTabFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CM_DELETE_ID = 2;
    private static final int CM_OPEN_ID = 1;
    DB database;

    CustomCursorAdapter myAdapter;
    GridView gridView;

    public static FragmentTotalList getInstance(Context context) {
        Bundle args = new Bundle();
        FragmentTotalList fragment = new FragmentTotalList();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle("total");
        return fragment;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_total_fragment,container,false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //бурем бвзу данных
        database= MainActivity.getDatabase();

        gridView = (GridView) getActivity().findViewById(R.id.gridView);

        myAdapter = new CustomCursorAdapter(context,database.getAllData(),2,R.layout.itemgrid);

        //настройка грид
        gridView.setAdapter(myAdapter);
        registerForContextMenu(gridView);
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setColumnWidth(200);
        gridView.setHorizontalSpacing(5);
        gridView.setVerticalSpacing(5);


        getActivity().getSupportLoaderManager().initLoader(2, null, this);

    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().getLoader(2).forceLoad();
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_OPEN_ID, 0, "Open");
        menu.add(1, CM_DELETE_ID, 0, "Remove item");
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            database.delRec(acmi.id);
            Log.e("mylog", acmi.id + "");
            // получаем новый курсор с данными
            getActivity().getSupportLoaderManager().getLoader(2).forceLoad();
            getActivity().getSupportLoaderManager().getLoader(1).forceLoad();

            return true;
        }else if (item.getItemId() == CM_OPEN_ID){
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();




            Intent intent = new Intent(context, ShowActivity.class);

            startActivity(intent);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoaderAll(context, database);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        myAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}