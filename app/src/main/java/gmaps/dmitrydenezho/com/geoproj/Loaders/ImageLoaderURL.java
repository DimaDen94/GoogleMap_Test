package gmaps.dmitrydenezho.com.geoproj.Loaders;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;



/**
 * Created by Dmitry on 15.01.2016.
 */
public class ImageLoaderURL extends AdapterLoader {
    public ImageLoaderURL(ImageView bmImage, Context context) {
        this.bmImage = bmImage;
        this.context = context;
    }

    protected byte[] doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bitmap = null;
        byte[] arr = new byte[0];
        try {
            InputStream in = new URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);
            Log.d("mylog", "Получен bitmap");
            arr = getBytes(bitmap);
            Log.d("mylog", "Преобразовано в байт код");

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return arr;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}