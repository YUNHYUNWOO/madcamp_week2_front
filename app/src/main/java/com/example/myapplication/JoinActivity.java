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

    private Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);

        createAccount = (Button) findViewById(R.id.join_add_user);
        createAccount.setOnClickListener(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.join_add_user) {
            EditText usernameJoinEditText = findViewById(R.id.join_id_show);
            EditText passwordJoinEditText = findViewById(R.id.join_password);
            EditText nicknameJoinEditText = findViewById(R.id.join_nickname);
            EditText addressJoinEditText = findViewById(R.id.join_address);

            String username = usernameJoinEditText.getText().toString();
            String password = passwordJoinEditText.getText().toString();
            String nickname = nicknameJoinEditText.getText().toString();
            String address = addressJoinEditText.getText().toString();
            String platform = "local";
            String profile = "";

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
            Call<UserProfile> call_userProfile = service.postUserProfile(userProfile.toString());
            call_userProfile.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                    if (response.isSuccessful()) {
                        String username = response.body().getUsername();
                        String password = response.body().getPassword();
                        String nickname = response.body().getNickname();
                        String address = response.body().getPlace();
                        String platform = response.body().getPlatform();
                        String profile = response.body().getProfile();

                        Log.v(TAG, "result = " + username + password);
                        Toast.makeText(getApplicationContext(), username + password, Toast.LENGTH_SHORT).show();
                    } else {
                        Log.v(TAG, "error = " + String.valueOf(response.code()));
                        Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<UserProfile> call, Throwable t) {
                    Log.v(TAG, "Fail");
                    Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}