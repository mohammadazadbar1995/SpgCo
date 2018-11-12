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
import com.spg.sgpco.service.RequestModel.LoginReq;
import com.spg.sgpco.service.RequestModel.VerifyReq;
import com.spg.sgpco.service.ResponseModel.LoginResponse;
import com.spg.sgpco.service.ResponseModel.VerifyResponse;
import com.spg.sgpco.service.ServerListener;
import com.spg.sgpco.service.ServerTransaction;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public class VerifyCodeService {


    private static VerifyCodeService verifyCodeService;

    public static VerifyCodeService getInstance() {
        if (verifyCodeService == null) {
            verifyCodeService = new VerifyCodeService();
        }
        return verifyCodeService;
    }


    public void verifyCode(final Resources res, VerifyReq req, final ResponseListener<VerifyResponse> responseListener) {
        new ServerTransaction().setCall(res, ApiClient.getClientLogin().create(ReqInterface.class).verifyCode(req), new ServerListener() {
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
                    VerifyResponse response = gson.fromJson(mJson, VerifyResponse.class);
                    responseListener.onSuccess(response);
                } catch (JsonSyntaxException ex) {
                    responseListener.onGetErrore(res.getString(R.string.jsonError));
                }
            }
        });
    }
}
