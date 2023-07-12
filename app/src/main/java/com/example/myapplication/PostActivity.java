package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

import retrofit2.Retrofit;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MainActivityLog";
    private final String URL = "https://025b-192-249-19-234.ngrok-free.app";

    private Retrofit retrofit;
    private ApiService service;

    private Button btn_get, btn_post, btn_delete, btn_update;

    BoardMainFragment boardMainFragment;
    VoteFragment voteFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        boardMainFragment = new BoardMainFragment();
        voteFragment = new VoteFragment();
        profileFragment = new ProfileFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, boardMainFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.post_bottom_navigation);

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.board){
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, boardMainFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.recommend) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, voteFragment).commit();
                    return true;
                }
                else if (item.getItemId() == R.id.profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, profileFragment).commit();
                    return true;
                }
                else return false;
            }
        });
    }
    public void onClick(View v) {
    }
}