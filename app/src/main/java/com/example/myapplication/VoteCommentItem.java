package com.example.myapplication;
public class VoteCommentItem {
    private int vote_id;
    private int vote_item_id;
    private String comment;
    private String writer;

    public int getVote_id() {
        return vote_id;
    }
    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }
    public int getVote_item_id() {
        return vote_id;
    }
    public void setVote_item_id(int vote_item_id) {
        this.vote_item_id = vote_item_id;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }
}