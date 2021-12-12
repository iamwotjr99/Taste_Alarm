package org.techtown.tastealarm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewAddActivity extends AppCompatActivity {
    private final int GET_GALLERY_IMAGE = 200;
    private Bitmap bitmap;
    private Uri imageUri;
    private EditText etAddReviewContent;
    private Button btnAddPhoto;
    private Button btnAddReview;
    private ImageView ivSelectedImg;
    private AutoCompleteTextView autoCompleteTextView;
    private List<String> stringList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreview);

        int id = getIntent().getIntExtra("id", 0);
        String nickname = getIntent().getStringExtra("nickname");

        etAddReviewContent = findViewById(R.id.addReview_content);
        btnAddPhoto = findViewById(R.id.addPhoto_btn);
        btnAddReview = findViewById(R.id.addReview_btn);
        autoCompleteTextView = findViewById(R.id.addReview_resName);
        ivSelectedImg = findViewById(R.id.addReview_image);

        ArrayList<Restaurant> list = (ArrayList<Restaurant>) getIntent().getSerializableExtra("list");

        stringList = new ArrayList<String>();

        for(int i = 0; i <list.size(); i++) {
            stringList.add(list.get(i).getName());
        }

        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                stringList));

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerURL.SERVER_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resName = autoCompleteTextView.getText().toString();
                String reviewContent = etAddReviewContent.getText().toString();

                if(imageUri != null) {
                    File file = imageToFile(imageUri);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("jpeg/*"), file);

                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", resName+id+".jpg", requestFile);

                    Call<Review> call = retrofitAPI.postImgReview(body, id, resName, reviewContent, nickname);
                    call.enqueue(new Callback<Review>() {
                        @Override
                        public void onResponse(Call<Review> call, Response<Review> response) {
                            Review result = response.body();
                            Log.d("ReviewAddRes", String.valueOf(result));
                        }

                        @Override
                        public void onFailure(Call<Review> call, Throwable t) {
                            Log.d("ReviewAddFai;", t.getMessage());
                        }
                    });
                } else {
                    Call<Review> call = retrofitAPI.postReview(id, resName, reviewContent, nickname);
                    call.enqueue(new Callback<Review>() {
                        @Override
                        public void onResponse(Call<Review> call, Response<Review> response) {
                            Review result = response.body();
                            Log.d("ReviewAddRes", String.valueOf(result));
                        }

                        @Override
                        public void onFailure(Call<Review> call, Throwable t) {
                            Log.d("ReviewAddFai;", t.getMessage());
                        }
                    });
                }

                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            ivSelectedImg.setImageURI(imageUri);

        } else if(resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "사진 선택을 취소하였습니다.", Toast.LENGTH_LONG).show();
        }
    }

    public File imageToFile(Uri imageUri) {
        File filesDir = getApplication().getFilesDir();
        File file = new File(filesDir, filesDir.getName() + ".jpg");
        Log.d("Path", String.valueOf(file));

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), imageUri));
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60 ,bos);
        byte[] bitmapData = bos.toByteArray();

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
