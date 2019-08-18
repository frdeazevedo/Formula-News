package com.formulanews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
                          implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        this.mBottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        this.mBottomNavigationView.setOnNavigationItemSelectedListener(this);
*/

        this.initGui();

        /*
        Fragment[] newsFragment = new NewsFragment[15];

        for(int i=0; i<15; i++) {
            NewsFragment nf = new NewsFragment("Headline "+ i, "Texto da notícia " + i);
            newsFragment[i] = nf;
        }

        openFragment(newsFragment);
        */

        this.openNewsPage();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_news: {
                this.closeAllFragments();
                this.openNewsPage();
                break;
            }
            case R.id.action_standings: {
                this.closeAllFragments();
                break;
            }
            case R.id.action_videos: {
                this.closeAllFragments();
                break;
            }
        }

        return true;
    }

    public void initGui() {
        this.mBottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        this.mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    public void openMainPage() {

    }

    public void openNewsPage() {
        Fragment[] newsFragment = new NewsFragment[15];

        for(int i=0; i<15; i++) {
            NewsFragment nf = new NewsFragment("Headline "+ i, "Texto da notícia " + i);
            newsFragment[i] = nf;
        }

        openFragment(newsFragment);
    }

    public void openStandingsPage() {

    }

    private BottomNavigationView mBottomNavigationView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private void openFragment(Fragment[] fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        for(int i=0; i<15; i++) {
            transaction.add(R.id.container, fragment[i], "News"+i);
        }

        transaction.commit();
    }

    private void closeAllFragments() {
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        }
    }
}
