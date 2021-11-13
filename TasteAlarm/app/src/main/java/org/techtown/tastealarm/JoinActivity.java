package org.techtown.tastealarm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinActivity extends AppCompatActivity {

    private EditText etJoinNickname;
    private EditText etJoinID;
    private Button btnJoinIDCheck;
    private EditText etJoinPW;
    private EditText etJoinPWCheck;
    private Button btnJoin;
    private TextView tvCheckText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerURL.SERVER_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        etJoinNickname = findViewById(R.id.join_nickname);
        etJoinID = findViewById(R.id.join_id);
        etJoinPW = findViewById(R.id.join_password);
        etJoinPWCheck = findViewById(R.id.join_password_sign);
        tvCheckText = findViewById(R.id.join_sign_text);
        btnJoinIDCheck = findViewById(R.id.join_check);
        btnJoin = findViewById(R.id.join_button);


        // String userPWCheck = etJoinPWCheck.getText().toString();


        btnJoinIDCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = etJoinID.getText().toString();
                if(etJoinID.length() == 0) {
                    tvCheckText.setText("아이디를 입력해주세요.");
                    tvCheckText.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    Call<User> call = retrofitAPI.getCheckUserId(userId);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful()) {
                                User getResult = response.body();
                                if(getResult == null) {
                                    tvCheckText.setText("사용할 수 있는 아이디입니다.");
                                    tvCheckText.setTextColor(Color.parseColor("#00FF00"));
                                    Log.d("JoinActivity", "사용할 수 있는 아이디 성공");
                                } else {
                                    tvCheckText.setText("사용할 수 없는 아이디입니다.");
                                    tvCheckText.setTextColor(Color.parseColor("#FF0000"));
                                    Log.d("JoinActivity", "사용할 수 없는 아이디 성공");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("JoinActivity", "IDCheckFail: " + t.getMessage());
                        }
                    });
                }

            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nickname = etJoinNickname.getText().toString();
                String userId = etJoinID.getText().toString();
                String userPW= etJoinPW.getText().toString();

                Call<User> call = retrofitAPI.postJoinUser(nickname, userId, userPW);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()) {
                            Log.d("PostCall","ok");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("JoinActivity", "JoinFail: " + t.getMessage());
                    }
                });
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
