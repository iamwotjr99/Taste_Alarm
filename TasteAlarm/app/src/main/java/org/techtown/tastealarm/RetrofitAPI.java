package org.techtown.tastealarm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {
    @GET("/get/join/checkid/{userId}")
    Call<User> getCheckUserId(@Path("userId") String userId);

    @POST("/post/join")
    Call<User> postJoinUser(@Body User user);
}
