package com.formulanews;

public class Video {
    public Video() {}

    public Video(String videoId, String title, String description) {
        this.mVideoId = videoId;
        this.mVideoTitle = title;
        this.mVideoDescription = description;
    }

    public String mVideoId;
    public String mVideoTitle;
    public String mVideoDescription;
}
