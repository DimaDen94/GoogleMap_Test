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
import java.util.ArrayList;
import gmaps.dmitrydenezho.com.geoproj.AllPhotoActivity;
import gmaps.dmitrydenezho.com.geoproj.Counter;
import gmaps.dmitrydenezho.com.geoproj.DB;
import gmaps.dmitrydenezho.com.geoproj.InfoImg;
import gmaps.dmitrydenezho.com.geoproj.MainActivity;
import gmaps.dmitrydenezho.com.geoproj.R;
import gmaps.dmitrydenezho.com.geoproj.ShowActivity;
import gmaps.dmitrydenezho.com.geoproj.adapters.CustomCursorAdapter;

/**
 * Created by Dmitry on 26.12.2015.
 */public class FragmentList extends AbstractTabFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CM_DELETE_ID = 2;
    private static final int CM_OPEN_ID = 1;
    private static final int LAYOUT = R.layout.list_fragment;
    DB database;
    ListView lvData;
    public static ArrayList<InfoImg> cor;
    TextView distance;
    float dis;
    int intDis;
    Button calcDistance;
    Button open;
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
        database= MainActivity.getDb();



        myAdapter = new CustomCursorAdapter(context,database.getAllData(),1,R.layout.itemlist);


        lvData = (ListView) getActivity().findViewById(R.id.image_list);

        lvData.setAdapter(myAdapter);
        registerForContextMenu(lvData);
        getActivity().getSupportLoaderManager().initLoader(1, null, this);



        calcDistance = (Button) getActivity().findViewById(R.id.calculate_path);
        calcDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cor = MainActivity.imgArrayList;
                Counter counter = new Counter();
                dis = counter.doIt(cor);
                intDis = (int) dis;
                initDistance();
                getActivity().getSupportLoaderManager().getLoader(1).forceLoad();
            }
        });
        open =(Button) getActivity().findViewById(R.id.open);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AllPhotoActivity.class);
                startActivity(intent);

            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().getLoader(1).forceLoad();
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
            // получаем новый курсор с данными
            Log.e("mylog", acmi.id + "");
            getActivity().getSupportLoaderManager().getLoader(1).forceLoad();

            return true;
        }else if (item.getItemId() == CM_OPEN_ID){
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            ArrayList<InfoImg> infoImgs = MainActivity.imgArrayList;

            InfoImg img = infoImgs.get(acmi.position);

            Intent intent = new Intent(context, ShowActivity.class);
            intent.putExtra("img" ,img.getPath());
            startActivity(intent);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        DB thisDayDataBase = new DB(context);
        thisDayDataBase.open();




        return new MainActivity.MyCursorLoader(context, database);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        myAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

public void initDistance(){
    distance = (TextView) getActivity().findViewById(R.id.tv_distance);
    distance.setText("Today, you have passed "+intDis+" m");
}

}