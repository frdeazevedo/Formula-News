package com.formulanews;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class StandingsViewPagerAdapter extends FragmentPagerAdapter {
    public StandingsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new DriverStandingsFragment();
            case 1:
                return new ConstructorStandings();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return "Drivers";
            case 1:
                return "Constructors";
        }

        return null;
    }
}
