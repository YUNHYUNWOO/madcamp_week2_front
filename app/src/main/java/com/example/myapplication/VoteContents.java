package com.example.myapplication;

public class VoteContents {
    private String id;
    private String title;
    private String contents;
    private int like;

    private String writer;
    private String writerName;

    private String postImage;

    private String uploadTime;
    private int hits;

    public VoteContents(String id,
                        String title,
                        String contents,
                        int like,
                        String writer,
                        String writerName,
                        String postImage,
                        String uploadTime,
                        int hits){
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.like = like;
        this.writer = writer;
        this.writerName = writerName;
        this.postImage = postImage;
        this.uploadTime = uploadTime;
        this.hits = hits;
    }

    public boolean equals(Object obj){
        if (obj instanceof VoteContents) {
            VoteContents p = (VoteContents) obj;
            return p.getId().equals(this.id);
        }
        return false;
    }

    public String getId(){
        return id;
    }
    public void setId(String _id){
        id = _id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String _title){
        title = _title;
    }
    public String getContents(){
        return contents;
    }
    public void setContents(String _contents){
        contents = _contents;
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
