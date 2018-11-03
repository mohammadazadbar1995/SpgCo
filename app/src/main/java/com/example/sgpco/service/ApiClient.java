package com.example.sgpco.service;

import android.util.Base64;

import com.example.sgpco.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit getClient() {
        String username = "DeltaApi";
        String password = "12345";
        final String basicAuth = "Basic " + new String(Base64.encode((username + ":" + password).getBytes(), Base64.NO_WRAP));
        OkHttpClient myClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request.Builder ongoing;
            ongoing = chain.request().newBuilder().addHeader("Content-Type", "application/json; charset=utf-8").addHeader("Authorization", basicAuth);
            return chain.proceed(ongoing.build());
        }).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
        return new Builder().baseUrl(Constants.URL_API).addConverterFactory(GsonConverterFactory.create()).client(myClient).build();
    }

}
