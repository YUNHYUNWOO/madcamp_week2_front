package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardMainFragment extends Fragment {
    ArrayList<PostContents> postList = new ArrayList<>(0);
    private Retrofit retrofit;
    private ApiService service;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.board_main_page, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.board_main_toolbar);

        initUI(rootView);

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void initUI(ViewGroup rootView) {
        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);

        RecyclerView recyclerView = rootView.findViewById(R.id.board_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        PostAdapter postAdapter = new PostAdapter(postList);
        recyclerView.setAdapter(postAdapter);

        Call<ArrayList<PostContents>> call_get = service.getAllPosts();
        call_get.enqueue(new Callback<ArrayList<PostContents>>() {
            @Override
            public void onResponse(Call<ArrayList<PostContents>> call, Response<ArrayList<PostContents>> response) {
                if (response.isSuccessful()) {
                    ArrayList<PostContents> postContentsDataSet = response.body();
                    Log.v("TAG", postContentsDataSet.get(0).getTitle());

                    postList = postContentsDataSet;
                    postAdapter.setList(postList);
                    postAdapter.notifyDataSetChanged();

//                    for(PostContents item : postContentsDataSet){
//                        postList.add(new PostContents(item.getId(),
//                                                      item.getTitle(),
//                                                      item.getContents(),
//                                                      item.getLike(),
//                                                      item.getWriter(),
//                                                      item.getPostImage(),
//                                                      item.getUploadTime(),
//                                                      item.getHits()));
//                    }

                    Log.v("TAG", "성공");
                    Toast.makeText(getContext(), "성공", Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("TAG", "error = " + String.valueOf(response.code()));
                    Toast.makeText(getContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PostContents>> call, Throwable t) {
                Log.v("TAG", "Fail");
                Toast.makeText(getContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });



        //click event implementation
        postAdapter.setOnItemclickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, String data) {
                Intent intent = new Intent(getContext(), PostInfoActivity.class);
                startActivity(intent);
            }
        });

    }
}
