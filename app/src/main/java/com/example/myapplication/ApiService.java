package com.example.myapplication;

import com.kakao.sdk.user.model.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    @POST("/auth/dup_id")
    Call<Boolean> duplicateIdCheck(@Field("username") String username);

    @FormUrlEncoded
    @POST("/auth/dup_nickname")
    Call<Boolean> duplicateNicknameCheck(@Field("nickname") String nickname);

    @FormUrlEncoded
    @POST("/auth/join")
    Call<ResponseBody> join(@Field("data") String userProfile);

    @FormUrlEncoded
    @POST("/profile")
    Call<UserProfile> getUserProfile(@Field("nickname") String nickname);

    @GET("/main")
    Call<ArrayList<PostContents>> getAllPosts();
    @FormUrlEncoded
    @POST("/main/post")
    Call<Boolean> postPost(@Field("post") String postContents);

    @FormUrlEncoded
    @POST("/auth/login")
    Call<ResponseBody> postFunc(@Field("data") String data);
    @FormUrlEncoded
    @POST("auth/kakao")
    Call<ResponseBody> kakaoUserCheck(@Field("data") String data);
    @FormUrlEncoded
    @POST("/auth/kakaojoin")
    Call<ResponseBody> kakaoJoin(@Field("data") String data);

    @FormUrlEncoded
    @PUT("/login/put/{id}")
    Call<ResponseBody> putFunc(@Path("id") String id, @Field("data") String data);

    @DELETE("/login/delete/{id}")
    Call<ResponseBody> deleteFunc(@Path("id") String id);
}