package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VoteAddActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView titleView;
    private TextView addressView;
    private Button curAddressChooseBtn;
    private Button homeAddressChoosBtn;
    private TextView categoryView;
    private Button categoryChooseBtn;
    private TextView contentView;
    private Button votePostingBtn;

    private Retrofit myRetrofit;
    private Retrofit kakaoRetrofit;
    private ApiService myService;
    private KakaoAPI kakaoService;

    private String keyword;
    String sLongitude;
    String sLatitude;
    private String API_KEY = "KakaoAK " + MainActivity.RESTAPI_KEY;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_new);

        Init();

    }

    public void Init(){
        titleView = findViewById(R.id.vote_title);
        addressView = findViewById(R.id.vote_address);
        curAddressChooseBtn = findViewById(R.id.vote_current_ad);
        homeAddressChoosBtn = findViewById(R.id.vote_home_ad);
        categoryView = findViewById(R.id.vote_category);
        categoryChooseBtn = findViewById(R.id.vote_category_choose);
        contentView = findViewById(R.id.content_et);
        votePostingBtn = findViewById(R.id.vote_post);



        myRetrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        kakaoRetrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.RESTAPI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        titleView.setOnClickListener(this);
        addressView.setOnClickListener(this);
        curAddressChooseBtn.setOnClickListener(this);
        homeAddressChoosBtn.setOnClickListener(this);
        categoryView.setOnClickListener(this);
        categoryChooseBtn.setOnClickListener(this);
        contentView.setOnClickListener(this);
        votePostingBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.vote_current_ad){
            final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(VoteAddActivity.this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            } else {
                // 가장최근 위치정보 가져오기
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    String provider = location.getProvider();
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    double altitude = location.getAltitude();

                    sLongitude = (new Double(longitude)).toString();
                    sLatitude  = (new Double(latitude)).toString();
                    Log.d("좌표", sLongitude + " " + sLatitude);
                    kakaoService = kakaoRetrofit.create(KakaoAPI.class);



                    retrofit2.Call<ResultCoord2Address> call_to = kakaoService.getSearchCoord(API_KEY,sLongitude, sLatitude);
                    call_to.enqueue(new Callback<ResultCoord2Address>() {
                        @Override
                        public void onResponse(retrofit2.Call<ResultCoord2Address> call, Response<ResultCoord2Address> response) {
                            if (response.isSuccessful()) {
                                Log.d("성공?", new Gson().toJson(response.body()));
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

                    retrofit2.Call<ResultCoord2Address> call_post = kakaoService.getSearchCoord(API_KEY,sLongitude, sLatitude);
                    call_post.enqueue(new Callback<ResultCoord2Address>() {
                        @Override
                        public void onResponse(retrofit2.Call<ResultCoord2Address> call, Response<ResultCoord2Address> response) {
                            if (response.isSuccessful()) {

                                String addressName = response.body().documents.get(0).address.address_name;

                                Log.d("주소 이름 잘 올라가나?", addressName);
                                addressView.setText(addressName);
                            } else {
                                Log.v("Dsfs", "error = " + String.valueOf(response.code()));
                                Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResultCoord2Address> call, Throwable t) {
                            Log.d("dfsfd", "Fail");
                            Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }
        else if(view.getId() == R.id.vote_home_ad){

        }
        else if(view.getId() == R.id.vote_category_choose){

        }
        else if(view.getId() == R.id.vote_post){
            String title = titleView.getText().toString();
            String address = addressView.getText().toString();
            String category = categoryView.getText().toString();
            String contents = contentView.getText().toString();

            Date nowDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
            String uploadTime = sdf.format(nowDate);
            Log.v("TAG", uploadTime);

            VoteContents newVote = new VoteContents(0, title, contents, "dfsd", address, 0, MainActivity.nickname, "", uploadTime, 0);

            myService = myRetrofit.create(ApiService.class);
            retrofit2.Call<Integer> call_post = myService.makeNewVote(newVote);
            call_post.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(retrofit2.Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        int idx = response.body();
                        Integer i = idx;
                        Log.d("제발..", i.toString());
                        retrofit2.Call<ResultSearchKeyword> call_get = kakaoService.getSearchKeyword(API_KEY,address , null, null);
                        call_get.enqueue(new Callback<ResultSearchKeyword>() {
                            @Override
                            public void onResponse(retrofit2.Call<ResultSearchKeyword> call, Response<ResultSearchKeyword> response) {
                                if (response.isSuccessful()) {
                                    String x = response.body().documents.get(0).x;
                                    String y = response.body().documents.get(0).y;

                                    retrofit2.Call<ResultSearchKeyword> call_get = kakaoService.getSearchKeyword(API_KEY, "맛집", x, y);
                                    call_get.enqueue(new Callback<ResultSearchKeyword>() {
                                        @Override
                                        public void onResponse(retrofit2.Call<ResultSearchKeyword> call, Response<ResultSearchKeyword> response) {
                                            if (response.isSuccessful()) {
                                                int id = idx;

                                                retrofit2.Call<Integer> call_post = myService.makeNewVoteList(new Postdata(id, response.body()));
                                                call_post.enqueue(new Callback<Integer>(){
                                                    @Override
                                                    public void onResponse(Call<Integer> call, Response<Integer> response) {

//                                                        Intent intent = new Intent(VoteAddActivity.this, VoteInfoWritter.class);
//                                                        Integer a = id;
//                                                        Log.d("웨 안뒈?", a.toString());
//                                                        intent.putExtra("index", id);
//                                                        startActivity(intent);
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Integer> call, Throwable t) {

                                                    }
                                                });
                                                Toast.makeText(getApplicationContext(), "성공적으로 업로드 되었습니다.", Toast.LENGTH_SHORT).show();
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
                    } else {
                        Log.v("Dsfs", "error = " + String.valueOf(response.code()));
                        Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.v("dfsfd", "Fail");
                    Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
