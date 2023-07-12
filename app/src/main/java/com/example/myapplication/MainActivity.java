package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "KakaoLogin";
    private View loginButton, logoutButton;
    private TextView nickName;
    private ImageView profileImage;
    public static String URL = "https://54ee-192-249-19-234.ngrok-free.app";
    public static String RESTAPI_URL = "https://dapi.kakao.com/";
    public static String NATIVE_KEY = "";
    public static String RESTAPI_KEY = "";

    public static String nickname;

    public Retrofit retrofit;
    public ApiService service;
    private Button btn_join, btn_post, btn_delete, btn_update, kakao_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KakaoSdk.init(this,NATIVE_KEY);

        firstInit();

        btn_post.setOnClickListener(this);
        btn_join.setOnClickListener(this);
        kakao_login_button.setOnClickListener(this);

    }

    public void firstInit() {
        btn_post = (Button) findViewById(R.id.btn_post);
        btn_join = (Button) findViewById(R.id.btn_join);

        kakao_login_button = (Button) findViewById(R.id.login);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login){
            Log.d("fdsf0", "SDfsdf");
            if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
                login();
            }
            else{
                accountLogin();
            }
        } else if (v.getId() == R.id.btn_post){
            EditText idEditText = findViewById(R.id.id_show);
            EditText passwordEditText = findViewById(R.id.password_show);
            String username = idEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            JSONObject userInfo = new JSONObject();
            try {
                userInfo.put("username", username);
                userInfo.put("password", password);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Call<String> call_get = service.postFunc(userInfo.toString());
            call_get.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        String result = response.body().toString();
                        nickname = response.body().toString();
                        Log.v(TAG, "result = " + nickname);
                        Toast.makeText(getApplicationContext(), nickname + "님, 환영합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, PostActivity.class);
                        startActivity(intent);
                    } else {
                        Log.v(TAG, "error = " + String.valueOf(response.code()));
                        Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.v(TAG, "Fail");
                    Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (v.getId() == R.id.btn_join) {
            Intent intent = new Intent(MainActivity.this, JoinActivity.class);
            startActivity(intent);
        }
    }


    public void login(){
        String TAG = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공" + oAuthToken.getAccessToken());
                getUserInfo();
            }
            return null;
        });
    }

    public void accountLogin(){
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공" + oAuthToken.getAccessToken());
                getUserInfo();
            }
            return null;
        });
    }

    public void getUserInfo(){
        String TAG = "getUserInfo()";
        UserApiClient.getInstance().me((user, meError) -> {
            if (meError != null) {
                Log.e(TAG, "사용자 정보 요청 실패", meError);
            } else {
                System.out.println("로그인 완료");

                Call<String> call_get = service.kakaoUserCheck(user.getId().toString());
                call_get.enqueue(new Callback<String>(){

                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {

                        Toast.makeText(getApplicationContext(), "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                        nickname = response.body().toString();

                        Intent intent = new Intent(MainActivity.this, PostActivity.class);
                        startActivity(intent);
                    } else {

                        Toast.makeText(getApplicationContext(), "회원가입을 진행합니다.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, KakaoJoinActivity.class);
                        intent.putExtra("username", user.getId().toString());
                        startActivity(intent);

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.v(TAG, "Fail");
                    Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                }
            });
            }
            return null;
        });
    }
}