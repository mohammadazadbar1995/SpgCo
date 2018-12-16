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
import com.spg.sgpco.service.RequestModel.RegisterReq;
import com.spg.sgpco.service.RequestModel.VerifyReq;
import com.spg.sgpco.service.ResponseModel.LoginResponse;
import com.spg.sgpco.service.ResponseModel.VerifyResponse;
import com.spg.sgpco.service.ServerListener;
import com.spg.sgpco.service.ServerTransaction;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public class RegisterService {


    private static RegisterService registerService;

    public static RegisterService getInstance() {
        if (registerService == null) {
            registerService = new RegisterService();
        }
        return registerService;
    }


    public void regiter(final Resources res, RegisterReq req, final ResponseListener<LoginResponse> responseListener) {
        new ServerTransaction().setCall(res, ApiClient.getClientLogin().create(ReqInterface.class).register(req), new ServerListener() {
            @Override
            public void onFailure(String str) {
                responseListener.onGetErrore(str);
            }

            @Override
            public void onUtorized() {
                responseListener.onUtorized();
            }

            @Override
            public void onSuccess(JsonObject jsonObject) throws JSONException, UnsupportedEncodingException {
                Gson gson = new Gson();
                try {
                    String mJsonString = jsonObject.toString();
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = parser.parse(mJsonString);
                    LoginResponse response = gson.fromJson(mJson, LoginResponse.class);
                    responseListener.onSuccess(response);
                } catch (JsonSyntaxException ex) {
                    responseListener.onGetErrore(res.getString(R.string.jsonError));
                }
            }
        });
    }
}
