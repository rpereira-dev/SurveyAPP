package rpereira.sondage.activities.trend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rpereira.sondage.R;

/**
 * Created by Romain on 08/04/2017.
 */

public class TrendFragment extends Fragment {

    public TrendFragment() {
        // Required empty public constructor
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profil, container, false);
        TextView tv = (TextView) v.findViewById(R.id.fragment_text);
        tv.setText("trend");
        return (v);
    }
}
