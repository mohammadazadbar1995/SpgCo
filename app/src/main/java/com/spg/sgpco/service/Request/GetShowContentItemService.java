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
import com.spg.sgpco.service.ResponseModel.GetShowItemResponse;
import com.spg.sgpco.service.ResponseModel.ShowContentResponse;
import com.spg.sgpco.service.ServerListener;
import com.spg.sgpco.service.ServerTransaction;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public class GetShowContentItemService {


    private static GetShowContentItemService getShowContentItemService;

    public static GetShowContentItemService getInstance() {
        if (getShowContentItemService == null) {
            getShowContentItemService = new GetShowContentItemService();
        }
        return getShowContentItemService;
    }


    public void getShowItemContent(final Resources res, String item, final ResponseListener<GetShowItemResponse> responseListener) {
        new ServerTransaction().setCall(res, ApiClient.getClient().create(ReqInterface.class).getShowContentItem(item), new ServerListener() {
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
                    GetShowItemResponse response = gson.fromJson(mJson, GetShowItemResponse.class);
                    responseListener.onSuccess(response);
                } catch (JsonSyntaxException ex) {
                    responseListener.onGetErrore(res.getString(R.string.jsonError));
                }
            }
        });
    }
}
