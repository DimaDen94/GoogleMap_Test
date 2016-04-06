package gmaps.dmitrydenezho.com.geoproj;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog, null);

        et_dialog_url = (EditText) v.findViewById(R.id.et_dialog_url);
        v.findViewById(R.id.btn_dialog_ready).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(TextUtils.isEmpty(et_dialog_url.getText().toString()))
            return;
        new ImageLoaderURL().execute(String.valueOf(et_dialog_url.getText()));
        dismiss();
    }

}
