package gmaps.dmitrydenezho.com.geoproj;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import gmaps.dmitrydenezho.com.geoproj.Loaders.MyCursorLoaderAll;

public class AllPhotoActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CM_DELETE_ID = 2;
    private static final int CM_OPEN_ID = 1;
    DB database;
    public static SimpleCursorAdapter scAdapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_photo);

        database= new DB(this);
        database.open();

        gridView = (GridView) findViewById(R.id.gridView);
        String[] from = new String[] { DB.COLUMN_IMG, DB.COLUMN_LAT, DB.COLUMN_LON, DB.COLUMN_DATA };
        int[] to = new int[] { R.id.image, R.id.tv1, R.id.tv2, R.id.tv3};
        scAdapter = new SimpleCursorAdapter(this, R.layout.itemgrid, null, from, to, 0);

        //настройка грид
        gridView.setAdapter(scAdapter);
        registerForContextMenu(gridView);
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setColumnWidth(200);
        gridView.setHorizontalSpacing(5);
        gridView.setVerticalSpacing(5);



        getSupportLoaderManager().initLoader(2, null, this);

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
            getSupportLoaderManager().getLoader(2).forceLoad();

            return true;
        }else if (item.getItemId() == CM_OPEN_ID){
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            ArrayList<InfoImg> infoImgs = MainActivity.imgArrayList;

            InfoImg img = infoImgs.get(acmi.position);

            Intent intent = new Intent(this, ShowActivity.class);
            intent.putExtra("img" ,img.getPath());
            startActivity(intent);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoaderAll(this, database);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
