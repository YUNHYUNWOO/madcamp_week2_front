package com.example.myapplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KakaoAPI {
    @GET("v2/local/search/keyword.json")
    Call<ResultSearchKeyword> getSearchKeyword(@Header("Authorization") String key, @Query("query") String query);
}