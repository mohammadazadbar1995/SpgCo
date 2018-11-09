package com.spg.sgpco.service;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.spg.sgpco.BuildConfig;
import com.spg.sgpco.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerTransaction {
    public void setCall(final Resources res, Call<JsonObject> call, final ServerListener serverListener) {

        call.enqueue(new Callback<JsonObject>() {
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                try {
                    if (response.code() == 200) {
                        JsonObject data = response.body();
                        if (data != null) {
                            serverListener.onSuccess(data);
                        } else {
                            if (BuildConfig.DEBUG) {
                                serverListener.onFailure(response.message() + " , code: " + response.code());
                            } else {
                                serverListener.onFailure(res.getString(R.string.notFound));
                            }
                        }
                    } else if (response.code() == 401) {
                        serverListener.onFailure(res.getString(R.string.unauthorized));
                    } else if (response.code() == 404) {
                        serverListener.onFailure(res.getString(R.string.notFound));
                    } else if (response.code() == 500) {
                        serverListener.onFailure(res.getString(R.string.internalError));
                    } else {
                        if (BuildConfig.DEBUG) {
                            serverListener.onFailure(response.message() + " , code: " + response.code());
                        } else {
                            serverListener.onFailure(res.getString(R.string.noNetworkConnectivity));
                        }
                    }
                } catch (Exception e) {
                    if (BuildConfig.DEBUG) {
                        serverListener.onFailure(" setCall Exception: " + e.getMessage());
                    } else {
                        serverListener.onFailure(res.getString(R.string.jsonError));
                    }
                }
            }

            public void onFailure(@NonNull Call<JsonObject> call, Throwable t) {
                if (BuildConfig.DEBUG && !TextUtils.isEmpty(t.getMessage())) {
                    serverListener.onFailure(t.getMessage());
                } else {
                    serverListener.onFailure(res.getString(R.string.communicationError));
                }
            }
        });
    }
}
