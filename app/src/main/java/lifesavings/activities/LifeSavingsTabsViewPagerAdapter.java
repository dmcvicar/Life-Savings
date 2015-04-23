package lifesavings.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class LifeSavingsTabsViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    public static final int ENTER = 0;
    public static final int RECORD = 1;
    public static final int SAVINGS = 2;
    public static final int HISTORY = 3;
    public static final String UI_TAB_ENTER = "ENTER";
    public static final String UI_TAB_RECORD = "RECORD";
    public static final String UI_TAB_SAVINGS = "SAVINGS";
    public static final String UI_TAB_HISTORY = "HISTORY";

    public LifeSavingsTabsViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    public Fragment getItem(int pos){
        return fragments.get(pos);
    }

    public int getCount(){
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case ENTER:
                return UI_TAB_ENTER;
            case RECORD:
                return UI_TAB_RECORD;
            case SAVINGS:
                return UI_TAB_SAVINGS;
            case HISTORY:
                return UI_TAB_HISTORY;
            default:
                break;
        }
        return null;
    }
}
