package com.formulanews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NewsFragment extends    Fragment
                          implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        TextView headline = view.findViewById(R.id.text_view_header);
        headline.setText(this.mNews.mNewsHeader);

        TextView fullnews = view.findViewById(R.id.text_view_news);
        fullnews.setText(this.mNews.mNewsIntro);

        view.setOnClickListener(this);

        return view;
    }

    public NewsFragment() {}

    public NewsFragment(Activity context, News news) {
        this.mContext = context;
        this.mNews = news;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this.mContext, NewsActivity.class);

        intent.putExtra(NEWSFRAGMENT_HEADER, this.mNews.mNewsHeader);
        intent.putExtra(NEWSFRAGMENT_BODY, this.mNews.mNewsBody);
        intent.putExtra(NEWSFRAGMENT_ID, this.mNews.mNewsId);
        intent.putExtra(NEWSFRAGMENT_INTRO, this.mNews.mNewsIntro);
        intent.putExtra(NEWSFRAGMENT_AUTHOR, this.mNews.mNewsAuthor);
        intent.putExtra(NEWSFRAGMENT_PUBLISHED_DATE, this.mNews.mNewsPublishedDate);
        intent.putExtra(NEWSFRAGMENT_UPDATED_DATE, this.mNews.mNewsUpdatedDate);

        startActivity(intent);
    }

    protected Activity mContext;
    protected News mNews;

    public static final String NEWSFRAGMENT_HEADER = "com.formulanews.NEWSFRAGMENT_HEADER";
    public static final String NEWSFRAGMENT_BODY = "com.formulanews.NEWSFRAGMENT_BODY";
    public static final String NEWSFRAGMENT_INTRO = "com.formulanews.NEWSFRAGMENT_INTRO";
    public static final String NEWSFRAGMENT_AUTHOR = "com.formulanews.NEWSFRAGMENT_AUTHOR";
    public static final String NEWSFRAGMENT_PUBLISHED_DATE = "com.formulanews.NEWSFRAGMENT_PUBLISHED_DATE";
    public static final String NEWSFRAGMENT_UPDATED_DATE = "com.formulanews.NEWSFRAGMENT_UPDATE_DATE";
    public static final String NEWSFRAGMENT_ID = "com.formulanews.NEWSFRAGMENT_ID";
}
