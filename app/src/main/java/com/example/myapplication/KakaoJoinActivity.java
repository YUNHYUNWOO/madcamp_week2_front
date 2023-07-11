package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;
import com.kakao.sdk.user.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KakaoJoinActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "JoinActivityLog";

    private Retrofit retrofit;
    private ApiService service;
    private Button createAccount, dup_id, dup_nickname;
    private EditText join_address, usernameJoinEditText, nicknameJoinEditText,passwordJoinEditText, passwordCheckEditText, addressJoinEditText ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);


        createAccount = (Button) findViewById(R.id.join_add_user);
        dup_id = (Button) findViewById(R.id.duplicate_id_check);
        dup_nickname = (Button) findViewById(R.id.duplicate_nickname_check);

        usernameJoinEditText = findViewById(R.id.join_id_show);
        nicknameJoinEditText = findViewById(R.id.join_nickname);
        passwordJoinEditText = findViewById(R.id.join_password);
        passwordCheckEditText = findViewById(R.id.join_password_correction);
        addressJoinEditText = findViewById(R.id.join_address);

        passwordJoinEditText.setEnabled(false);
        passwordCheckEditText.setEnabled(false);
        dup_id.setEnabled(false);
        usernameJoinEditText.setEnabled(false);

        join_address = (EditText) findViewById(R.id.join_address);
        join_address.setFocusable(false);

        createAccount.setOnClickListener(this);
        dup_id.setOnClickListener(this);
        dup_nickname.setOnClickListener(this);
        join_address.setOnClickListener(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }
    @Override
    public void onStart(){
        super.onStart();
        ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK){
                        String data = result.getData().getExtras().getString("data");
                        if (data != null) {
                            Log.i("test", "data:" + data);
                            join_address.setText(data);
                        }
                    }
                }
        );

        join_address.setOnClickListener(v -> {
            int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
            if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
                Log.i("주소설정페이지", "주소입력창 클릭");
                Intent intent = new Intent(getApplicationContext(), AddressApiActivity.class);
                overridePendingTransition(0, 0);
                mStartForResult.launch(intent);
            }else {
                Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.duplicate_id_check){

            String username = usernameJoinEditText.getText().toString();
            Call<Boolean> call_username = service.duplicateIdCheck(username);
            call_username.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        usernameJoinEditText.setEnabled(false);
                        dup_id.setEnabled(false);
                        Log.v(TAG, "사용가능한 아이디입니다");
                        Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다", Toast.LENGTH_SHORT).show();
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
        }else if(v.getId() == R.id.duplicate_nickname_check){

            String nickname = nicknameJoinEditText.getText().toString();
            Call<Boolean> call_username = service.duplicateNicknameCheck(nickname);
            call_username.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        nicknameJoinEditText.setEnabled(false);
                        dup_nickname.setEnabled(false);
                        Log.v(TAG, "사용가능한 별명입니다");
                        Toast.makeText(getApplicationContext(), "사용가능한 별명입니다", Toast.LENGTH_SHORT).show();
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
        } else if(v.getId() == R.id.join_add_user) {



            String nickname = nicknameJoinEditText.getText().toString();
            String address = addressJoinEditText.getText().toString();
            String platform = "kakao";
            String profile = "";


            if (nickname.length() == 0){
                Toast.makeText(getApplicationContext(), "별명을 입력하세요", Toast.LENGTH_SHORT).show();
                nicknameJoinEditText.requestFocus();
                return;
            }
            if (dup_nickname.isEnabled()){
                Toast.makeText(getApplicationContext(), "별명 중복체크하세요", Toast.LENGTH_SHORT).show();
                nicknameJoinEditText.requestFocus();
                return;
            }
            if (address.length() == 0){
                Toast.makeText(getApplicationContext(), "주소를 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }

            JSONObject userProfile = new JSONObject();
            try {
                userProfile.put("username", getIntent().getStringExtra("username"));
                userProfile.put("password", "");
                userProfile.put("nickname", nickname);
                userProfile.put("place", address);
                userProfile.put("platform", platform);
                userProfile.put("profile", profile);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Log.d("User Profile", userProfile.toString());
            Call<ResponseBody> call_userProfile = service.kakaoJoin(userProfile.toString());
            call_userProfile.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {

                        Toast.makeText(getApplicationContext(), "ㅎㅇ", Toast.LENGTH_SHORT).show();


                        MainActivity.nickname = response.body().toString();
                        Intent intent = new Intent(KakaoJoinActivity.this, PostActivity.class);
                        startActivity(intent);
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
}