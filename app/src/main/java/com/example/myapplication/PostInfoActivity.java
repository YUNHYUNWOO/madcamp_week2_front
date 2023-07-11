package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostInfoActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<PostComment> commentList = new ArrayList<>(0);
    private Retrofit retrofit;
    private ApiService service;
    private LinearLayout comment_layout;
    private int post_id;
    private Button reg_comment_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postinfo_activity);

        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);

        reg_comment_btn = findViewById(R.id.reg_comment_button);
        reg_comment_btn.setOnClickListener(this);

        Toolbar mToolbar = findViewById(R.id.post_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        TextView post_title = (TextView) findViewById(R.id.post_title);
        TextView post_date = (TextView) findViewById(R.id.post_date);
        TextView post_content = (TextView) findViewById(R.id.post_content);
        TextView post_writer = (TextView) findViewById(R.id.post_writer);

        comment_layout = findViewById(R.id.post_comment_layout);

        Intent intent = getIntent();

        post_id = intent.getIntExtra("id", 0);
        String write_time = intent.getExtras().getString("write_time");
        String hits = intent.getExtras().getString("hits");
        String title = intent.getExtras().getString("title");
        String writer = intent.getExtras().getString("writer");
        String like_numStr = intent.getExtras().getString("like_num");
        String contents = intent.getExtras().getString("contents");

        post_title.setText(title);
        post_date.setText(write_time);
        post_content.setText(contents);
        post_writer.setText(writer);

        initData();
    }
    public void initData(){
        Call<ArrayList<PostComment>> call_get = service.readComments(post_id);
        call_get.enqueue(new Callback<ArrayList<PostComment>>() {
            @Override
            public void onResponse(Call<ArrayList<PostComment>> call, Response<ArrayList<PostComment>> response) {
                if (response.isSuccessful()) {
                    ArrayList<PostComment> postComments = response.body();
                    if (postComments.size() != 0){
                        Log.v("TAG", postComments.get(0).getUploadTime());

                        commentList = postComments;

                        comment_layout.removeAllViews();

                        for (PostComment i : commentList){
                            LayoutInflater layoutInflater = LayoutInflater.from(PostInfoActivity.this);
                            View customView = layoutInflater.inflate(R.layout.post_comment, null);

                            String writer = i.getWriter();
                            String comment = i.getComment();
                            String upload = i.getUploadTime();

                            ((TextView) customView.findViewById(R.id.cmt_userid_tv)).setText(writer);
                            ((TextView) customView.findViewById(R.id.cmt_content_tv)).setText(comment);
                            ((TextView) customView.findViewById(R.id.cmt_date_tv)).setText(upload);

                            comment_layout.addView(customView);
                        }
                    }

                    Log.v("TAG", "성공");
                    Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("TAG", "error = " + String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PostComment>> call, Throwable t) {
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
        if (view.getId() == R.id.reg_comment_button){
            EditText newCommentEditText = findViewById(R.id.comment_et);

            String newComment = newCommentEditText.getText().toString();
            String newCommentWriter = MainActivity.nickname;

            Date nowDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
            String newCommentUploadTime = sdf.format(nowDate);

            JSONObject commentInfo = new JSONObject();
            try {
                commentInfo.put("post_id", post_id);
                commentInfo.put("comment", newComment);
                commentInfo.put("writer", newCommentWriter);
                commentInfo.put("uploadTime", newCommentUploadTime);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            Call<Boolean> call_write = service.writeComments(commentInfo.toString());
            call_write.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Log.v("TAG", "성공");
                        Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
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
        }
    }
}
