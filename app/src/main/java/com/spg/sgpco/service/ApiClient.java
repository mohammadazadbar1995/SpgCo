package com.spg.sgpco.service;

import android.content.Context;
import android.text.TextUtils;

import com.ihsanbal.logging.LoggingInterceptor;
import com.spg.sgpco.BuildConfig;
import com.spg.sgpco.controller.AppController;
import com.spg.sgpco.utils.Constants;
import com.spg.sgpco.utils.PreferencesData;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
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
        if (!TextUtils.isEmpty(PreferencesData.getToken(getContext()))) {
            basicAuth =  PreferencesData.getToken(getContext());
        } else {
            basicAuth = "";
        }
        OkHttpClient myClient = new OkHttpClient.Builder().addInterceptor(getLoggingInterceptor(basicAuth)).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
        return new Builder().baseUrl(Constants.URL_API).addConverterFactory(GsonConverterFactory.create()).client(myClient).build();

    }


    public static Retrofit getClientLogin() {

        OkHttpClient myClient = new OkHttpClient.Builder().addInterceptor(getLoggingInterceptor("")).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
        return new Builder().baseUrl(Constants.URL_API).addConverterFactory(GsonConverterFactory.create()).client(myClient).build();

    }

    private static LoggingInterceptor getLoggingInterceptor(String basicAuth) {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(com.ihsanbal.logging.Level.BASIC)
                .log(Platform.INFO)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("User-Agent", "com.spg.sgpco")
                .addHeader("Authorization", basicAuth)
                .request("request")
                .response("response")
                .build();
    }

}
