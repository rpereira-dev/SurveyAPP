package rpereira.sondage.activities.profil;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import rpereira.sondage.R;
import rpereira.sondage.activities.ActivityTab;
import rpereira.sondage.activities.home.HomeFragment;

/**
 * Created by Romain on 09/04/2017.
 */

public class ActivityTabProfil extends ActivityTab {

    public ActivityTabProfil(TabLayout tabLayout) {
        super(tabLayout, R.drawable.ic_action_profil_white, R.drawable.ic_action_profil_black, R.string.voc_profil, Fragment.instantiate(tabLayout.getContext(), ProfilFragment.class.getName()));
    }
}
