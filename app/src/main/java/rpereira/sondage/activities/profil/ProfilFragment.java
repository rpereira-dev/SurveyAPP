package rpereira.sondage.activities.profil;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rpereira.sondage.R;
import rpereira.sondage.activities.ImageTextButton;
import rpereira.sondage.activities.LoginActivity;

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
        ImageTextButton imageTextButton = (ImageTextButton) v.findViewById(R.id.profil_imageTextButton);
        imageTextButton.setClickable(true);
        imageTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return (v);
    }
}

