package com.spg.sgpco.service;

import com.google.gson.JsonObject;
import com.spg.sgpco.service.RequestModel.ForgetPassReq;
import com.spg.sgpco.service.RequestModel.LoginReq;
import com.spg.sgpco.service.RequestModel.LoginWithCodeForgetPassReq;
import com.spg.sgpco.service.RequestModel.RegisterReq;
import com.spg.sgpco.service.RequestModel.VerifyReq;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReqInterface {
    @POST("user/login")
    Call<JsonObject> login(@Body LoginReq req);

    @POST("user/verify")
    Call<JsonObject> verifyCode(@Body VerifyReq req);

    @POST("user/create")
    Call<JsonObject> register(@Body RegisterReq req);

    @POST("user/login/phone")
    Call<JsonObject> forgetPass(@Body ForgetPassReq req);

    @POST("user/login/phone/verify")
    Call<JsonObject> loginWithCode(@Body LoginWithCodeForgetPassReq req);

    @POST("user/logout")
    Call<JsonObject> logout();

//    @POST("v2/home/GetNewVersion")
//    Call<JsonObject> VersionControl(@Body ForceUpdateReq req);


}
