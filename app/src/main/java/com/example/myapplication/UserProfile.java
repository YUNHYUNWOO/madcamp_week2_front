package com.example.myapplication;

public class UserProfile {
    private String username;
    private String password;
    private String platform;
    private String nickname;
    private String place;
    private String profile;
    public static UserProfile userInfo;

    public UserProfile(String username,
                       String password,
                       String platform,
                       String nickname,
                       String place,
                       String profile){
        this.username = username;
        this.password = password;
        this.platform = platform;
        this.nickname = nickname;
        this.place = place;
        this.profile = profile;
    }

    public boolean equals(Object obj){
        if (obj instanceof UserProfile) {
            UserProfile p = (UserProfile) obj;
            return p.getUsername().equals(this.username);
        }
        return false;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPlatform(){
        return platform;
    }
    public void setPlatform(String platform){
        this.platform = platform;
    }
    public String getNickname(){
        return nickname;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getPlace(){
        return place;
    }
    public void setPlace(String place){
        this.place = place;
    }
    public String getProfile(){
        return profile;
    }
    public void setProfile(String profile){
        this.username = profile;
    }
}
