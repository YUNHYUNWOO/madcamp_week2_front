package com.example.myapplication;

public class VoteContents {
    private int id;
    private String title;
    private String address;
    private String contents;

    private String category;
    private int like;
    private String writer;
    private String postImage;
    private String uploadTime;
    private int hits;

    public VoteContents(int id,
                        String title,
                        String contents,
                        String category,
                        String address,
                        int like,
                        String writer,
                        String postImage,
                        String uploadTime,
                        int hits){
        this.id = id;
        this.title = title;
        this.address = address;
        this.contents = contents;
        this.category = category;
        this.like = like;
        this.writer = writer;
        this.postImage = postImage;
        this.uploadTime = uploadTime;
        this.hits = hits;
    }

    public boolean equals(Object obj){
        if (obj instanceof VoteContents) {
            VoteContents p = (VoteContents) obj;
            return p.getId() == this.id;
        }
        return false;
    }

    public int getId(){
        return id;
    }
    public void setId(int _id){
        id = _id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String _title){
        title = _title;
    }
    public String getAddress(){return address; }
    public void setAddress(){this.address = address; }
    public String getContents(){
        return contents;
    }
    public void setContents(String _contents){
        contents = _contents;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String _category){
        category = _category;
    }
    public int getLike(){
        return like;
    }
    public void setLike(int _like){
        like = _like;
    }
    public String getWriter() {
        return writer;
    }
    public void setWriter(String _writer) {
        writer = _writer;
    }
    public String getPostImage() {
        return postImage;
    }
    public void setPostImg(String _postImage) {
        postImage = _postImage;
    }
    public String getUploadTime() {
        return uploadTime;
    }
    public void setUploadTime(String _uploadTime) {
        writer = _uploadTime;
    }
    public int getHits(){
        return hits;
    }
}
