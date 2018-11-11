package com.spg.sgpco.service.RequestModel;

public class LoginWithCodeForgetPassReq {


    private String phonenumber;
    private String code;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
