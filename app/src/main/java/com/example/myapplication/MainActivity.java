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


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "KakaoLogin";
    private View loginButton, logoutButton;
    private TextView nickName;
    private ImageView profileImage;

//    private final String URL = "http://172.10.5.180";
    private final String URL = "https://3726-192-249-19-234.ngrok-free.app";

    public Retrofit retrofit;
    public ApiService service;
    private Button btn_get, btn_post, btn_delete, btn_update, kakao_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KakaoSdk.init(this,"");

        firstInit();

        btn_post.setOnClickListener(this);

        kakao_login_button.setOnClickListener(this);


        Function2<OAuthToken,Throwable, Unit> callback =new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            // 콜백 메서드 ,
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                Log.e(TAG,"CallBack Method");
                //oAuthToken != null 이라면 로그인 성공
                if(oAuthToken!=null){
                    // 토큰이 전달된다면 로그인이 성공한 것이고 토큰이 전달되지 않으면 로그인 실패한다.
                    Log.d("Success", "!");

                }else {
                    //로그인 실패
                    Log.e(TAG, "invoke: login fail" );
                }

                return null;
            }
        };
    }

    public void firstInit() {
        btn_post = (Button) findViewById(R.id.btn_post);

        kakao_login_button = (Button) findViewById(R.id.login);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login){
            if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
                login();
            }
            else{
                accountLogin();
            }
        } else if (v.getId() == R.id.btn_post){
            EditText idEditText = findViewById(R.id.id_show);
            EditText passwordEditText = findViewById(R.id.password_show);
            String id = idEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            JSONObject userInfo = new JSONObject();
            try {
                userInfo.put("id", id);
                userInfo.put("password", password);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Call<ResponseBody> call_get = service.postFunc(userInfo.toString());
            call_get.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            Log.v(TAG, "result = " + result);
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, PostActivity.class);
                            startActivity(intent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.v(TAG, "error = " + String.valueOf(response.code()));
                        Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.v(TAG, "Fail");
                    Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void login(){
        String TAG = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
            } else {
                Log.i(TAG, "sdasdasda ");
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
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
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
                Log.i(TAG, user.toString());
                {
                    Log.i(TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: "+user.getId() +
                            "\n이메일: "+user.getKakaoAccount().getEmail());
                }
                Account user1 = user.getKakaoAccount();
                System.out.println("사용자 계정" + user1);
            }
            return null;
        });
    }
}