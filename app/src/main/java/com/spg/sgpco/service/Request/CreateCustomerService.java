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
import com.spg.sgpco.service.RequestModel.CreateCustomerReq;
import com.spg.sgpco.service.RequestModel.DeleteCustomerReq;
import com.spg.sgpco.service.ResponseModel.CreateCustomerResponse;
import com.spg.sgpco.service.ResponseModel.DeleteCustomerResponse;
import com.spg.sgpco.service.ServerListener;
import com.spg.sgpco.service.ServerTransaction;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public class CreateCustomerService {


    private static CreateCustomerService createCustomerService;

    public static CreateCustomerService getInstance() {
        if (createCustomerService == null) {
            createCustomerService = new CreateCustomerService();
        }
        return createCustomerService;
    }


    public void createCustomer(final Resources res, CreateCustomerReq req, final ResponseListener<CreateCustomerResponse> responseListener) {
        new ServerTransaction().setCall(res, ApiClient.getClient().create(ReqInterface.class).createCustomer(req), new ServerListener() {
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
                    CreateCustomerResponse response = gson.fromJson(mJson, CreateCustomerResponse.class);
                    responseListener.onSuccess(response);
                } catch (JsonSyntaxException ex) {
                    responseListener.onGetErrore(res.getString(R.string.jsonError));
                }
            }
        });
    }
}
