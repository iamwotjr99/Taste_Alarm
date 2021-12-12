package org.techtown.tastealarm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends Fragment {

    private ImageView ivUserProfile;
    private List<Review> reviewList = new ArrayList<>();
    private TextView tvUserID;
    private TextView tvNickname;
    private TextView tvCount;
    private String userID;
    private String nickname;
    private int id;
    private ReviewAdapter reviewAdapter;
    private int k = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        ivUserProfile = view.findViewById(R.id.user_profile);
        tvUserID = view.findViewById(R.id.user_userID);
        tvNickname = view.findViewById(R.id.user_nickname);
        tvCount = view.findViewById(R.id.review_count);

        userID = getArguments().getString("userId");
        //Log.d("userActivity", userID);
        nickname = getArguments().getString("nickname");
        id = getArguments().getInt("id");

        tvUserID.setText(userID);
        tvNickname.setText(nickname);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerURL.SERVER_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<List<Review>> call = retrofitAPI.getUserReviews(id);
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if(response.isSuccessful()) {
                    List<Review> result = response.body();
                    for(int i = k; i < result.size(); i++) {
                        if(reviewList.size() != result.size()) {
                            reviewList.add(new Review(result.get(i).getTitle(), result.get(i).getContent(),
                                    result.get(i).getPicture(), result.get(i).getUserName()));
                            k++;
                            reviewAdapter.notifyDataSetChanged();
                        }
                    }
                    tvCount.setText("리뷰 개수: " + result.size());
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.d("ReviewActivity", "Fail" + t.getMessage());
            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.user_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        reviewAdapter = new ReviewAdapter(reviewList);
        recyclerView.setAdapter(reviewAdapter);

        return view;
    }
}
