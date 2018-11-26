package com.spg.sgpco.service;

import com.google.gson.JsonObject;
import com.spg.sgpco.service.RequestModel.CreateCustomerReq;
import com.spg.sgpco.service.RequestModel.CreateOrdinaryProjectReq;
import com.spg.sgpco.service.RequestModel.CreateThermostaticReq;
import com.spg.sgpco.service.RequestModel.DeleteCustomerReq;
import com.spg.sgpco.service.RequestModel.ForgetPassReq;
import com.spg.sgpco.service.RequestModel.LoginReq;
import com.spg.sgpco.service.RequestModel.LoginWithCodeForgetPassReq;
import com.spg.sgpco.service.RequestModel.RegisterReq;
import com.spg.sgpco.service.RequestModel.UpdateCustomerReq;
import com.spg.sgpco.service.RequestModel.UpdateProfileReq;
import com.spg.sgpco.service.RequestModel.VerifyReq;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("settings/all")
    Call<JsonObject> getAllSetting();

    @GET("customer/list")
    Call<JsonObject> getListCustomer();

    @HTTP(method = "DELETE", path = "customer/delete", hasBody = true)
    Call<JsonObject> deleteCustomer(@Body DeleteCustomerReq req);

    @POST("customer/create")
    Call<JsonObject> createCustomer(@Body CreateCustomerReq req);

    @PATCH("customer/update")
    Call<JsonObject> updateCustomer(@Body UpdateCustomerReq req);

    @GET("user/info/get")
    Call<JsonObject> getInfo();

    @PATCH("user/info/update")
    Call<JsonObject> updateProfile(@Body UpdateProfileReq req);

    @GET("post/list")
    Call<JsonObject> showContent();

    @GET("post/{item}")
    Call<JsonObject> getShowContentItem(@Path("item") String item);

    @GET("gallery/list")
    Call<JsonObject> galleryInfo();

    @GET("gallery/{item}")
    Call<JsonObject> getGalleryItem(@Path("item") String item);

    @POST("project/create")
    Call<JsonObject> createOrdinaryProject(@Body CreateOrdinaryProjectReq req);

    @GET("project/list")
    Call<JsonObject> getProjectList();

    @GET("project/pdf/{item}")
    Call<JsonObject> pdfItem(@Path("item") int item);

    @POST("project/create")
    Call<JsonObject> createThermostaticProject(@Body CreateThermostaticReq req);

    @GET("project/{item}")
    Call<JsonObject> getProjectUpdate(@Path("item") int item);

//    @POST("v2/home/GetNewVersion")
//    Call<JsonObject> VersionControl(@Body ForceUpdateReq req);


}
