package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginResponse implements Parcelable {

    private boolean success;
    private Login result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Login getResult() {
        return result;
    }

    public void setResult(Login result) {
        this.result = result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.result, flags);
    }

    public LoginResponse() {
    }

    protected LoginResponse(Parcel in) {
        this.success = in.readByte() != 0;
        this.result = in.readParcelable(Login.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoginResponse> CREATOR = new Parcelable.Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel source) {
            return new LoginResponse(source);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };
}
