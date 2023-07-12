package com.example.myapplication;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
    Call<String> join(@Field("data") String userProfile);
    @FormUrlEncoded
    @POST("/auth/login")
    Call<String> postFunc(@Field("data") String data);
    @FormUrlEncoded
    @POST("auth/kakao")
    Call<String> kakaoUserCheck(@Field("data") String data);
    @FormUrlEncoded
    @POST("/auth/kakaojoin")
    Call<String> kakaoJoin(@Field("data") String data);

    @FormUrlEncoded
    @POST("/profile")
    Call<UserProfile> getUserProfile(@Field("nickname") String nickname);

    @GET("/main")
    Call<ArrayList<PostContents>> getAllPosts();
    @FormUrlEncoded
    @POST("/main/post")
    Call<Boolean> postPost(@Field("data") String postContents);
    @FormUrlEncoded
    @POST("/main/read_comment")
    Call<ArrayList<PostComment>> readComments(@Field("data") int postId);
    @FormUrlEncoded
    @POST("/main/write_comment")
    Call<Boolean> writeComments(@Field("data") String comment);

    @GET("/vote/getAllVotes")
    Call<ArrayList<VoteContents>> getAllVotes();
    @FormUrlEncoded
    @POST("/vote/read")
    Call<ArrayList<VoteListItem>> readVotes(@Field("data") int voteId);

    @FormUrlEncoded
    @PUT("/login/put/{id}")
    Call<ResponseBody> putFunc(@Path("id") String id, @Field("data") String data);

    @DELETE("/login/delete/{id}")
    Call<ResponseBody> deleteFunc(@Path("id") String id);


    //Vote add


    @POST("/vote/newVote")
    Call<Integer> makeNewVote(@Body VoteContents newVote);


    @POST("/vote/newVoteList")
    Call<Integer> makeNewVoteList(@Body Postdata newVote);

    @POST("/vote/isOwner")
    Call<Boolean> isOwner(@Field("id") int id ,@Field("writter") String writter);

    @POST("/vote/isAlreadyVoted")
    Call<Boolean> isAlreadyVoted(@Field("id") int id, @Field("VoteContents") VoteContents voteContents);
}