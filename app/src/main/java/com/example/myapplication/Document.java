package com.example.myapplication;

import com.google.gson.Gson;

public class Document {

    public Address address;
    // getters and setters

    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}