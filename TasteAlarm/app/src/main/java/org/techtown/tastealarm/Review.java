package org.techtown.tastealarm;

import java.io.Serializable;

public class Review implements Serializable {
    private int user_id;
    private String title; // 가게 이름
    private String content;
    private String picture;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Review(int user_id, String title, String content) {
        this.user_id = user_id;
        this.title = title;
        this.content = content;
    }

    public Review(String title, String content, String picture) {
        this.title = title;
        this.content = content;
        this.picture = picture;
    }
}
