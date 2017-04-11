package rpereira.sondage.activities.survey;

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

public class SurveyFragment extends Fragment {

    public SurveyFragment() {
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
        View v = inflater.inflate(R.layout.fragment_empty, container, false);
        return (v);
    }
}

