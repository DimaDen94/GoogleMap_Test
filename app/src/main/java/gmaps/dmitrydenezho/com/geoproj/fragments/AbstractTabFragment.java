package gmaps.dmitrydenezho.com.geoproj.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Dmitry on 27.12.2015.
 */
public abstract class AbstractTabFragment  extends Fragment {
    private String title;
    protected Context context;
    protected View view;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContext(Context context) {
        this.context = context;
    }
}
