package org.techtown.tastealarm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {
    @GET("/get/join/checkid/{userId}")
    Call<User> getCheckUserId(@Path("userId") String userId);

    @POST("/post/join/{nickname}/{userId}/{userPW}")
    Call<User> postJoinUser(@Path("nickname") String nickname,
                            @Path("userId") String userId,
                            @Path("userPW") String userPW);

    @GET("/get/login/{userId}/{userPW}")
    Call<User> getUserInfo(@Path("userId") String userId,
                           @Path("userPW") String userPW);

    @GET("/get/restaurantList/{resList}")
    Call<List<Restaurant>> getResList(@Path("resList") String resList);
}
