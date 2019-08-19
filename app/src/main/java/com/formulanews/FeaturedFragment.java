package com.formulanews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FeaturedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_featured, container, false);

        TextView headline = (TextView)view.findViewById(R.id.text_view_featured);
        headline.setText(this.mHeadline);

        TextView description = (TextView)view.findViewById(R.id.text_view_description_featured);
        description.setText(this.mDescription);

        return view;
    }

    public FeaturedFragment(String headline, String description) {
        this.mHeadline = headline;
        this.mDescription = description;
    }

    private String mHeadline;
    private String mDescription;
}
