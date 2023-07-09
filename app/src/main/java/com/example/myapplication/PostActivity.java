package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MainActivityLog";
    private final String URL = "http://172.10.5.180";

    private Retrofit retrofit;
    private ApiService service;

    private Button btn_get, btn_post, btn_delete, btn_update;

    BoardMainFragment boardMainFragment;
    RecommendFragment recommendFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        boardMainFragment = new BoardMainFragment();
        recommendFragment = new RecommendFragment();
        profileFragment = new ProfileFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, boardMainFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.post_bottom_navigation);

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.board){
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, boardMainFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.recommend) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, recommendFragment).commit();
                    return true;
                }
                else if (item.getItemId() == R.id.profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, profileFragment).commit();
                    return true;
                }
                else return false;
            }
        });

        firstInit();

//        btn_get.setOnClickListener(this);
//        btn_post.setOnClickListener(this);
//        btn_delete.setOnClickListener(this);
//        btn_update.setOnClickListener(this);
    }

    /**
     * Init
     */
    public void firstInit() {
        btn_get = (Button) findViewById(R.id.btn_get);
        btn_post = (Button) findViewById(R.id.btn_post);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_update = (Button) findViewById(R.id.btn_update);

//        retrofit = new Retrofit.Builder()
//                .baseUrl(URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        service = retrofit.create(ApiService.class);
    }

    /**
     * View.OnLongClickListener override method
     */
    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.btn_get) {
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
//            Call<PostContents> call_get = service.getFunc(userInfo);
//            call_get.enqueue(new Callback<PostContents>() {
//                @Override
//                public void onResponse(Call<PostContents> call, Response<PostContents> response) {
//                    if (response.isSuccessful()) {
//                        String title = response.body().getTitle();
//                        String contents = response.body().getContents();
//                        Log.v(TAG, "result = " + title + contents);
//                        Toast.makeText(getApplicationContext(), title + contents, Toast.LENGTH_SHORT).show();
//                    } else {
//                        Log.v(TAG, "error = " + String.valueOf(response.code()));
//                        Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
//                    }
//                }
//                @Override
//                public void onFailure(Call<PostContents> call, Throwable t) {
//                    Log.v(TAG, "Fail");
//                    Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
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