package rpereira.sondage.activities.home;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import rpereira.sondage.R;
import rpereira.sondage.activities.ActivityTab;

/**
 * Created by Romain on 09/04/2017.
 */

public class ActivityTabHome extends ActivityTab {

    public ActivityTabHome(TabLayout tabLayout) {
        super(tabLayout, R.drawable.home_white, R.drawable.home_black, R.string.voc_home, Fragment.instantiate(tabLayout.getContext(), HomeFragment.class.getName()));
        this.setWhiteIcon();
    }
}
