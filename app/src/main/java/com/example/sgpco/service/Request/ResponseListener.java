package com.example.sgpco.service.Request;

public interface ResponseListener<T> {

    void onGetErrore(String error);

    void onSuccess(T response);

}
