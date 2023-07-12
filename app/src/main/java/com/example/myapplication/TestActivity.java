package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {
    private Button button1;
    private TextView txtResult;
    private Retrofit retrofit;
    private KakaoAPI service;
    private String API_KEY = "KakaoAK " + MainActivity.RESTAPI_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        button1 = (Button) findViewById(R.id.btn);

        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.RESTAPI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(KakaoAPI.class);

        // 위치 관리자 객체 참조하기
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TestActivity.this, new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                } else {
                    // 가장최근 위치정보 가져오기
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        String provider = location.getProvider();
                        double longitude = location.getLongitude();
                        double latitude = location.getLatitude();
                        double altitude = location.getAltitude();

                        String sLongitude = (new Double(longitude)).toString();
                        String sLatitude  = (new Double(latitude)).toString();


                        retrofit2.Call<ResultSearchKeyword> call_get = service.getSearchKeyword(API_KEY,"맛집" ,sLongitude, sLatitude);
                        call_get.enqueue(new Callback<ResultSearchKeyword>() {
                            @Override
                            public void onResponse(retrofit2.Call<ResultSearchKeyword> call, Response<ResultSearchKeyword> response) {
                                if (response.isSuccessful()) {
                                    Log.d("성공?", new Gson().toJson(response.body()));

                                    Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.v("Dsfs", "error = " + String.valueOf(response.code()));
                                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<ResultSearchKeyword> call, Throwable t) {
                                Log.v("dfsfd", "Fail");
                                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                            }
                        });

                        retrofit2.Call<ResultCoord2Address> call_to = service.getSearchCoord(API_KEY, sLongitude, sLatitude);
                        call_to.enqueue(new Callback<ResultCoord2Address>() {
                            @Override
                            public void onResponse(retrofit2.Call<ResultCoord2Address> call, Response<ResultCoord2Address> response) {
                                if (response.isSuccessful()) {
                                    Log.d("성공?", new Gson().toJson(response.body()));

                                    Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.v("Dsfs", "error = " + String.valueOf(response.code()));
                                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<ResultCoord2Address> call, Throwable t) {
                                Log.v("dfsfd", "Fail");
                                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }
        });
    }

}