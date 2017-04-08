package rpereira.sondage.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import rpereira.sondage.R;
import rpereira.sondage.util.Logger;

/**
 * Created by Romain on 07/04/2017.
 */

public class MainActivity extends AppCompatActivity {

    private static final int[] TAB_ICONS = {
            R.drawable.home,
            R.drawable.trend,
            R.drawable.new_survey,
            R.drawable.profil,
    };

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        //toolbar
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(this.toolbar);
        this.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //TODO: search
                Logger.log("tapped");
                return (false);
            }
        });


        //tab layout / view pager setup
        this.tabLayout = (TabLayout) this.findViewById(R.id.tabs);
        this.viewPager = new ViewPager(this);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "HOME");
        adapter.addFragment(new OneFragment(), "TRENDS");
        adapter.addFragment(new OneFragment(), "NEW");
        adapter.addFragment(new OneFragment(), "PROFIL");
        this.viewPager.setAdapter(adapter);
        this.tabLayout.setupWithViewPager(viewPager);

        //icon setup
        for (int i = 0 ; i < TAB_ICONS.length ; i++) {
            this.tabLayout.getTabAt(i).setIcon(TAB_ICONS[i]);
        }

        //tab listener to properlly swip
        // Create a tab listener that is called when the user changes tabs.

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return (this.fragmentList.get(position));
        }

        @Override
        public int getCount() {
            return (this.fragmentList.size());
        }

        public void addFragment(Fragment fragment, String title) {
            this.fragmentList.add(fragment);
            this.fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (this.fragmentTitleList.get(position));
        }
    }

}
