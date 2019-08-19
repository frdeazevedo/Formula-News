package com.formulanews;

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
        headline.setText(this.mHeadline1);

        TextView fullnews = (TextView)view.findViewById(R.id.text_view_double_news2);
        fullnews.setText(this.mHeadline2);

        return view;
    }

    public DoubleNewsFragment(String headline1, String headline2) {
        this.mHeadline1 = headline1;
        this.mHeadline2 = headline2;
    }

    private String mHeadline1;
    private String mHeadline2;
}
