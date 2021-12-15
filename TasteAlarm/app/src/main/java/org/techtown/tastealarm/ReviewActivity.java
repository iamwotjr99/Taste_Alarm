package org.techtown.tastealarm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewActivity extends Fragment{
    private List<Restaurant> restaurantList = new ArrayList<>();
    private List<Review> reviewList = new ArrayList<>();
    private ReviewAdapter reviewAdapter;
    private int k = 0;
    private SearchView searchView;

    FloatingActionButton floatingActionButton;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ServerURL.SERVER_URL)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    @Override
    public void onResume() {
        super.onResume();

        Log.d("onResume", "Start onResume");

        Call<List<Review>> reviewCall = retrofitAPI.getReviewList("getReviewList");
        reviewCall.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if(response.isSuccessful()) {
                    List<Review> result = response.body();
                    reviewList = new ArrayList<>();
                    for(int i = 0; i < result.size(); i++) {
                        if(reviewList.size() != result.size()) {
                            reviewList.add(new Review(result.get(i).getTitle(), result.get(i).getContent(),
                                    result.get(i).getPicture(), result.get(i).getUserName()));
                            reviewAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.d("ReviewActivity", "Fail" + t.getMessage());
            }
        });

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        searchView = view.findViewById(R.id.review_searchview);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                reviewAdapter.getFilter().filter(s);
                return false;
            }
        });

        int id = getArguments().getInt("id");
        String nickname = getArguments().getString("nickname");

        Call<List<Restaurant>> call = retrofitAPI.getResList("getResList");
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if(response.isSuccessful()) {
                    List<Restaurant> result = response.body();
                    if(restaurantList.size() != result.size()) {
                        for(int i = 0; i < result.size(); i++) {
                            restaurantList.add(new Restaurant(result.get(i).getId(),result.get(i).getName()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.d("ReviewActivity", "Fail" + t.getMessage());
            }
        });

        Call<List<Review>> reviewCall = retrofitAPI.getReviewList("getReviewList");
        reviewCall.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if(response.isSuccessful()) {
                    List<Review> result = response.body();
                    for(int i = 0; i < result.size(); i++) {
                        if(reviewList.size() != result.size()) {
                            reviewList.add(new Review(result.get(i).getTitle(), result.get(i).getContent(),
                                    result.get(i).getPicture(), result.get(i).getUserName()));
                            reviewAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.d("ReviewActivity", "Fail" + t.getMessage());
            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.review_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        reviewAdapter = new ReviewAdapter(reviewList);
        recyclerView.setAdapter(reviewAdapter);

        floatingActionButton = view.findViewById(R.id.addReview_float_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReviewAddActivity.class);
                intent.putExtra("list", (Serializable) restaurantList);
                intent.putExtra("id", id);
                intent.putExtra("nickname", nickname);
                startActivity(intent);
            }
        });

        return view;
    }
}
