package org.techtown.tastealarm;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("/get/restaurantMenu/{id}")
    Call<List<Menu>> getResMenu(@Path("id") int resID);

    @Multipart
    @POST("/post/uploadImage/{userID}/res/{resName}/{content}")
    Call<Review> postReview(@Part MultipartBody.Part file, @Path("userID") int user_id,
                            @Path("resName") String resName, @Path("content") String content);

    @GET("/get/reviewList/{reviewList}")
    Call<List<Review>> getReviewList(@Path("reviewList") String reviewList);
}
