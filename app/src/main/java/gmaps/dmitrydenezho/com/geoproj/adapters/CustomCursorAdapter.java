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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.File;
import gmaps.dmitrydenezho.com.geoproj.DB;
import gmaps.dmitrydenezho.com.geoproj.DBHelper;
import gmaps.dmitrydenezho.com.geoproj.R;

/**
 * Created by Dmitry on 21.01.2016.
 */
public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    ImageView imageView;
    Context context;
    int id;
    public CustomCursorAdapter(Context context, Cursor c, int flags, int idLayout) {
        super(context, c, flags);
        this.context =context;
        this.id = idLayout;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ((TextView) view.findViewById(R.id.tv1)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_LAT)));
        ((TextView) view.findViewById(R.id.tv2)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_LON)));
        ((TextView) view.findViewById(R.id.tv3)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATA)));

        imageView = (ImageView) view.findViewById(R.id.image);
        Uri path = Uri.parse(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_IMG)));


        Picasso.with(context).load(path).resize(200, 200). centerInside().into(imageView);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(id, parent, false);
    }



}