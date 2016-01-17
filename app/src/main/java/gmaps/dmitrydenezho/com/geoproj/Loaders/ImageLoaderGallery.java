package gmaps.dmitrydenezho.com.geoproj.Loaders;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import gmaps.dmitrydenezho.com.geoproj.DBHelper;
import gmaps.dmitrydenezho.com.geoproj.fragments.One;

/**
 * Created by Dmitry on 15.01.2016.
 */
public class ImageLoaderGallery extends AdapterLoader {
    ContentResolver resolver;



    public ImageLoaderGallery(ImageView bmImage, ContentResolver resolver, Context context) {
        this.bmImage = bmImage;
        this.resolver = resolver;
        this.context = context;


    }

    protected Bitmap doInBackground(String... adress) {
        String ur = adress[0];

        Bitmap bitmap = null;
        try {

            bitmap = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(ur));
            Log.d("mylog", "Получен bitmap");

            byte[] arr = getBytes(bitmap);
            Log.d("mylog", "Преобразовано в байт код");

            dbHelper = new DBHelper(context);
            Log.d("mylog", "Создан DBHelper");

            ContentValues cv = new ContentValues();
            Log.d("mylog", "Создан ContentValues");

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Log.d("mylog", "DB подключена");

            cv.put("lat", One.getLongitude());
            cv.put("long", One.getLatitude());
            cv.put("thisDate", String.valueOf(One.getDate()));

            cv.put("photo", arr);
            Log.d("mylog", "Загружено фото в DB");

            db.delete("photoDB", null, null);
            Log.d("mylog", "DB очищена");
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);


    }


}