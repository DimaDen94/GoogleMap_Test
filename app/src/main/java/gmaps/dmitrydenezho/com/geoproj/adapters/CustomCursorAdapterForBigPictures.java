package gmaps.dmitrydenezho.com.geoproj.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import gmaps.dmitrydenezho.com.geoproj.DB;
import gmaps.dmitrydenezho.com.geoproj.R;

/**
 * Created by Dmitry on 21.01.2016.
 */
public class CustomCursorAdapterForBigPictures extends CursorAdapter {

    private LayoutInflater mInflater;
    ImageView imageView;
    Context context;
    int id;
    int w;
    int h;
    public CustomCursorAdapterForBigPictures(Context context, Cursor c, int flags, int idLayout,int w, int h) {
        super(context, c, flags);
        this.context =context;
        this.id = idLayout;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.w = w;
        this.h = h;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        ((TextView) view.findViewById(R.id.tv3)).setText(cursor.getString(cursor.getColumnIndex(DB.COLUMN_DATA)));

        imageView = (ImageView) view.findViewById(R.id.image);
        Uri path = Uri.parse(cursor.getString(cursor.getColumnIndex(DB.COLUMN_IMG)));

        Picasso.with(context).load(path).resize(w, h). centerInside().into(imageView);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(id, parent, false);
    }



}