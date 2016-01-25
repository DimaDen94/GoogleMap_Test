package gmaps.dmitrydenezho.com.geoproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {
    GoogleMap map;
    public static ArrayList<InfoImg> cor;

    Button allTime;
    Button thisDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        cor = MainActivity.imgArrayList;
        //Создание карты
        createMapView();
        //поиск фото за все время
        allTime = (Button) findViewById(R.id.btn_update_map_all_time);
        allTime.setOnClickListener(this);
        //поиск фото за день
        thisDay = (Button) findViewById(R.id.btn_update_map_this_day);
        thisDay.setOnClickListener(this);
    }

    private void createMapView(){
        try {
            if(null==map){
                map =((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMap();

                if(map == null){
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){

        }

    }

    @Override
    public void onClick(View v) {
        PointFinder finder = new PointFinder();
        switch (v.getId()){
            case R.id.btn_update_map_all_time:
                finder.finder(map, cor);
                break;
            case R.id.btn_update_map_this_day:
                finder.thisDayFinder(map, cor);
                break;
        }
    }
}
