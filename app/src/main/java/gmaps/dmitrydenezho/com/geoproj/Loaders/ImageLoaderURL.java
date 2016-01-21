package gmaps.dmitrydenezho.com.geoproj.Loaders;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import gmaps.dmitrydenezho.com.geoproj.MainActivity;
import gmaps.dmitrydenezho.com.geoproj.fragments.FragmentMark;


/**
 * Created by Dmitry on 15.01.2016.
 */
public class ImageLoaderURL extends AsyncTask<String, String, File> {
DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    double lat;
    double lon;
    protected File doInBackground(String... urls) {
        String urldisplay = urls[0];
        double lat = FragmentMark.getLatitude();
        double lon = FragmentMark.getLongitude();


        Bitmap bitmap = null;
        bitmap = loadImageFromUrl(urldisplay);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

        File storagePath = new File(Environment.getExternalStorageDirectory(),"forGeo");
        storagePath.mkdirs();
//you can create a new file name "test.jpg" in sdcard folder.
        String name = urldisplay.replaceAll("/","");
        File f = new File(storagePath + File.separator+ name.substring(7,27) + ".jpg");

        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

// remember close de FileOutput
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//write the bytes in file
        MainActivity.getDb().addRec(""+lat,""+lon,dateFormat.format(new Date()), String.valueOf(f));

        return f;
    }

    protected void onPostExecute(File result) {

    }

    public static Bitmap loadImageFromUrl(String url) {
        URL m;
        InputStream i = null;
        BufferedInputStream bis = null;
        ByteArrayOutputStream out =null;
        try {
            m = new URL(url);
            i = (InputStream) m.getContent();
            bis = new BufferedInputStream(i,1024 * 8);
            out = new ByteArrayOutputStream();
            int len=0;
            byte[] buffer = new byte[1024];
            while((len = bis.read(buffer)) != -1){
                out.write(buffer, 0, len);
            }
            out.close();
            bis.close();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = out.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        return bitmap;
    }

}