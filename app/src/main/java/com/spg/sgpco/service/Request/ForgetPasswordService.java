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
import com.spg.sgpco.service.RequestModel.ForgetPassReq;
import com.spg.sgpco.service.RequestModel.VerifyReq;
import com.spg.sgpco.service.ResponseModel.VerifyResponse;
import com.spg.sgpco.service.ServerListener;
import com.spg.sgpco.service.ServerTransaction;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public class ForgetPasswordService {


    private static ForgetPasswordService forgetPasswordService;

    public static ForgetPasswordService getInstance() {
        if (forgetPasswordService == null) {
            forgetPasswordService = new ForgetPasswordService();
        }
        return forgetPasswordService;
    }


    public void forgetPassword(final Resources res, ForgetPassReq req, final ResponseListener<VerifyResponse> responseListener) {
        new ServerTransaction().setCall(res, ApiClient.getClientLogin().create(ReqInterface.class).forgetPass(req), new ServerListener() {
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
