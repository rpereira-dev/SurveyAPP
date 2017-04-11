package rpereira.sondage.activities.survey;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.widget.TableLayout;

import rpereira.sondage.R;
import rpereira.sondage.activities.ActivityTab;
import rpereira.sondage.activities.home.HomeFragment;

/**
 * Created by Romain on 09/04/2017.
 */

public class ActivityTabSurvey extends ActivityTab {

    public ActivityTabSurvey(TabLayout tabLayout) {
        super(tabLayout, R.drawable.ic_action_new_survey_white, R.drawable.ic_action_new_survey_black, R.string.voc_new, Fragment.instantiate(tabLayout.getContext(), SurveyFragment.class.getName()));
    }
}
