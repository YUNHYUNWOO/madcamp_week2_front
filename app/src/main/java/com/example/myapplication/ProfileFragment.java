package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {
    private final String TAG = "ProfileFragmentLog";

    private Retrofit retrofit;
    private ApiService service;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.profile_fragment_layout, container, false);
        setHasOptionsMenu(true);

        TextView profile_nickname = (TextView) rootView.findViewById(R.id.profile_nickname);
        TextView profile_address = (TextView) rootView.findViewById(R.id.profile_address);

        try {
            initProfile(rootView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Button logout = (Button) rootView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return rootView;
    }

    private void initProfile(ViewGroup rootView) throws IOException, JSONException {
        ArrayList<PostContents> postContentsDataSet = new ArrayList<>();

        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);

        Call<UserProfile> call_getProfile = service.getUserProfile(MainActivity.nickname);
        call_getProfile.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    String nickname = response.body().getNickname();
                    String address = response.body().getPlace();
                    TextView profile_nickname = rootView.findViewById(R.id.profile_nickname);
                    TextView profile_address = rootView.findViewById(R.id.profile_address);
                    profile_nickname.setText(nickname);
                    profile_address.setText(address);
                    Log.v("TAG", "result = " + nickname + address);
                } else {
                    Log.v("TAG", "error = " + String.valueOf(response.code()));
                    Toast.makeText(getContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.v("TAG", "Fail");
                Toast.makeText(getContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });
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
}