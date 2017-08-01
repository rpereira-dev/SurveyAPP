package rpereira.sondage.activities.profil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rpereira.sondage.R;
import rpereira.sondage.activities.views.ImageTextButton;
import rpereira.sondage.activities.MainActivity;
import rpereira.sondage.network.session.OnSessionChangedListener;
import rpereira.sondage.network.session.Session;
import rpereira.sondage.network.session.SessionManager;

/**
 * Created by Romain on 08/04/2017.
 */

public class ProfilFragment extends Fragment implements OnSessionChangedListener {

    private ImageTextButton loginImageTextButton;

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
        this.loginImageTextButton = (ImageTextButton) v.findViewById(R.id.profil_imageTextButton);
        this.loginImageTextButton.setClickable(true);
        this.loginImageTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                SessionManager sessionManager = mainActivity.getSessionManager();
                sessionManager.showSessionFragment();
            }
        });

        MainActivity mainActivity = (MainActivity) getActivity();
        SessionManager sessionManager = mainActivity.getSessionManager();
        sessionManager.showSessionFragment();
        sessionManager.addOnSessionChangedListener(this);

        return (v);
    }

    @Override
    public void onSessionChanged(Session session) {
        if (session == null) {
            this.setLoginButton();
        } else {
            this.setLogoutButton();
        }
    }

    public void setLoginButton() {

    }

    public void setLogoutButton() {

    }
}

