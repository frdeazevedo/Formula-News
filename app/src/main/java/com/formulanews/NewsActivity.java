package com.formulanews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news);

        this.mWebView = findViewById(R.id.webview_content);

        String html = this.generateHtml();

        this.mWebView.loadData(html, "text/html; charset=UTF-8", "UTF-8");
    }

    private String generateHtml() {
        String html = "";

        try {
            InputStream inputStream = getResources().openRawResource(getResources().getIdentifier("news_template", "raw", this.getPackageName()));
            html = readTextFile(inputStream);
        } catch(Exception e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();

        if(intent.getStringExtra(NewsFragment.NEWSFRAGMENT_IMAGE_HEADER) != null) {
            html = html.replaceAll("_NEWS_IMAGE_HEADER_", "<img src='"+intent.getStringExtra(NewsFragment.NEWSFRAGMENT_IMAGE_HEADER)+"'/>");
        } else {
            html = html.replaceAll("_NEWS_IMAGE_HEADER_", "");
        }

        html = html.replaceAll("_NEWS_HEADER_", intent.getStringExtra(NewsFragment.NEWSFRAGMENT_HEADER));
        html = html.replaceAll("_NEWS_INTRO_", intent.getStringExtra(NewsFragment.NEWSFRAGMENT_INTRO));
        html = html.replaceAll("_NEWS_AUTHOR_", intent.getStringExtra(NewsFragment.NEWSFRAGMENT_AUTHOR));
        html = html.replaceAll("_NEWS_PUBLISHED_DATE_", intent.getStringExtra(NewsFragment.NEWSFRAGMENT_PUBLISHED_DATE));
        html = html.replaceAll("_NEWS_UPDATED_DATE_", intent.getStringExtra(NewsFragment.NEWSFRAGMENT_UPDATED_DATE));
        html = html.replaceAll("_NEWS_BODY_", intent.getStringExtra(NewsFragment.NEWSFRAGMENT_BODY));

        return html;
    }

    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    private WebView mWebView;
}
