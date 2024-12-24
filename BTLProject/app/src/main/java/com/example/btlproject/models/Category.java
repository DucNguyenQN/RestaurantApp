package com.example.btlproject.models;

import com.google.gson.annotations.SerializedName;

public class Category {
    private int id;
    @SerializedName("imageUrl")
    private int  img;
    @SerializedName("name")
    private String title;

    public Category(int id, int  img, String title) {
        this.id = id;
        this.img = img;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int  getImg() {
        return img;
    }

    public void setImg(int  img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
