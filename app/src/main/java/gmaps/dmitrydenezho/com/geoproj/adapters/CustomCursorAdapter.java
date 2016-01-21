package gmaps.dmitrydenezho.com.geoproj.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import gmaps.dmitrydenezho.com.geoproj.DB;
import gmaps.dmitrydenezho.com.geoproj.InfoImg;
import gmaps.dmitrydenezho.com.geoproj.R;

/**
 * Created by Dmitry on 21.01.2016.
 */
public class CustomCursorAdapter extends CursorAdapter {
DownloadImageTask task;
    private LayoutInflater mInflater;
    ImageView imageView;
    Context context;
    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
this.context =context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if (cursor.getPosition() % 2 == 1) {
            view.setBackgroundColor(context.getResources().getColor(R.color.backGround));
        } else {
            view.setBackgroundColor(context.getResources().getColor(R.color.backGround));
        }


        ((TextView) view.findViewById(R.id.tv1)).setText(cursor.getString(cursor.getColumnIndex(DB.COLUMN_LAT)));
        ((TextView) view.findViewById(R.id.tv2)).setText(cursor.getString(cursor.getColumnIndex(DB.COLUMN_LON)));
        ((TextView) view.findViewById(R.id.tv3)).setText(cursor.getString(cursor.getColumnIndex(DB.COLUMN_DATA)));

        imageView = (ImageView) view.findViewById(R.id.image);
        Uri path = Uri.parse(cursor.getString(cursor.getColumnIndex(DB.COLUMN_IMG)));

        //imageView.setImageURI(path);
        task = new DownloadImageTask(imageView);
        task.execute(String.valueOf(path));

        Log.e("my",cursor.getString(cursor.getColumnIndex(DB.COLUMN_IMG)));

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.itemlist, parent, false);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Uri path = Uri.parse(urldisplay);

            File imageFile = new File(getRealPathFromURI(path));
            Bitmap mIcon11 = null;
            try {

                BitmapFactory.Options op = new BitmapFactory.Options();
                op.inPreferredConfig = Bitmap.Config.RGB_565; //без альфа-канала.
                op.inSampleSize = 4; //чем больше число (1-16), тем хуже качество, но меньше потребление памяти. Качество 4 практически не заметно на маленьких значках.

                mIcon11 = BitmapFactory.decodeFile(String.valueOf(imageFile), op);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
        private String getRealPathFromURI(Uri contentURI) {
            Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) { // Source is Dropbox or other similar local file path
                return contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                return cursor.getString(idx);
            }
        }
    }

}