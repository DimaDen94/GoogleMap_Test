package gmaps.dmitrydenezho.com.geoproj.Loaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

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

import gmaps.dmitrydenezho.com.geoproj.DB;
import gmaps.dmitrydenezho.com.geoproj.MainActivity;



/**
 * Created by Dmitry on 15.01.2016.
 */
public class ImageLoaderURL extends AsyncTask<String, String, File> {
DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    protected File doInBackground(String... urls) {
        String urldisplay = urls[0];
        double lat = MainActivity.getLatitude();
        double lon = MainActivity.getLongitude();


        Bitmap bitmap;
        bitmap = loadImageFromUrl(urldisplay);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

        File storagePath = new File(Environment.getExternalStorageDirectory(),"forGeo");
        storagePath.mkdirs();

        File f = new File(storagePath + "/" + "geo_"
                + System.currentTimeMillis() + ".jpg");

        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());


            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainActivity.getDatabase().addRec(""+lat,""+lon,dateFormat.format(new Date()), String.valueOf(f));

        return f;
    }

    protected void onPostExecute(File result) {

    }

    public static Bitmap loadImageFromUrl(String url) {
        URL m;
        InputStream i;
        BufferedInputStream bis;
        ByteArrayOutputStream out =null;
        try {
            m = new URL(url);
            i = (InputStream) m.getContent();
            bis = new BufferedInputStream(i,1024 * 8);
            out = new ByteArrayOutputStream();
            int len;
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