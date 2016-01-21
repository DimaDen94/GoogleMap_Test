package gmaps.dmitrydenezho.com.geoproj;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class AllPhotoActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor> {

    DB database;
    public static SimpleCursorAdapter scAdapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_photo);

        database= new DB(this);
        database.open();

        String[] from = new String[] { DB.COLUMN_IMG, DB.COLUMN_LAT, DB.COLUMN_LON, DB.COLUMN_DATA };
        int[] to = new int[] { R.id.image, R.id.tv1, R.id.tv2, R.id.tv3};
        scAdapter = new SimpleCursorAdapter(this, R.layout.itemgrid, null, from, to, 0);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(scAdapter);
        registerForContextMenu(gridView);
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setColumnWidth(200);
        gridView.setHorizontalSpacing(5);
        gridView.setVerticalSpacing(5);
        getSupportLoaderManager().initLoader(2, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, database);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    public static class MyCursorLoader extends CursorLoader {

        DB db;

        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            return cursor;
        }

    }
}
