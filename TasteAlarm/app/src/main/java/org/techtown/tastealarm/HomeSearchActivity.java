package org.techtown.tastealarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeSearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private SearchAdapter searchAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchrestaurant);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        searchView = findViewById(R.id.restaurant_searchview);

        Intent search_intent = getIntent();
        ArrayList<Restaurant> list = (ArrayList<Restaurant>) search_intent.getSerializableExtra("list");

        RecyclerView recyclerView = findViewById(R.id.search_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(list);
        recyclerView.setAdapter(searchAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchAdapter.getFilter().filter(s);
                return false;
            }
        });

        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int resID = list.get(position).getId();
                String resName = list.get(position).getName();
                String resAddress = list.get(position).getAddress();
                String resPicture = list.get(position).getPicture();
                String resCategory = list.get(position).getCategory();
                Intent intent = new Intent(v.getContext(), RestaurantActivity.class);
                intent.putExtra("resID", resID);
                intent.putExtra("resName", resName);
                intent.putExtra("resAddress", resAddress);
                intent.putExtra("resPicture", resPicture);
                intent.putExtra("resCategory", resCategory);
                startActivity(intent);
            }
        });

    }
}
