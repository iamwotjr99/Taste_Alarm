package org.techtown.tastealarm;


import java.io.Serializable;

public class User implements Serializable {
    private int id;

    private String userID;

    private String nickname;

    private String userPW;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userID;
    }

    public void setUserId(String userId) {
        this.userID = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserPW() {
        return userPW;
    }

    public void setUserPW(String userPW) {
        this.userPW = userPW;
    }

    public User(String nickname, String userID, String userPW) {
        this.userID = userID;
        this.nickname = nickname;
        this.userPW = userPW;
    }
}
