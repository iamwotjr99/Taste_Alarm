package org.techtown.tastealarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private HomeActivity frag_home;
    private ReviewActivity frag_review;
    private UserActivity frag_myInfo;
    private BottomNavigationView bottomNavigationView;
    private String nickname;
    private String userID;
    private String userPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nickname = getIntent().getExtras().getString("nickname");
        userID = getIntent().getExtras().getString("userId");
        userPW = getIntent().getExtras().getString("userPW");

        Bundle bundle = new Bundle();
        bundle.putString("nickname", nickname);
        bundle.putString("userId", userID);
        bundle.putString("userPW", userPW);

        frag_home = new HomeActivity();

        frag_review = new ReviewActivity();
        frag_review.setArguments(bundle);

        frag_myInfo = new UserActivity();
        frag_myInfo.setArguments(bundle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, frag_home)
                .commit();

        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frameLayout, frag_home)
                                        .commit();
                                return true;
                            case R.id.review:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frameLayout, frag_review)
                                        .commit();
                                return true;
                            case R.id.user:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frameLayout, frag_myInfo)
                                        .commit();
                                return true;
                        }
                        return false;
                    }
                });
    }

}
