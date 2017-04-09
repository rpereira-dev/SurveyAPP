package rpereira.sondage.activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

/**
 * Created by Romain on 09/04/2017.
 */

public class ActivityTab {

    private final TabLayout tabLayout;
    private final Fragment fragment;
    private final TabLayout.Tab tab;

    private int whiteIcon;
    private int blackIcon;
    private int curIcon;

    public ActivityTab(TabLayout tabLayout, int whiteIcon, int blackIcon, int title, Fragment fragment) {
        this.tabLayout = tabLayout;
        this.fragment = fragment;
        this.tab = tabLayout.newTab();
        this.whiteIcon = whiteIcon;
        this.blackIcon = blackIcon;
        this.setBlackIcon();
        this.setText(title);
    }

    public Drawable getIcon() {
        return (this.tab.getIcon());
    }

    public CharSequence getTitle() {
        return (this.tab.getText());
    }

    public Fragment getFragment() {
        return (this.fragment);
    }

    public TabLayout getTabLayout() {
        return (this.tabLayout);
    }

    public Context getContext() {
        return (this.tabLayout.getContext());
    }

    public TabLayout.Tab getTab() {
        return (this.tab);
    }

    public void setWhiteIcon() {
        this.setIcon(this.whiteIcon);
    }

    public void setBlackIcon() {
        this.setIcon(this.blackIcon);
    }

    public void setIcon(int icon) {
        if (this.curIcon == icon) {
            return ;
        }
        this.curIcon = icon;
        this.tab.setIcon(this.curIcon);
    }

    public void setText(int resID) {
        this.tab.setText(resID);
    }
}
