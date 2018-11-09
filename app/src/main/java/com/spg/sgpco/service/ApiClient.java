package com.spg.sgpco.service;

import android.content.Context;
import android.text.TextUtils;

import com.spg.sgpco.controller.AppController;
import com.spg.sgpco.utils.Constants;
import com.spg.sgpco.utils.PreferencesData;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Context context = null;

    public static Context getContext() {
        if (context != null) {
            return context;
        } else {
            return AppController.getCurrentContext();
        }
    }

    public static Retrofit getClient() {

        final String basicAuth;
        if (TextUtils.isEmpty(PreferencesData.getToken(getContext()))) {
            basicAuth = "Bearer " + PreferencesData.getToken(getContext());
        } else {
            basicAuth = "";
        }
        OkHttpClient myClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request.Builder ongoing;
            ongoing = chain.request().newBuilder().addHeader("Content-Type", "application/json; charset=utf-8");
            ongoing.addHeader("Authorization", basicAuth);
            ongoing.addHeader("User-Agent","com.spg.sgpco");
            return chain.proceed(ongoing.build());
        }).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
        return new Builder().baseUrl(Constants.URL_API).addConverterFactory(GsonConverterFactory.create()).client(myClient).build();

    }

}
