package gmaps.dmitrydenezho.com.geoproj.Loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import gmaps.dmitrydenezho.com.geoproj.DB;

/**
 * Created by Dmitry on 21.01.2016.
 */
public class MyCursorLoaderDay extends CursorLoader {

    DB db;

    public MyCursorLoaderDay(Context context, DB db) {
        super(context);
        this.db = db;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = db.getData();
        return cursor;
    }

}
