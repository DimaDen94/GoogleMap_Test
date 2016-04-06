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
import android.widget.ListView;
import android.widget.TextView;

import gmaps.dmitrydenezho.com.geoproj.Counter;
import gmaps.dmitrydenezho.com.geoproj.DB;
import gmaps.dmitrydenezho.com.geoproj.Loaders.MyCursorLoaderDay;
import gmaps.dmitrydenezho.com.geoproj.MainActivity;
import gmaps.dmitrydenezho.com.geoproj.R;
import gmaps.dmitrydenezho.com.geoproj.GalleryActivity;
import gmaps.dmitrydenezho.com.geoproj.adapters.CustomCursorAdapter;

/**
 * Created by Dmitry on 26.12.2015.
 */public class FragmentList extends AbstractTabFragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int CM_DELETE_ID = 2;
    private static final int CM_OPEN_ID = 1;
    private static final int LAYOUT = R.layout.list_fragment;
    DB database;
    ListView lvData;
    TextView distance;
    float dis;
    int intDis;
    Button calcDistance;
    Button openGallery;
    CustomCursorAdapter myAdapter;

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

        //берем базу данных
        database = DB.getInstance(context);
        database.open();

        //создание списка
        myAdapter = new CustomCursorAdapter(context,database.getData(),1,R.layout.itemlist);
        lvData = (ListView) getActivity().findViewById(R.id.image_list);
        lvData.setAdapter(myAdapter);
        registerForContextMenu(lvData);




        calcDistance = (Button) getActivity().findViewById(R.id.calculate_path);
        calcDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Counter counter = new Counter();
                dis = counter.doIt(database.getData());
                intDis = (int) dis;
                initDistance();
            }
        });

        openGallery = (Button) getActivity().findViewById(R.id.btn_open_gallery);
        openGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GalleryActivity.class);
                startActivity(intent);
            }
        });

        getActivity().getSupportLoaderManager().initLoader(1, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        database.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        database.close();
    }

    public void initDistance(){
    distance = (TextView) getActivity().findViewById(R.id.tv_distance);
    distance.setText("Today, you have passed "+intDis+" m");
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
            getActivity().getSupportLoaderManager().getLoader(1).forceLoad();
            getActivity().getSupportLoaderManager().getLoader(2).forceLoad();

            return true;
        }else if (item.getItemId() == CM_OPEN_ID){
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();

            Intent intent = new Intent(context, GalleryActivity.class);

            startActivity(intent);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoaderDay(context, database);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        myAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}