package com.formulanews;

public class Constructor {
    public Constructor(String name) {
        this.mName = name;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getName() {
        return this.mName;
    }

    private String mName;
}
