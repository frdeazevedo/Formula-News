package com.formulanews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        TextView headline = (TextView)view.findViewById(R.id.text_view_header);
        headline.setText("Aqui vai o headline da notícia");

        TextView fullnews = (TextView)view.findViewById(R.id.text_view_news);
        fullnews.setText(this.mFullNews);

        return view;
    }

    public NewsFragment(String news) {
        this.mFullNews = news;
    }

    /*public static NewsFragment newInstance() {
        return new NewsFragment();
    }*/

    public void setFullNews(String s) {

    }

    private String mFullNews;

}
