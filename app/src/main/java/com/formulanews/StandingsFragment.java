package com.formulanews;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class StandingsFragment extends Fragment {


    public StandingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standings, container, false);

        ViewPager viewPager = view.findViewById(R.id.standings_view_pager);
        viewPager.setAdapter(new StandingsViewPagerAdapter(getChildFragmentManager()));

        TabLayout tabLayout = view.findViewById(R.id.standings_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
