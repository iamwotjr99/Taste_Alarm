package org.techtown.tastealarm;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userId")
    private String userId;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("userPW")
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

    public User(String nickname, String userId, String userPW) {
        this.userId = userId;
        this.nickname = nickname;
        this.userPW = userPW;
    }
}
