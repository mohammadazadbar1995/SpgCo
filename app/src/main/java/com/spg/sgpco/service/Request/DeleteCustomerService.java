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
import com.spg.sgpco.service.RequestModel.DeleteCustomerReq;
import com.spg.sgpco.service.RequestModel.LoginReq;
import com.spg.sgpco.service.ResponseModel.DeleteCustomerResponse;
import com.spg.sgpco.service.ResponseModel.LoginResponse;
import com.spg.sgpco.service.ServerListener;
import com.spg.sgpco.service.ServerTransaction;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public class DeleteCustomerService {


    private static DeleteCustomerService deleteCustomerService;

    public static DeleteCustomerService getInstance() {
        if (deleteCustomerService == null) {
            deleteCustomerService = new DeleteCustomerService();
        }
        return deleteCustomerService;
    }


    public void deleteCustomer(final Resources res, DeleteCustomerReq req, final ResponseListener<DeleteCustomerResponse> responseListener) {
        new ServerTransaction().setCall(res, ApiClient.getClient().create(ReqInterface.class).deleteCustomer(req), new ServerListener() {
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
                    DeleteCustomerResponse response = gson.fromJson(mJson, DeleteCustomerResponse.class);
                    responseListener.onSuccess(response);
                } catch (JsonSyntaxException ex) {
                    responseListener.onGetErrore(res.getString(R.string.jsonError));
                }
            }
        });
    }
}
