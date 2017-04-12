package rpereira.sondage.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import rpereira.sondage.R;
import rpereira.sondage.activities.home.ActivityTabHome;
import rpereira.sondage.activities.home.HomeFragment;
import rpereira.sondage.activities.informations.InformationActivity;
import rpereira.sondage.activities.profil.ActivityTabProfil;
import rpereira.sondage.activities.profil.ProfilFragment;
import rpereira.sondage.activities.survey.ActivityTabSurvey;
import rpereira.sondage.activities.survey.SurveyFragment;
import rpereira.sondage.activities.trend.ActivityTabTrend;
import rpereira.sondage.activities.trend.TrendFragment;
import rpereira.sondage.network.session.GoogleSession;
import rpereira.sondage.network.session.SessionFragment;
import rpereira.sondage.network.session.SessionManager;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static java.security.AccessController.getContext;

/**
 * Created by Romain on 07/04/2017.
 */

public class MainActivity extends AppCompatActivity {

    private static final int TAB_HOME   = 0;
    private static final int TAB_TREND  = 1;
    private static final int TAB_SURVEY = 2;
    private static final int TAB_PROFIL = 3;
    private static final int TAB_MAX    = 4;

    private SessionManager sessionManager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    public MainActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize session stuff
        this.sessionManager = new SessionManager(this);
        this.sessionManager.init();

        //set tabs
        this.setContentView(R.layout.activity_main);

        //toolbar
        this.toolbar = (Toolbar)this.findViewById(R.id.toolbar);
        this.toolbar.setTitleTextColor(this.getResources().getColor(R.color.colorTitle));
        this.setSupportActionBar(this.toolbar);

        //search button setup

        //informations button setup
        ImageButton imageButtonInformations = (ImageButton) this.findViewById(R.id.imageButtonInformation);
        imageButtonInformations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), InformationActivity.class));
            }
        });

        //setup tabs layout
        this.tabLayout = (TabLayout) this.findViewById(R.id.tabs);
        this.viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        this.viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        this.viewPagerAdapter.linkToViewPager(tabLayout, viewPager);

        //when we swip pages, update the selected tab
        this.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));

        //when we click on a tab, it changes the viewpager adapter cursor
        this.tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(this.viewPager));

        //set icon to white when selected
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                viewPagerAdapter.onPageSelected(position);
                toolbar.setTitle(viewPagerAdapter.getActionBarTitle( position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        //set default pager (and so toggle events to setup everything properly)
        this.viewPager.setCurrentItem(0);
    }

    public SessionManager getSessionManager() {
        return (this.sessionManager);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.getSessionManager().onMainActivityResult(requestCode, resultCode, data);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final ActivityTab[] tabs;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.tabs = new ActivityTab[TAB_MAX];
        }

        @Override
        public Fragment getItem(int position) {
            return (this.tabs[position].getFragment());
        }

        @Override
        public int getCount() {
            return (this.tabs.length);
        }

        public void linkToViewPager(TabLayout tabLayout, ViewPager viewPager) {

            this.tabs[TAB_HOME] = new ActivityTabHome(tabLayout);
            this.tabs[TAB_TREND] = new ActivityTabTrend(tabLayout);
            this.tabs[TAB_SURVEY] = new ActivityTabSurvey(tabLayout);
            this.tabs[TAB_PROFIL] = new ActivityTabProfil(tabLayout);

            viewPager.setAdapter(viewPagerAdapter);

            for (ActivityTab tab : this.tabs) {
                tabLayout.addTab(tab.getTab());
            }
            tabLayout.getTabAt(TAB_HOME).select();

            Resources res = tabLayout.getResources();
            tabLayout.setSelectedTabIndicatorColor(res.getColor(R.color.colorSelectedTabIndicator));
            tabLayout.setTabTextColors(res.getColor(R.color.colorUnselectedTabText), res.getColor(R.color.colorSelectedTabText));
        }

        public CharSequence getActionBarTitle(int position) {
            return (this.tabs[position].getTitle());
        }

        public void onPageSelected(int position) {

            for (ActivityTab tab : this.tabs) {
                if (tab.getTab().getPosition() == position) {
                    tab.setWhiteIcon();
                } else {
                    tab.setBlackIcon();
                }
            }
        }
    }
}
