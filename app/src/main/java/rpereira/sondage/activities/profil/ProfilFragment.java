package rpereira.sondage.activities.profil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rpereira.sondage.R;
import rpereira.sondage.activities.ImageTextButton;
import rpereira.sondage.activities.MainActivity;
import rpereira.sondage.network.session.SessionFragment;
import rpereira.sondage.network.session.SessionManager;
import rpereira.sondage.util.Logger;

/**
 * Created by Romain on 08/04/2017.
 */

public class ProfilFragment extends Fragment {

    public ProfilFragment() {
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
        final ImageTextButton imageTextButton = (ImageTextButton) v.findViewById(R.id.profil_imageTextButton);
        imageTextButton.setClickable(true);
        imageTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                SessionManager sessionManager = mainActivity.getSessionManager();
                sessionManager.showSessionFragment();
            }
        });
        return (v);
    }
}

