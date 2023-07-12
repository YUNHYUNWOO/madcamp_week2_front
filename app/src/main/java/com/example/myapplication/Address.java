package com.example.myapplication;

import com.google.gson.Gson;

public class Address {
    public String address_name;
    public String region_1depth_name;
    public String region_2depth_name;
    public String region_3depth_name;
    public String region_3depth_h_name;
    public String h_code;
    public String b_code;
    public String mountain_yn;
    public String main_address_no;
    public String sub_address_no;
    public String x;
    public String y;
    // getters and setters
    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}



