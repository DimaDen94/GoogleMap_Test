package gmaps.dmitrydenezho.com.geoproj.Loaders;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import gmaps.dmitrydenezho.com.geoproj.DBHelper;

/**
 * Created by Dmitry on 16.01.2016.
 */
public class AdapterLoader extends AsyncTask<String, String, Bitmap> {
    ImageView bmImage;
    DBHelper dbHelper;
    Context context;

    @Override
    protected Bitmap doInBackground(String... adress) {
        return null;
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
