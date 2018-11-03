package com.example.sgpco.service;

import android.content.res.Resources;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.example.sgpco.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerTransaction {
    public void setCall(final Resources res, Call<JsonObject> call, final ServerListener serverListener) {

        call.enqueue(new Callback<JsonObject>() {

            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("response", response.code() + "");

                if (response.code() == ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                    Log.i("response", response.body() + "");
                    try {
                        JsonObject data = (JsonObject) response.body();
                        if (data != null) {
                            serverListener.onSuccess(data);
                            return;
                        } else {
                            serverListener.onFailure(res.getString(R.string.notFound));
                            return;
                        }
                    } catch (Exception e) {
                        serverListener.onFailure(res.getString(R.string.jsonError));
                        return;
                    }
                }
                if (response.code() == 401) {
                    serverListener.onFailure(res.getString(R.string.unauthorized));
                } else
                    serverListener.onFailure(res.getString(R.string.noNetworkConnectivity));
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {

                    serverListener.onFailure(res.getString(R.string.connectionTimeout));


            }
        });
    }
}
