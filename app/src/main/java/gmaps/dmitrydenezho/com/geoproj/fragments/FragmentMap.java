package gmaps.dmitrydenezho.com.geoproj.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

import gmaps.dmitrydenezho.com.geoproj.DB;
import gmaps.dmitrydenezho.com.geoproj.MainActivity;
import gmaps.dmitrydenezho.com.geoproj.R;


/**
 * Created by Dmitry on 26.12.2015.
 */
public class FragmentMap extends AbstractTabFragment {
    GoogleMap map;
    Cursor cursor;
    DB database;
    public static Map<Double,Double> cor;
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


    }

    @Override
    public void onPause() {
        super.onPause();
        for(Map.Entry<Double, Double> c:cor.entrySet()){

            map.addMarker(new MarkerOptions()
                    .position(new LatLng(c.getKey(), c.getValue()))
                    .title("Hello world"));

        }
    }

    private void createMapView(){
        try {
            if(null==map){
                map =((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.mapView)).getMap();
                if(map == null){
                    Toast.makeText(context,"Ошибка",Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){

        }

    }

}
