package org.techtown.tastealarm;


public class User {
    private int id;

    private String userId;

    private String nickname;

    private String userPW;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public User(String nickname, String userId, String userPW) {
        this.userId = userId;
        this.nickname = nickname;
        this.userPW = userPW;
    }
}
