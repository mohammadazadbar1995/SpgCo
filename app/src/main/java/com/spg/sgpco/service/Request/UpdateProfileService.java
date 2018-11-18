package com.spg.sgpco.service.Request;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.spg.sgpco.R;
import com.spg.sgpco.service.ApiClient;
import com.spg.sgpco.service.ReqInterface;
import com.spg.sgpco.service.RequestModel.UpdateCustomerReq;
import com.spg.sgpco.service.RequestModel.UpdateProfileReq;
import com.spg.sgpco.service.ResponseModel.UpdateCustomerResponse;
import com.spg.sgpco.service.ResponseModel.UpdateProfileResponse;
import com.spg.sgpco.service.ServerListener;
import com.spg.sgpco.service.ServerTransaction;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public class UpdateProfileService {


    private static UpdateProfileService updateProfileService;

    public static UpdateProfileService getInstance() {
        if (updateProfileService == null) {
            updateProfileService = new UpdateProfileService();
        }
        return updateProfileService;
    }


    public void updateProfile(final Resources res, UpdateProfileReq req, final ResponseListener<UpdateProfileResponse> responseListener) {
        new ServerTransaction().setCall(res, ApiClient.getClient().create(ReqInterface.class).updateProfile(req), new ServerListener() {
            @Override
            public void onFailure(String str) {
                responseListener.onGetErrore(str);
            }

            @Override
            public void onSuccess(JsonObject jsonObject) throws JSONException, UnsupportedEncodingException {
                Gson gson = new Gson();
                try {
                    String mJsonString = jsonObject.toString();
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = parser.parse(mJsonString);
                    UpdateProfileResponse response = gson.fromJson(mJson, UpdateProfileResponse.class);
                    responseListener.onSuccess(response);
                } catch (JsonSyntaxException ex) {
                    responseListener.onGetErrore(res.getString(R.string.jsonError));
                }
            }
        });
    }
}
