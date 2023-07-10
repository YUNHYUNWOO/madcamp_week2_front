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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "JoinActivityLog";
    private final String URL = "https://3e00-192-249-19-234.ngrok-free.app";

    private Retrofit retrofit;
    private ApiService service;
    private Button createAccount, dup_id, dup_nickname;
    private EditText join_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);

        createAccount = (Button) findViewById(R.id.join_add_user);
        dup_id = (Button) findViewById(R.id.duplicate_id_check);
        dup_nickname = (Button) findViewById(R.id.duplicate_nickname_check);

        join_address = (EditText) findViewById(R.id.join_address);
        join_address.setFocusable(false);

        createAccount.setOnClickListener(this);
        dup_id.setOnClickListener(this);
        dup_nickname.setOnClickListener(this);
        join_address.setOnClickListener(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
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
            EditText usernameJoinEditText = findViewById(R.id.join_id_show);
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
            EditText nicknameJoinEditText = findViewById(R.id.join_nickname);
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
            EditText usernameJoinEditText = findViewById(R.id.join_id_show);
            EditText passwordJoinEditText = findViewById(R.id.join_password);
            EditText passwordCheckEditText = findViewById(R.id.join_password_correction);
            EditText nicknameJoinEditText = findViewById(R.id.join_nickname);
            EditText addressJoinEditText = findViewById(R.id.join_address);

            String username = usernameJoinEditText.getText().toString();
            String password = passwordJoinEditText.getText().toString();
            String password_correction = passwordCheckEditText.getText().toString();
            String nickname = nicknameJoinEditText.getText().toString();
            String address = addressJoinEditText.getText().toString();
            String platform = "local";
            String profile = "";

            if (username.length() == 0){
                Toast.makeText(getApplicationContext(), "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                usernameJoinEditText.requestFocus();
                return;
            }
            if (dup_id.isEnabled()){
                Toast.makeText(getApplicationContext(), "아이디 중복체크하세요", Toast.LENGTH_SHORT).show();
                usernameJoinEditText.requestFocus();
                return;
            }
            if (password.length() == 0){
                Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                passwordJoinEditText.requestFocus();
                return;
            }
            if (password_correction.length() == 0){
                Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력하세요", Toast.LENGTH_SHORT).show();
                passwordCheckEditText.requestFocus();
                return;
            }
            if (!password.equals(password_correction)){
                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                passwordJoinEditText.setText("");
                passwordCheckEditText.setText("");
                passwordJoinEditText.requestFocus();
                return;
            }
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
                userProfile.put("username", username);
                userProfile.put("password", password);
                userProfile.put("nickname", nickname);
                userProfile.put("address", address);
                userProfile.put("platform", platform);
                userProfile.put("profile", profile);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Call<Boolean> call_userProfile = service.postUserProfile(userProfile.toString());
            call_userProfile.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Log.v(TAG, "result = " + username + password);
                        Toast.makeText(getApplicationContext(), "ㅎㅇ", Toast.LENGTH_SHORT).show();
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
}