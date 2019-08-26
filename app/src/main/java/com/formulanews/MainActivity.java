package com.formulanews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends    AppCompatActivity
                          implements BottomNavigationView.OnNavigationItemSelectedListener,
                                     FetchDataAsyncTask.OnJSONResponse {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.initGui();
        this.testing();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.closeAllFragments();

        switch(item.getItemId()) {
            case R.id.action_news: {
                if(this.mNewsList != null) {
                    List<Fragment> fragmentList = new ArrayList<>();

                    fragmentList.add(new FeaturedFragment(this, this.mNewsList.get(0)));

                    int doublenews_counter = 0;

                    for(int i=1; i<this.mNewsList.size(); i++) {
                        if(doublenews_counter == 2) {
                            doublenews_counter = 0;

                            fragmentList.add(new DoubleNewsFragment(this, this.mNewsList.get(i)));
                        } else {
                            fragmentList.add(new NewsFragment(this, this.mNewsList.get(i)));
                        }

                        doublenews_counter++;
                    }

                    this.openFragmentList(fragmentList);
                }

                break;
            }
            case R.id.action_standings: {
                break;
            }
            case R.id.action_videos: {
                break;
            }
        }

        return true;
    }

    public void initGui() {
        this.mBottomNavigationView = findViewById(R.id.bottom_navigation);
        this.mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void openFragmentList(List<Fragment> fragmentList) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        int i=0;
        for(Fragment f:fragmentList) {
            i++;
            transaction.add(R.id.container, f, "Fragment"+i);
        }

        transaction.commit();
    }

    private void closeAllFragments() {
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        }
    }

    private void testing() {
        FetchDataAsyncTask fetchDataAsyncTask = new FetchDataAsyncTask(this);
        fetchDataAsyncTask.execute("https://my-json-server.typicode.com/frdeazevedo/fake_rest/news");
    }

    private void onDataLoad() {
    }

    /*
     * Response after querying JSON web service
     */
    public void onResponse(String result) {
        JSONArray jarray;
        this.mNewsList= new ArrayList<>();

        try {
            jarray = new JSONArray(result);

            for(int i=0; i < jarray.length(); i++) {
                JSONObject jobj = jarray.getJSONObject(i);

                News news = new News();
                news.mNewsId = jobj.getString("id");
                news.mNewsHeader = jobj.getString("header");
                news.mNewsIntro = jobj.getString("intro");
                news.mNewsPublishedDate = jobj.getString("published_date");
                news.mNewsUpdatedDate = jobj.getString("updated_date");
                news.mNewsAuthor = jobj.getString("author");
                news.mNewsBody = jobj.getString("body");

                this.mNewsList.add(news);
            }


        } catch(Exception e) {
            Log.d("DBG", e.getMessage());
        }
    }

    private BottomNavigationView mBottomNavigationView;
    private List<News> mNewsList;
}
