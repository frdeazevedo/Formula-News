package com.formulanews;

public class News {
    public News(String header, String intro, String publishedDate, String updatedDate, String author, String body) {
        this.mNewsHeader = header;
        this.mNewsIntro = intro;
        this.mNewsPublishedDate = publishedDate;
        this.mNewsUpdatedDate = updatedDate;
        this.mNewsAuthor = author;
        this.mNewsBody = body;
    }

    public String mNewsHeader;
    public String mNewsIntro;
    public String mNewsPublishedDate;
    public String mNewsUpdatedDate;
    public String mNewsAuthor;
    public String mNewsBody;
}
