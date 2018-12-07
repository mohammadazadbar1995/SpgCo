package com.spg.sgpco.service.ResponseModel;

public class EnterCodeResponse {

    private boolean success;
    private String message;
    private Login result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Login getResult() {
        return result;
    }

    public void setResult(Login result) {
        this.result = result;
    }
}
