package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAddActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "PostAddActivityLog";
    private Retrofit retrofit;
    private ApiService service;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_new);

        search = (Button) findViewById(R.id.new_post_button);
        search.setOnClickListener(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.new_post_button) {
            EditText newPostTitleEditText = findViewById(R.id.new_post_title);
            EditText newPostContentsEditText = findViewById(R.id.new_post_contents);

            String newPostTitle = newPostTitleEditText.getText().toString();
            String newPostContents = newPostContentsEditText.getText().toString();
            String newPostWriter = MainActivity.nickname;

            Date nowDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
            String newPostUploadTime = sdf.format(nowDate);

            JSONObject postInfo = new JSONObject();
            try {
                postInfo.put("title", newPostTitle);
                postInfo.put("contents", newPostContents);
                postInfo.put("writer", newPostWriter);
                postInfo.put("uploadtime", newPostUploadTime);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            Call<Boolean> call_get = service.postPost(postInfo.toString());
            call_get.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Log.v(TAG, "성공");
                        Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.v(TAG, "error = " + String.valueOf(response.code()));
                        Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.v(TAG, "Fail");
                    Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
//
//        else if(v.getId() == R.id.btn_post) {
//            EditText idEditText = findViewById(R.id.id_show);
//            EditText passwordEditText = findViewById(R.id.password_show);
//            String id = idEditText.getText().toString();
//            String password = passwordEditText.getText().toString();
//            JSONObject userInfo = new JSONObject();
//            try {
//                userInfo.put("id", id);
//                userInfo.put("password", password);
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//            Call<ResponseBody> call_post = service.postFunc(userInfo.toString());
//            call_post.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    if (response.isSuccessful()) {
//                        try {
//                            String result = response.body().string();
//                            Log.v(TAG, "result = " + result);
//                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        Log.v(TAG, "error = " + String.valueOf(response.code()));
//                        Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Log.v(TAG, "Fail");
//                    Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        else if(v.getId() == R.id.btn_update){
//            Call<ResponseBody> call_put = service.putFunc("board", "put data");
//            call_put.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    if (response.isSuccessful()) {
//                        try {
//                            String result = response.body().string();
//                            Log.v(TAG, "result = " + result);
//                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        Log.v(TAG, "error = " + String.valueOf(response.code()));
//                        Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Log.v(TAG, "Fail");
//                    Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        else if(v.getId() ==R.id.btn_delete ){
//            Call<ResponseBody> call_delete = service.deleteFunc("board");
//            call_delete.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    if (response.isSuccessful()) {
//                        try {
//                            String result = response.body().string();
//                            Log.v(TAG, "result = " + result);
//                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        Log.v(TAG, "error = " + String.valueOf(response.code()));
//                        Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Log.v(TAG, "Fail");
//                    Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }
}