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
import com.spg.sgpco.service.RequestModel.CreateOrdinaryProjectReq;
import com.spg.sgpco.service.ResponseModel.CreateCustomerResponse;
import com.spg.sgpco.service.ResponseModel.CreateOrdinaryProjectResponse;
import com.spg.sgpco.service.ServerListener;
import com.spg.sgpco.service.ServerTransaction;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public class OrdinaryProjectService {


    private static OrdinaryProjectService ordinaryProjectService;

    public static OrdinaryProjectService getInstance() {
        if (ordinaryProjectService == null) {
            ordinaryProjectService = new OrdinaryProjectService();
        }
        return ordinaryProjectService;
    }


    public void createOrdinaryProject(final Resources res, CreateOrdinaryProjectReq req, final ResponseListener<CreateOrdinaryProjectResponse> responseListener) {
        new ServerTransaction().setCall(res, ApiClient.getClient().create(ReqInterface.class).createOrdinaryProject(req), new ServerListener() {
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
                    CreateOrdinaryProjectResponse response = gson.fromJson(mJson, CreateOrdinaryProjectResponse.class);
                    responseListener.onSuccess(response);
                } catch (JsonSyntaxException ex) {
                    responseListener.onGetErrore(res.getString(R.string.jsonError));
                }
            }
        });
    }
}
