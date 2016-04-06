package gmaps.dmitrydenezho.com.geoproj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dmitry on 06.04.2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instance = null;


    public  static final String DB_NAME = "imgDB";
    public  static final int DB_VERSION = 1;
    public  static final String DB_TABLE = "img";

    public  static final String COLUMN_ID = "_id";
    public  static final String COLUMN_LAT = "lat";
    public  static final String COLUMN_LON = "lon";
    public  static final String COLUMN_DATA = "data";
    public  static final String COLUMN_IMG = "img";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_LAT + " text, " +COLUMN_LON + " text, " +COLUMN_DATA + " text, " +
                    COLUMN_IMG + " text" +
                    ");";

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public static DBHelper getInstance(Context context){
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    // создаем и заполняем БД
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
