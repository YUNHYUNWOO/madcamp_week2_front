package com.example.myapplication;

public class VoteList {
    private String id;
    private int like;
    private String option;

    public VoteList(String id,
                        int like,
                        String option){
        this.id = id;
        this.like = like;
        this.option = option;
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
    public int getLike(){
        return like;
    }
    public void setLike(int _like){
        like = _like;
    }
    public String getOption() {
        return option;
    }
    public void setOption(String _option) {
        option = _option;
    }
}
