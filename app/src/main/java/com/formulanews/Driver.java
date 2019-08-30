package com.formulanews;

public class Driver {
    public Driver() {
        this.mAbbreviatedName = null;
        this.mFirstName = null;
        this.mSurname = null;
        this.mCountry = null;
        this.mState = null;
        this.mConstructor = null;
        this.mPoints = null;
    }

    public Driver(String firstName, String surname, String country) {
        this.mFirstName = firstName;
        this.mSurname = surname;
        this.mCountry = country;
    }

    public void setAbbreviatedName(String abvName) {
        this.mAbbreviatedName = abvName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public void setSurname(String surname) {
        this.mSurname = surname;
    }

    public void setCountry(String country) {
        this.mCountry = country;
    }

    public void setState(String state) {
        this.mState = state;
    }

    public void setConstructor(String constructor) {
        this.mConstructor = constructor;
    }

    public void setPoints(String points) {
        this.mPoints = points;
    }

    public String getAbbreviatedName() {
        return this.mAbbreviatedName;
    }

    public String getFirstName() {
        return this.mFirstName;
    }

    public String getSurname() {
        return this.mSurname;
    }

    public String getCountry() {
        return this.mCountry;
    }

    public String getState() {
        return this.mState;
    }

    public String getmConstructor() {
        return this.mConstructor;
    }

    public String getPoints() {
        return this.mPoints;
    }

    private String mAbbreviatedName;
    private String mFirstName;
    private String mSurname;
    private String mCountry;
    private String mState;
    private String mConstructor;
    private String mPoints;
}
