package rpereira.sondage.activities.trend;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import rpereira.sondage.R;
import rpereira.sondage.activities.ActivityTab;
import rpereira.sondage.activities.home.HomeFragment;

/**
 * Created by Romain on 09/04/2017.
 */

public class ActivityTabTrend extends ActivityTab {

    public ActivityTabTrend(TabLayout tabLayout) {
        super(tabLayout, R.drawable.ic_action_trend_white, R.drawable.ic_action_trend_black, R.string.voc_trend, Fragment.instantiate(tabLayout.getContext(), TrendFragment.class.getName()));
    }
}
