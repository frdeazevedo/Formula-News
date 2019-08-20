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
        headline.setText(this.mHeadline);

        TextView fullnews = view.findViewById(R.id.text_view_news);
        fullnews.setText(this.mFullNews);

        view.setOnClickListener(this);

        return view;
    }

    public NewsFragment(Activity context, String headline, String news) {
        this.mHeadline = headline;
        this.mFullNews = news;
        this.mContext = context;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this.mContext, NewsActivity.class);

        intent.putExtra(NEWSFRAGMENT_HEADER, this.mHeadline);
        intent.putExtra(NEWSFRAGMENT_BODY, this.mFullNews);

        startActivity(intent);
    }

    private String mFullNews;
    private String mHeadline;
    private Activity mContext;

    public static final String NEWSFRAGMENT_HEADER = "com.formulanews.NEWSFRAGMENT_HEADER";
    public static final String NEWSFRAGMENT_BODY = "com.formulanews.NEWSFRAGMENT_BODY";
}
