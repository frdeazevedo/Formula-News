package com.formulanews;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.io.IOException;
import java.io.InputStream;

public class VideoFragment extends Fragment
                           implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        TextView headline = view.findViewById(R.id.text_view_video_headline);
        headline.setText(this.mVideoHeadline);

        TextView description = view.findViewById(R.id.text_view_video_description);
        description.setText(this.mVideoDescription);

        ImageView thumbnail = view.findViewById(R.id.image_view_video_thumbnail);
        VideoFragment.DownloadImageTask dlImageTask = new VideoFragment.DownloadImageTask(thumbnail);
        dlImageTask.execute("http://img.youtube.com/vi/"+this.mVideoId+ "/0.jpg");

        view.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(this.mContext, "AIzaSyA2TRuQcNKeeQalMCLyBBFggr4uem6V2JI", this.mVideoId);
        startActivity(intent);
    }

    public VideoFragment(Activity context, String headline, String description, String videoId) {
        this.mContext = context;
        this.mVideoHeadline = headline;
        this.mVideoDescription = description;
        this.mVideoId = videoId;
    }

    protected class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imgview) {
            this.imageView = imgview;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap image = null;

            try {
                InputStream inputstream = new java.net.URL(urldisplay).openStream();
                image = BitmapFactory.decodeStream(inputstream);
            } catch(IOException ioe) {
                Log.e("DBG", ioe.toString());
            }

            return image;
        }

        protected void onPostExecute(Bitmap result) {
            if(result != null) {
                this.imageView.setImageBitmap(result);
            }
        }
    }

    private String mVideoHeadline;
    private String mVideoDescription;
    private String mVideoId;
    private Activity mContext;
}
