package org.techtown.tastealarm;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestaurantActivity extends AppCompatActivity {
    private ImageView ivResPicture;
    private TextView tvResName;
    private TextView tvResAddress;
    private TextView tvResCategory;
    private ImageButton btnBack;
    private MenuAdapter menuAdapter;
    private List<Menu> mList = new ArrayList<Menu>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticerestaurant);

        tvResName = findViewById(R.id.notice_restaurant_name);
        tvResAddress = findViewById(R.id.notice_restaurant_address);
        tvResCategory = findViewById(R.id.notice_restaurant_category);
        ivResPicture = findViewById(R.id.notice_restaurant_imageview);
        btnBack = findViewById(R.id.notice_restaurant_backBtn);

        int resID = getIntent().getIntExtra("resID",1);
        String resName = getIntent().getStringExtra("resName");
        String resAddress = getIntent().getStringExtra("resAddress");
        String resCategory = getIntent().getStringExtra("resCategory");
        String resPicture = getIntent().getStringExtra("resPicture");

        tvResName.setText(resName);
        tvResAddress.setText(resAddress);
        tvResCategory.setText(resCategory);
        Glide.with(this)
                .load(resPicture)
                .into(ivResPicture);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerURL.SERVER_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<List<Menu>> call = retrofitAPI.getResMenu(resID);

        call.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                if(response.isSuccessful()) {
                    List<Menu> result = response.body();
                    if(mList.size() != result.size()) {
                        for(int i = 0; i < result.size(); i++) {
                            mList.add(new Menu(result.get(i).getName(), result.get(i).getMenu(),
                                    result.get(i).getPrice(), result.get(i).getPicture()));
                            menuAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.notice_restaurant_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        menuAdapter = new MenuAdapter(mList);
        recyclerView.setAdapter(menuAdapter);
    }
}
