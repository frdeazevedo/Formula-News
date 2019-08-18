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

        this.initGui();
        this.openMainPage();
        //this.openNewsPage();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.closeAllFragments();

        switch(item.getItemId()) {
            case R.id.action_news: {
                this.openNewsPage();
                break;
            }
            case R.id.action_standings: {
                this.openStandingsPage();
                break;
            }
            case R.id.action_videos: {
                this.openVideosPage();
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
        Fragment[] featuredFragment = new Fragment[1];
        featuredFragment[0] = new FeaturedFragment("Radio Ga Ga - Remastered");

        openFragment(featuredFragment);
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

    public void openVideosPage() {

    }

    private BottomNavigationView mBottomNavigationView;

    private void openFragment(Fragment[] fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        int i=0;
        for(Fragment f:fragment) {
            i++;
            transaction.add(R.id.container, f, "News"+i);
        }

        transaction.commit();
    }

    private void closeAllFragments() {
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        }
    }
}
