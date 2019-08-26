package com.formulanews;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DoubleNewsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_double_news, container, false);

        TextView headline = (TextView)view.findViewById(R.id.text_view_double_news1);
        headline.setText(this.mNews.mNewsHeader);

        TextView fullnews = (TextView)view.findViewById(R.id.text_view_double_news2);
        fullnews.setText(this.mNews.mNewsIntro);

        return view;
    }

    public DoubleNewsFragment(Activity context, News news) {
        this.mContext = context;
        this.mNews = news;
    }

    private News mNews;
    private Activity mContext;
}
