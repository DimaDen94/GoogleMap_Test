package gmaps.dmitrydenezho.com.geoproj;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class ShowActivity extends AppCompatActivity {
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        String key = intent.getStringExtra("img");
        imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageURI(Uri.parse(key));

    }


}
