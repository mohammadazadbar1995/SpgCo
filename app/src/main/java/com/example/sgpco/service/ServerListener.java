package com.example.sgpco.service;

import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public interface ServerListener {

    void onFailure(String str);

    void onSuccess(JsonObject jsonObject) throws JSONException, UnsupportedEncodingException;
}
