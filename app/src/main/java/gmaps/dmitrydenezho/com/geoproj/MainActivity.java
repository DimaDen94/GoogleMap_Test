package gmaps.dmitrydenezho.com.geoproj;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.app.DialogFragment;

import gmaps.dmitrydenezho.com.geoproj.Loaders.ImageLoaderGallery;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final int GALLERY_REQUEST = 1;
    Button btnForGallery;
    Button btnForURL;
    DialogFragment dialog;
    ImageView imageView;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnForGallery = (Button) findViewById(R.id.btn_gallery);
        btnForGallery.setOnClickListener(this);

        btnForURL = (Button) findViewById(R.id.btn_urly);
        btnForURL.setOnClickListener(this);
        context = getApplicationContext();

        dialog = new DialogForURL();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        imageView = (ImageView) findViewById(R.id.img);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){

                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_gallery:
                Intent imgPickerIntent = new Intent(Intent.ACTION_PICK);
                imgPickerIntent.setType("image/*");
                startActivityForResult(imgPickerIntent, GALLERY_REQUEST);
                break;
            case R.id.btn_urly:
                dialog.show(getFragmentManager(),"URL");
                break;
        }
    }
}
