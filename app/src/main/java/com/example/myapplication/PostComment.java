package com.example.myapplication;

public class PostComment {
    private int post_id;
    private String writer;
    private String comment;
    private String uploadTime;
    private String profile;
    public static PostComment userInfo;

    public PostComment(int post_id,
                       String writer,
                       String comment,
                       String uploadTime,
                       String profile){
        this.post_id = post_id;
        this.writer = writer;
        this.comment = comment;
        this.uploadTime = uploadTime;
        this.profile = profile;
    }
    public int getPost_id(){
        return post_id;
    }
    public void setPost_id(int post_id){
        this.post_id = post_id;
    }
    public String getWriter() {
        return writer;
    }
    public void setWriter(String password){
        this.writer = writer;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
    public String getUploadTime(){
        return uploadTime;
    }
    public void setUploadTime(String platform){
        this.uploadTime = uploadTime;
    }
    public String getProfile(){
        return profile;
    }
    public void setProfile(String profile){
        this.profile = profile;
    }
}
