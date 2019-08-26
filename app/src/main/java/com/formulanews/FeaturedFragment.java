package com.formulanews;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FeaturedFragment extends NewsFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_featured, container, false);

        TextView headline = view.findViewById(R.id.text_view_featured);
        headline.setText(this.mNews.mNewsHeader);

        TextView description = view.findViewById(R.id.text_view_description_featured);
        description.setText(this.mNews.mNewsIntro);

        if(this.mNews.mNewsImageHeader != null) {
            ImageView newsimage = view.findViewById(R.id.image_view_featured);
            DownloadImageTask dlImageTask = new DownloadImageTask(newsimage);
            dlImageTask.execute(this.mNews.mNewsImageHeader);
        }

        view.setOnClickListener(this);

        return view;
    }

    public FeaturedFragment(Activity context, News news) {
        super(context, news);
    }
}
