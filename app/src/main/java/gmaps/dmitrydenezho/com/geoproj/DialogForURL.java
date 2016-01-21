package gmaps.dmitrydenezho.com.geoproj;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import gmaps.dmitrydenezho.com.geoproj.Loaders.ImageLoaderURL;


/**
 * Created by Dmitry on 15.01.2016.
 */
public class DialogForURL extends DialogFragment implements OnClickListener {
    EditText et_dialog_url;
    Context context;






    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog, null);
        context = getActivity().getApplicationContext();
        et_dialog_url = (EditText) v.findViewById(R.id.et_dialog_url);
        v.findViewById(R.id.btn_dialog_ready).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {

        new ImageLoaderURL().execute(String.valueOf(et_dialog_url.getText()));
        dismiss();
    }

}
