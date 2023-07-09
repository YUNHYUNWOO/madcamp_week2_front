package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

public class PostInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postinfo_activity);

        Toolbar mToolbar = findViewById(R.id.post_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        TextView post_title = (TextView) findViewById(R.id.post_title);
        TextView post_date = (TextView) findViewById(R.id.post_date);
        TextView post_content = (TextView) findViewById(R.id.post_content);
        TextView post_writer = (TextView) findViewById(R.id.post_writer);

        Intent intent = getIntent();

        String id = intent.getExtras().getString("id");
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
}
