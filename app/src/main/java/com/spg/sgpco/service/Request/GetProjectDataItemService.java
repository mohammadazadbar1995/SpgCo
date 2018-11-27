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
import com.spg.sgpco.service.ResponseModel.GalleryItemResponse;
import com.spg.sgpco.service.ResponseModel.UpdateProjectResponse;
import com.spg.sgpco.service.ServerListener;
import com.spg.sgpco.service.ServerTransaction;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public class GetProjectDataItemService {


    private static GetProjectDataItemService getProjectDataItemService;

    public static GetProjectDataItemService getInstance() {
        if (getProjectDataItemService == null) {
            getProjectDataItemService = new GetProjectDataItemService();
        }
        return getProjectDataItemService;
    }


    public void getProjectDataItem(final Resources res, int item, final ResponseListener<UpdateProjectResponse> responseListener) {
        new ServerTransaction().setCall(res, ApiClient.getClient().create(ReqInterface.class).getProjectDataItem(item), new ServerListener() {
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
                    UpdateProjectResponse response = gson.fromJson(mJson, UpdateProjectResponse.class);
                    responseListener.onSuccess(response);
                } catch (JsonSyntaxException ex) {
                    responseListener.onGetErrore(res.getString(R.string.jsonError));
                }
            }
        });
    }
}
