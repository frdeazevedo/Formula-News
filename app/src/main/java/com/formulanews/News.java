package com.formulanews;

import android.media.Image;

public class News {
    public News(Image imageHeader, String headline, String fullText) {
        this.mImageHeader = imageHeader;
        this.mHeadline = headline;
        this.mFullText = fullText;
    }

    public Image getImageHeader() {
        return this.mImageHeader;
    }

    public String getHeadline() {
        return this.mHeadline;
    }

    public String getFullText() {
        return this.mFullText;
    }

    private Image mImageHeader;
    private String mHeadline;
    private String mFullText;
}
