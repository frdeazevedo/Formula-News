package com.formulanews;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class StandingsFragment extends Fragment {


    public StandingsFragment(List<Driver> drivers, List<Constructor> constructors) {
        this.mDrivers = drivers;
        this.mConstructors = constructors;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standings, container, false);

        StandingsViewPagerAdapter standingsViewPagerAdapter = new StandingsViewPagerAdapter(getChildFragmentManager());
        standingsViewPagerAdapter.setDriverList(this.mDrivers);
        standingsViewPagerAdapter.setConstructorList(this.mConstructors);

        ViewPager viewPager = view.findViewById(R.id.standings_view_pager);
        viewPager.setAdapter(standingsViewPagerAdapter);


        TabLayout tabLayout = view.findViewById(R.id.standings_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    List<Driver> mDrivers;
    List<Constructor> mConstructors;
}
