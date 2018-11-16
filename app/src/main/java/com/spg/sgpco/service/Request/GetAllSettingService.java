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
import com.spg.sgpco.service.RequestModel.LoginWithCodeForgetPassReq;
import com.spg.sgpco.service.ResponseModel.LoginResponse;
import com.spg.sgpco.service.ResponseModel.SettingAllResponse;
import com.spg.sgpco.service.ServerListener;
import com.spg.sgpco.service.ServerTransaction;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public class GetAllSettingService {


    private static GetAllSettingService getAllSettingService;

    public static GetAllSettingService getInstance() {
        if (getAllSettingService == null) {
            getAllSettingService = new GetAllSettingService();
        }
        return getAllSettingService;
    }


    public void getAllSetting(final Resources res, final ResponseListener<SettingAllResponse> responseListener) {
        new ServerTransaction().setCall(res, ApiClient.getClient().create(ReqInterface.class).getAllSetting(), new ServerListener() {
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
                    SettingAllResponse response = gson.fromJson(mJson, SettingAllResponse.class);
                    responseListener.onSuccess(response);
                } catch (JsonSyntaxException ex) {
                    responseListener.onGetErrore(res.getString(R.string.jsonError));
                }
            }
        });
    }
}
