package gmaps.dmitrydenezho.com.geoproj.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import gmaps.dmitrydenezho.com.geoproj.R;


/**
 * Created by Dmitry on 26.12.2015.
 */
public class Two extends AbstractTabFragment {


    private static final int LAYOUT = R.layout.second_fragment;
    public static Two getInstance(Context context) {

        Bundle args = new Bundle();
        Two fragment = new Two();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle("two");
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }


}
