package gmaps.dmitrydenezho.com.geoproj.Loaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;



/**
 * Created by Dmitry on 16.01.2016.
 */
public class AdapterLoader extends AsyncTask<String, String, byte[]> {
    ImageView bmImage;

    Context context;

    @Override
    protected byte[] doInBackground(String... adress) {
        return null;
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
