package com.formulanews;

public class Constructor {
    public Constructor(String name, String points) {
        this.mName = name;
        this.mPoints = points;
    }

    public Constructor() {
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getName() {
        return this.mName;
    }

    public void setPoints(String points) {
        this.mPoints = points;
    }

    public String getPoints() {
        return this.mPoints;
    }

    private String mName;
    private String mPoints;
}
