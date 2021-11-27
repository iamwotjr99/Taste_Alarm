package org.techtown.tastealarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginID;
    private EditText etLoginPW;
    private Button btnLogin;
    private TextView tvJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginID = findViewById(R.id.login_id);
        etLoginPW = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_btn);
        tvJoin = findViewById(R.id.join);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerURL.SERVER_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = etLoginID.getText().toString();
                String userPW = etLoginPW.getText().toString();
                Call<User> getCall = retrofitAPI.getUserInfo(userId, userPW);
                getCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()) {
                            User result = response.body();
                            if(result != null) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("id", result.getId());
                                intent.putExtra("userId", result.getUserId());
                                intent.putExtra("userPW", result.getUserPW());
                                intent.putExtra("nickname", result.getNickname());
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setTitle("로그인 실패").setMessage("아이디 혹은 비빌번호를 다시 입력해주세요.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        } else {
                            Log.d("getCall", "서버 연결 실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("JoinActivity", "IDCheckFail: " + t.getMessage());
                    }
                });
            }
        });

        tvJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });
    }
}
