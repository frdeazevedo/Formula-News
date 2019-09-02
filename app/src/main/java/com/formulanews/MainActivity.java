package com.formulanews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
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

        this.mNewsList = new ArrayList<>();
        this.mVideosList = new ArrayList<>();
        this.mProgressBar.setVisibility(View.VISIBLE);

        //Fetches the JSON of the news
        FetchDataAsyncTask fetchNewsDataAsyncTask = new FetchDataAsyncTask(this, MainActivity.FORMULANEWS_JSON_QUERY_NEWS);
        fetchNewsDataAsyncTask.execute(MainActivity.FORMULANEWS_JSON_QUERY_NEWS);

        //Fetches the JSON of the videos
        FetchDataAsyncTask fetchVideosDataAsyncTask = new FetchDataAsyncTask(this, MainActivity.FORMULANEWS_JSON_QUERY_VIDEOS);
        fetchVideosDataAsyncTask.execute(MainActivity.FORMULANEWS_JSON_QUERY_VIDEOS);

        //Fetches the JSON of the drivers standings
        FetchDataAsyncTask fetchDriverStandingsDataAsyncTask = new FetchDataAsyncTask(this, MainActivity.FORMULANEWS_JSON_QUERY_DRIVER_STANDINGS);
        fetchDriverStandingsDataAsyncTask.execute(MainActivity.FORMULANEWS_JSON_QUERY_DRIVER_STANDINGS);

        //Fetches the JSON of the drivers standings
        FetchDataAsyncTask fetchConstructorStandingsDataAsyncTask = new FetchDataAsyncTask(this, MainActivity.FORMULANEWS_JSON_QUERY_CONSTRUCTOR_STANDINGS);
        fetchConstructorStandingsDataAsyncTask.execute(MainActivity.FORMULANEWS_JSON_QUERY_CONSTRUCTOR_STANDINGS);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.closeAllFragments();

        switch(item.getItemId()) {
            case R.id.action_news: {
                this.openNews();
                break;
            }
            case R.id.action_standings: {
                this.openStandings();
                break;
            }
            case R.id.action_videos: {
                this.openVideos();
                break;
            }
        }

        return true;
    }

    /*
     * Response after querying JSON web service.
     *     url is the URL that was queried
     *     result is the JSON string result of the URL queried
     */
    public void onResponse(String url, String result) {
        this.mProgressBar.setVisibility(View.GONE);

        String requestedService = null;

        try {
            URI uri = new URI(url);
            String path = uri.getPath();
            requestedService = path.substring(path.lastIndexOf("/")+1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Toast toast = new Toast(this);
            toast.setText("Connection error.");
            toast.show();
        }

        switch(requestedService) {
            case "news":
                this.onNewsJSONQueryResponse(result);
                break;
            case "videos":
                this.onVideosJSONQueryResponse(result);
                break;
            case "driver_standings":
                this.onDriverStandingsJSONQueryResponse(result);
                break;
            case "constructor_standings":
                this.onConstructorStandingsJSONQueryResponse(result);
                break;
        }
    }

    private void initGui() {
        this.mBottomNavigationView = findViewById(R.id.bottom_navigation);
        this.mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        this.mProgressBar = findViewById(R.id.progress_bar);
    }

    private void onNewsJSONQueryResponse(String result) {
        this.mNewsList = new ArrayList<>();

        JSONArray jarray;

        try {
            jarray = new JSONArray(result);

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jobj = jarray.getJSONObject(i);

                News news = new News();
                news.mNewsId = jobj.getString("id");
                news.mNewsHeader = jobj.getString("header");
                news.mNewsIntro = jobj.getString("intro");
                news.mNewsPublishedDate = jobj.getString("published_date");
                news.mNewsUpdatedDate = jobj.getString("updated_date");
                news.mNewsAuthor = jobj.getString("author");
                news.mNewsBody = jobj.getString("body");

                if (jobj.has("image_header")) {
                    news.mNewsImageHeader = jobj.getString("image_header");
                }

                this.mNewsList.add(news);
            }

            this.openNews();
        } catch (Exception e) {
            Log.d("DBG", e.getMessage());
        }
    }

    private void onVideosJSONQueryResponse(String result) {
        this.mVideosList = new ArrayList<>();

        JSONArray jarray;

        try {
            jarray = new JSONArray(result);

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jobj = jarray.getJSONObject(i);

                Video video = new Video();
                video.mVideoTitle = jobj.getString("title");
                video.mVideoDescription = jobj.getString("description");
                video.mVideoId = jobj.getString("id");

                this.mVideosList.add(video);
            }
        } catch(Exception e) {
            Log.e("DBG", e.toString());
        }
    }

    private void onDriverStandingsJSONQueryResponse(String result) {
        this.mDriversList = new ArrayList<>();

        JSONArray jarray;

        try {
            jarray = new JSONArray(result);

            for(int i=0; i < jarray.length(); i++) {
                JSONObject jobj = jarray.getJSONObject(i);

                Driver driver = new Driver();
                driver.setFirstName(jobj.getString("first_name"));
                driver.setSurname(jobj.getString("last_name"));
                driver.setConstructor(jobj.getString("constructor"));
                driver.setPoints(jobj.getString("points"));
                driver.setAbbreviatedName(jobj.getString("abbreviated_name"));

                this.mDriversList.add(driver);
            }
        } catch(Exception e) {
            Log.e("DBG", e.toString());
        }
    }

    private void onConstructorStandingsJSONQueryResponse(String result) {
        this.mConstructorsList = new ArrayList<>();

        JSONArray jarray;

        try {
            jarray = new JSONArray(result);

            for(int i=0; i < jarray.length(); i++) {
                JSONObject jobj = jarray.getJSONObject(i);

                Constructor constructor = new Constructor();
                constructor.setName(jobj.getString("name"));
                constructor.setPoints(jobj.getString("points"));

                this.mConstructorsList.add(constructor);
            }
        } catch(Exception e) {
            Log.e("DBG", e.toString());
        }
    }

    private void openNews() {
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
    }

    private void openVideos() {
        List<Fragment> videos = new ArrayList<>();

        for(Video v:this.mVideosList) {
            videos.add(new VideoFragment(this, v.mVideoTitle, v.mVideoDescription, v.mVideoId));
        }

        this.openFragmentList(videos);
    }

    private void openStandings() {
        List<Fragment> l = new ArrayList<>();
        l.add(new StandingsFragment(this.mDriversList, this.mConstructorsList));
        this.openFragmentList(l);
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

    private BottomNavigationView mBottomNavigationView;
    private List<News> mNewsList;
    private List<Video> mVideosList;
    private List<Driver> mDriversList;
    private List<Constructor> mConstructorsList;
    private ProgressBar mProgressBar;

    private static final String FORMULANEWS_JSON_QUERY_NEWS = "https://my-json-server.typicode.com/frdeazevedo/fake_rest/news";
    private static final String FORMULANEWS_JSON_QUERY_VIDEOS = "https://my-json-server.typicode.com/frdeazevedo/fake_rest/videos";
    private static final String FORMULANEWS_JSON_QUERY_DRIVER_STANDINGS = "https://my-json-server.typicode.com/frdeazevedo/fake_rest/driver_standings";
    private static final String FORMULANEWS_JSON_QUERY_CONSTRUCTOR_STANDINGS = "https://my-json-server.typicode.com/frdeazevedo/fake_rest/constructor_standings";
}
