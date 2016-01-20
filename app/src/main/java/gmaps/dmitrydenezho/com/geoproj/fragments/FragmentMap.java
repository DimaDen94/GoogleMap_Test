package gmaps.dmitrydenezho.com.geoproj.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;

import gmaps.dmitrydenezho.com.geoproj.InfoImg;
import gmaps.dmitrydenezho.com.geoproj.MainActivity;
import gmaps.dmitrydenezho.com.geoproj.PointFinder;
import gmaps.dmitrydenezho.com.geoproj.R;


/**
 * Created by Dmitry on 26.12.2015.
 */public class FragmentMap extends AbstractTabFragment {
    GoogleMap map;
    public static ArrayList<InfoImg> cor;

    Button allTime;
    Button thisDay;
    private static final int LAYOUT = R.layout.map_fragment;
    public static FragmentMap getInstance(Context context) {

        Bundle args = new Bundle();
        FragmentMap fragment = new FragmentMap();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle("map");
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
        cor = MainActivity.cor;
        createMapView();
        allTime = (Button) getActivity().findViewById(R.id.btn_update_map_all_time);
        allTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PointFinder finder = new PointFinder();
                finder.finder(map, cor);
            }
        });
        thisDay = (Button) getActivity().findViewById(R.id.btn_update_map_this_day);
        thisDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PointFinder finder = new PointFinder();
                finder.thisDayFinder(map,cor);
            }
        });


    }

    private void createMapView(){
        try {
            if(null==map){
                map =((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.mapView)).getMap();

                if(map == null){
                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){

        }

    }

}