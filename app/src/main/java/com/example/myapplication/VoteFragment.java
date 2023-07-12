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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VoteFragment extends Fragment {
    ArrayList<VoteContents> voteList = new ArrayList<>(0);
    private Retrofit retrofit;
    private ApiService service;

    private FloatingActionButton new_vote_btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.vote_main_page, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.vote_main_toolbar);

        new_vote_btn = rootView.findViewById(R.id.vote_fab_btn);

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

        RecyclerView recyclerView = rootView.findViewById(R.id.vote_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        VoteAdapter voteAdapter = new VoteAdapter(voteList);
        recyclerView.setAdapter(voteAdapter);

        Call<ArrayList<VoteContents>> call_get = service.getAllVotes();
        call_get.enqueue(new Callback<ArrayList<VoteContents>>() {
            @Override
            public void onResponse(Call<ArrayList<VoteContents>> call, Response<ArrayList<VoteContents>> response) {
                if (response.isSuccessful()) {
                    ArrayList<VoteContents> voteContentsDataSet = response.body();

                    voteList = voteContentsDataSet;
                    voteAdapter.setList(voteList);
                    voteAdapter.notifyDataSetChanged();

                    Log.v("TAG", "result = ");
//                    Toast.makeText(getContext(), "살려줘", Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("TAG", "error = " + String.valueOf(response.code()));
                    Toast.makeText(getContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<VoteContents>> call, Throwable t) {
                Log.v("TAG", "Fail");
                Toast.makeText(getContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });

        voteAdapter.setOnItemclickListener(new VoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, String data) {
                Intent intent = new Intent(getContext(), VoteInfoActivity.class);
                startActivity(intent);
            }
        });
        new_vote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), VoteAddActivity.class);
                startActivity(intent);
            }
        });
    }
}
