package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VoteInfoNotSelectActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<VoteListItem> voteListArrayList = new ArrayList<>(0);
    private Retrofit retrofit;
    private ApiService service;
    private LinearLayout vote_layout;
    private RadioGroup radioGroup;
    private int vote_id;
    private Button reg_vote_comment_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_select_layout);

        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);

        Toolbar mToolbar = findViewById(R.id.vote_select_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        TextView vote_category = (TextView) findViewById(R.id.vote_select_category_select) ;
        TextView vote_title = (TextView) findViewById(R.id.vote_select_title);
        TextView vote_writer = (TextView) findViewById(R.id.vote_select_writer);
        TextView vote_content = (TextView) findViewById(R.id.vote_select_content);
        TextView vote_date = (TextView) findViewById(R.id.vote_select_date);

        TextView vote_select_place = (TextView) findViewById(R.id.vote_select_place);
        vote_select_place.setOnClickListener(this);
        Button reg_vote_comment_button = (Button) findViewById(R.id.reg_vote_comment_button);
        reg_vote_comment_button.setOnClickListener(this);

        vote_layout = findViewById(R.id.vote_select_comment_layout);

        Intent intent = getIntent();

        vote_id = intent.getIntExtra("id", 0);
        String write_time = intent.getExtras().getString("write_time");
        String hits = intent.getExtras().getString("hits");
        String title = intent.getExtras().getString("title");
        String writer = intent.getExtras().getString("writer");
        String contents = intent.getExtras().getString("contents");
        String category = intent.getExtras().getString("category");

        vote_category.setText(category);
        vote_title.setText(title);
        vote_date.setText(write_time);
        vote_content.setText(contents);
        vote_writer.setText(writer);

        initData();
    }
    public void initData(){
        Call<ArrayList<VoteListItem>> call_get = service.readVotes(vote_id);
        call_get.enqueue(new Callback<ArrayList<VoteListItem>>() {
            @Override
            public void onResponse(Call<ArrayList<VoteListItem>> call, Response<ArrayList<VoteListItem>> response) {
                if (response.isSuccessful()) {
                    ArrayList<VoteListItem> voteLists = response.body();
                    if (voteLists.size() != 0){
                        Log.v("TAG", voteLists.get(0).getPhone());

                        voteListArrayList = voteLists;

                        vote_layout.removeAllViews();

                        for (VoteListItem i : voteListArrayList){
                            LayoutInflater layoutInflater = LayoutInflater.from(VoteInfoNotSelectActivity.this);
                            View customView = layoutInflater.inflate(R.layout.vote_list_item, null);

                            String place_name = i.getPlace_name();
                            String place_url = i.getPlace_url();
                            String road_address_name = i.getRoad_address_name();
                            String address_name = i.getAddress_name();
                            String phone = i.getPhone();

                            ((TextView) customView.findViewById(R.id.place_name)).setText(place_name);
                            ((TextView) customView.findViewById(R.id.road_address_name)).setText(road_address_name);
                            ((TextView) customView.findViewById(R.id.address_name)).setText(address_name);
                            ((TextView) customView.findViewById(R.id.phone)).setText(phone);

                            vote_layout.addView(customView);
                        }
                    }


                } else {
                    Log.v("TAG", "error = " + String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<VoteListItem>> call, Throwable t) {
                Log.v("TAG", "Fail");
                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.vote_select_place){
            TextView check_option = findViewById(R.id.vote_select_place);
            VoteDialog voteDialog = new VoteDialog(VoteInfoNotSelectActivity.this, voteListArrayList, check_option);
            voteDialog.show();
        } else if (view.getId() == R.id.reg_vote_comment_button){
            TextView place = findViewById(R.id.vote_select_place);
            Call<Integer> call_get = service.getVoteItemId(vote_id, place.getText().toString());
            call_get.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        int vote_item_id = response.body();
                        EditText regEditText = findViewById(R.id.vote_comment_et);
                        Call<Boolean> call_get = service.writeVoteComment(vote_id, vote_item_id, regEditText.getText().toString(), MainActivity.nickname);
                        call_get.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if (response.isSuccessful()) {
                                    finish();
                                } else {
                                    Log.v("TAG", "error = " + String.valueOf(response.code()));
                                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Log.v("TAG", "Fail");
                                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Log.v("TAG", "error = " + String.valueOf(response.code()));
                        Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.v("TAG", "Fail");
                    Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
