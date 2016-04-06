package gmaps.dmitrydenezho.com.geoproj;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Dmitry on 17.01.2016.
 */
public class DB {


    private static DB instance = null;
    private static Context context;


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    private DB(Context context) {
        DB.context = context;
    }

    public static DB getInstance(Context context) {
        if (instance == null) {
            instance = new DB(context);
        }
        return instance;

    }

    // открыть подключение
    public void open() {
        mDBHelper = DBHelper.getInstance(context);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DBHelper.DB_TABLE, null, null, null, null, null, null);
    }

    public Cursor getData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String data = dateFormat.format(new Date());

        String selection = DBHelper.COLUMN_DATA + " LIKE ?";
        String[] selectionArgs = new String[]{data.substring(0, 10) + "%"};


        return mDB.query(DBHelper.DB_TABLE, null, selection, selectionArgs, null, null, null);

    }

    public Cursor getPath(long id) {
        String selection = DBHelper.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = new String[]{id + ""};


        return mDB.query(DBHelper.DB_TABLE, null, selection, selectionArgs, null, null, null);

    }


    // добавить запись в DB_TABLE
    public void addRec(String latitude, String longitude, String data, String img) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_LAT, latitude);
        cv.put(DBHelper.COLUMN_LON, longitude);
        cv.put(DBHelper.COLUMN_DATA, data);
        cv.put(DBHelper.COLUMN_IMG, img);
        mDB.insert(DBHelper.DB_TABLE, null, cv);
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DBHelper.DB_TABLE, DBHelper.COLUMN_ID + " = " + id, null);
    }

}
