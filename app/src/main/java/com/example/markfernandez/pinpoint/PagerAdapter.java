package com.example.markfernandez.pinpoint;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.markfernandez.pinpoint.fragment.Map_page;
import com.example.markfernandez.pinpoint.fragment.Newsfeed_page;
import com.example.markfernandez.pinpoint.fragment.Profile_page;

/**
 * Created by AstroNuts on 1/5/2017.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Map_page tab1 = new Map_page();
                return tab1;
            case 1:
                Newsfeed_page tab2 = new Newsfeed_page();
                return tab2;
            case 2:
                Profile_page tab3 = new Profile_page();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
