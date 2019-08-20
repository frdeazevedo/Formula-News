package com.formulanews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news);

        this.mNewsHeader = (TextView)findViewById(R.id.text_view_news_header);
        this.mNewsDescription = (TextView)findViewById(R.id.text_view_news_header);
        this.mNewsBody = (TextView)findViewById(R.id.text_view_news_body);

        Intent intent = getIntent();
        this.mNewsHeader.setText(intent.getStringExtra(NewsFragment.NEWSFRAGMENT_HEADER));
        this.mNewsBody.setText(intent.getStringExtra(NewsFragment.NEWSFRAGMENT_BODY));
    }

    private TextView mNewsHeader;
    private TextView mNewsDescription;
    private TextView mNewsBody;
}
