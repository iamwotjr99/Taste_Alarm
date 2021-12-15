package org.techtown.tastealarm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        MoveMin(1);     //1초후 이동

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
    private void MoveMin(int sec) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent move_login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(move_login);
            }
        }, 1000 * sec);
    }
}