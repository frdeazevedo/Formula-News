package com.formulanews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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
        this.mDriversList = new ArrayList<>();
        this.mConstructorsList = new ArrayList<>();
        this.mProgressBar.setVisibility(View.VISIBLE);

        this.mNewsFragmentList = new ArrayList<>();
        this.mVideosFragmentList = new ArrayList<>();
        this.mStandingsFragmentList = new ArrayList<>();

        //Fetches the JSON of the news
        FetchDataAsyncTask fetchNewsDataAsyncTask = new FetchDataAsyncTask(this, getResources().getString(R.string.json_server_url)+MainActivity.FORMULANEWS_JSON_QUERY_NEWS);
        fetchNewsDataAsyncTask.execute(getResources().getString(R.string.json_server_url)+MainActivity.FORMULANEWS_JSON_QUERY_NEWS);

        //Fetches the JSON of the videos
        FetchDataAsyncTask fetchVideosDataAsyncTask = new FetchDataAsyncTask(this, getResources().getString(R.string.json_server_url)+MainActivity.FORMULANEWS_JSON_QUERY_VIDEOS);
        fetchVideosDataAsyncTask.execute(getResources().getString(R.string.json_server_url)+MainActivity.FORMULANEWS_JSON_QUERY_VIDEOS);

        //Fetches the JSON of the drivers standings
        FetchDataAsyncTask fetchDriverStandingsDataAsyncTask = new FetchDataAsyncTask(this, getResources().getString(R.string.json_server_url)+MainActivity.FORMULANEWS_JSON_QUERY_DRIVER_STANDINGS);
        fetchDriverStandingsDataAsyncTask.execute(getResources().getString(R.string.json_server_url)+MainActivity.FORMULANEWS_JSON_QUERY_DRIVER_STANDINGS);

        //Fetches the JSON of the drivers standings
        FetchDataAsyncTask fetchConstructorStandingsDataAsyncTask = new FetchDataAsyncTask(this, getResources().getString(R.string.json_server_url)+MainActivity.FORMULANEWS_JSON_QUERY_CONSTRUCTOR_STANDINGS);
        fetchConstructorStandingsDataAsyncTask.execute(getResources().getString(R.string.json_server_url)+MainActivity.FORMULANEWS_JSON_QUERY_CONSTRUCTOR_STANDINGS);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_news: {
                this.showFragmentList(this.mNewsFragmentList);
                this.hideFragmentList(this.mVideosFragmentList);
                this.hideFragmentList(this.mStandingsFragmentList);
                break;
            }
            case R.id.action_standings: {
                this.hideFragmentList(this.mVideosFragmentList);
                this.showFragmentList(this.mStandingsFragmentList);
                this.hideFragmentList(this.mNewsFragmentList);
                break;
            }
            case R.id.action_videos: {
                this.showFragmentList(this.mVideosFragmentList);
                this.hideFragmentList(this.mStandingsFragmentList);
                this.hideFragmentList(this.mNewsFragmentList);
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
    public void onJSONQueryResponse(String url, String result) {
        //result == null means connection error
        if(result == null) {
            this.mTextViewNoInternet.setVisibility(View.VISIBLE);

            /**
             * Load string result from cache
             */
            try {
                URI uri = new URI(url);
                String path = uri.getPath();
                String service = path.substring(path.lastIndexOf("/")+1);

                ObjectInput objectInput = new ObjectInputStream(new FileInputStream(new File(getCacheDir(), "") + service + "CacheFile.srl"));
                result = (String)objectInput.readObject();
                objectInput.close();

                Log.d("DBG", "Loading from cache: "+result);
            } catch(Exception e) {
                Log.d("DBG", "Failed to load data from cache: there is no cache file saved.");
                Log.e("DBG", e.toString());
            }
        } else {
            this.mTextViewNoInternet.setVisibility(View.GONE);

            /**
             * Save response to cache
             */
            try {
                URI uri = new URI(url);
                String path = uri.getPath();
                String service = path.substring(path.lastIndexOf("/")+1);

                ObjectOutput objectOutput = new ObjectOutputStream(new FileOutputStream(new File(getCacheDir(), "") + service + "CacheFile.srl"));
                objectOutput.writeObject(result);
                objectOutput.close();

                Log.d("DBG", "Writing to cache: "+result);
            } catch(Exception e) {
                Log.e("DBG", e.toString());
            }
        }

        this.mProgressBar.setVisibility(View.GONE);

        String requestedService = null;

        try {
            URI uri = new URI(url);
            String path = uri.getPath();
            requestedService = path.substring(path.lastIndexOf("/")+1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if(requestedService == null) {
            return;
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
        this.mTextViewNoInternet = findViewById(R.id.text_view_no_internet);
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

            this.openNewsFragments();
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

        this.openVideosFragments();
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

        this.openStandingsFragments();
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

        this.openStandingsFragments();
    }

    private void openNewsFragments() {
        if(this.mNewsList != null) {
            this.mNewsFragmentList.add(new FeaturedFragment(this, this.mNewsList.get(0)));

            int doublenews_counter = 0;

            for(int i=1; i<this.mNewsList.size(); i++) {
                if(doublenews_counter == 2) {
                    doublenews_counter = 0;

                    this.mNewsFragmentList.add(new DoubleNewsFragment(this, this.mNewsList.get(i)));
                } else {
                    this.mNewsFragmentList.add(new NewsFragment(this, this.mNewsList.get(i)));
                }

                doublenews_counter++;
            }

            this.openFragmentList(this.mNewsFragmentList);
        }
    }

    private void openVideosFragments() {
        if(this.mVideosFragmentList == null) {
            return;
        }

        for(Video v:this.mVideosList) {
            this.mVideosFragmentList.add(new VideoFragment(this, v.mVideoTitle, v.mVideoDescription, v.mVideoId));
        }

        this.openFragmentList(this.mVideosFragmentList);
    }

    private void openStandingsFragments() {
        if(this.mStandingsFragmentList == null) {
            return;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        for(Fragment f:this.mStandingsFragmentList) {
            fragmentTransaction.remove(f);
        }

        fragmentTransaction.commit();

        this.mStandingsFragmentList.add(new StandingsFragment(this.mDriversList, this.mConstructorsList));

        this.openFragmentList(this.mStandingsFragmentList);
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

    private void showFragmentList(List<Fragment> fragmentList) {
        if(fragmentList == null) {
            return;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        for(Fragment f:fragmentList) {
            transaction.show(f);
        }

        transaction.commit();
    }

    private void hideFragmentList(List<Fragment> fragmentList) {
        if(fragmentList == null) {
            return;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        for(Fragment f:fragmentList) {
            transaction.hide(f);
        }

        transaction.commit();
    }

    private void closeAllFragments() {
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        }
    }

    private BottomNavigationView mBottomNavigationView;
    private ProgressBar mProgressBar;
    private TextView mTextViewNoInternet;

    private List<News> mNewsList;
    private List<Video> mVideosList;
    private List<Driver> mDriversList;
    private List<Constructor> mConstructorsList;

    private List<Fragment> mNewsFragmentList;
    private List<Fragment> mVideosFragmentList;
    private List<Fragment> mStandingsFragmentList;

    private static final String FORMULANEWS_JSON_QUERY_NEWS = "/news";
    private static final String FORMULANEWS_JSON_QUERY_VIDEOS = "/videos";
    private static final String FORMULANEWS_JSON_QUERY_DRIVER_STANDINGS = "/driver_standings";
    private static final String FORMULANEWS_JSON_QUERY_CONSTRUCTOR_STANDINGS = "/constructor_standings";
}
