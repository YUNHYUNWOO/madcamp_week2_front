package com.example.myapplication;

import com.kakao.sdk.user.model.User;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/")
    Call<ResponseBody> sessionCheck();

    @FormUrlEncoded
    @POST("/auth/join")
    Call<UserProfile> postUserProfile(@Field("userProfile") String userProfile);

    @GET("/main/getall")
    Call<PostContents> getAllPosts();

    @FormUrlEncoded
    @POST("/login")
    Call<ResponseBody> postFunc(@Field("data") String data);

    @FormUrlEncoded
    @PUT("/login/put/{id}")
    Call<ResponseBody> putFunc(@Path("id") String id, @Field("data") String data);

    @DELETE("/login/delete/{id}")
    Call<ResponseBody> deleteFunc(@Path("id") String id);
}