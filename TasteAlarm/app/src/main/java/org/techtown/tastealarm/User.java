package org.techtown.tastealarm;

public class User {
    private String userId;
    private String nickname;
    private String userPW;

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

    public User(String userId, String nickname, String userPW) {
        this.userId = userId;
        this.nickname = nickname;
        this.userPW = userPW;
    }
}
