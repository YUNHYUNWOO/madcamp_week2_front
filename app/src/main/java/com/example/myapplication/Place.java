package com.example.myapplication;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Place {
    public String place_name;
    public String category_name;
    public String category_group_code;
    public String category_group_name;
    public String phone;
    public String address_name;
    public String road_address_name;
    public String x;
    public String y;
    public String place_url;

    @NonNull
    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}