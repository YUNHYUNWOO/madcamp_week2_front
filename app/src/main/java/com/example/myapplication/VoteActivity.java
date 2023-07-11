package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VoteActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "VoteActivityLog";
    private final String URL = "https://dapi.kakao.com/";
    private String API_KEY = "KakaoAK " + MainActivity.RESTAPIKEY;

    private Retrofit retrofit;
    private KakaoAPI service;

    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_layout);

        search = (Button) findViewById(R.id.search_btn);
        search.setOnClickListener(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(KakaoAPI.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.search_btn) {
            EditText searchEditText = findViewById(R.id.dummy_search);
            String searchKeyword = searchEditText.getText().toString();

            TextView resultTextView = findViewById(R.id.dummy_text);

            Call<ResultSearchKeyword> call_get = service.getSearchKeyword(API_KEY, searchKeyword);
            call_get.enqueue(new Callback<ResultSearchKeyword>() {
                @Override
                public void onResponse(Call<ResultSearchKeyword> call, Response<ResultSearchKeyword> response) {
                    if (response.isSuccessful()) {
                        resultTextView.setText(response.body().documents.toString());
                        Log.v(TAG, "성공");
                        Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.v(TAG, "error = " + String.valueOf(response.code()));
                        Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResultSearchKeyword> call, Throwable t) {
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