package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

        Toolbar mToolbar = findViewById(R.id.add_new_post_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
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
                postInfo.put("uploadTime", newPostUploadTime);
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
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}