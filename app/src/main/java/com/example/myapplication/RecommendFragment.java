package com.example.myapplication;

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

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecommendFragment extends Fragment {
    ArrayList<PostContents> postContentsArrayList;
    private Retrofit retrofit;
    private ApiService service;
    private String URL = "https://6102-192-249-19-234.ngrok-free.app";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.board_main_page, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.board_main_toolbar);

        try {
            initUI(rootView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

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

    private void initUI(ViewGroup rootView) throws IOException, JSONException {
        ArrayList<String> nameDataSet = new ArrayList<>();
        ArrayList<String> telDataSet = new ArrayList<>();
        ArrayList<String> emailDataSet = new ArrayList<>();
        ArrayList<String> galleryDataSet = new ArrayList<>();

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);

        Call<PostContents> call_get = service.getAllPosts();
        call_get.enqueue(new Callback<PostContents>() {
            @Override
            public void onResponse(Call<PostContents> call, Response<PostContents> response) {
                if (response.isSuccessful()) {
                    String title = response.body().getTitle();
                    String contents = response.body().getContents();
                    Log.v("TAG", "result = " + title + contents);
                    Toast.makeText(getContext(), title + contents, Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("TAG", "error = " + String.valueOf(response.code()));
                    Toast.makeText(getContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PostContents> call, Throwable t) {
                Log.v("TAG", "Fail");
                Toast.makeText(getContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });

        StringBuffer buffer = new StringBuffer();

        String jsonData = buffer.toString();

//        JSONArray jsonArray = new JSONArray(jsonData);
//
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject jo = jsonArray.getJSONObject(i);
//            String name = jo.getString("Name");
//            String phone = jo.getString("tel");
//            String email_address = jo.getString("email");
//            String image_path = jo.getString("pic");
//            nameDataSet.add(name);
//            telDataSet.add(phone);
//            emailDataSet.add(email_address);
//            galleryDataSet.add(image_path);
//        }
////        Toast.makeText(getActivity(), "" + nameDataSet.size(), Toast.LENGTH_SHORT).show();
//
//        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//        CustomAdapter customAdapter = new CustomAdapter(nameDataSet, telDataSet, emailDataSet, galleryDataSet);
//
//        //click event implementation
//        customAdapter.setOnItemclickListener(new CustomAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClicked(int position, String data) {
//                Intent intent = new Intent(getContext(), ContactsActivity.class);
//                startActivity(intent);
//            }
//        });
//        recyclerView.setAdapter(customAdapter);
    }
}
