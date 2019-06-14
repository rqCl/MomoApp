package com.example.navigationsandtabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

        int mNoOfTabs;

        public PagerAdapter(FragmentManager fm, int mNoOfTabs) {
            super(fm);
            this.mNoOfTabs =    mNoOfTabs;
        }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Home home = new Home();
                return home;
            case 1:
                Events events = new Events();
                return events;
            case 2:
                Challenges challenges = new Challenges();
                return challenges;
            case 3:
                Redeem redeem = new Redeem();
                return redeem;
            case 4:
                Profile profile = new Profile();
                return profile;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
