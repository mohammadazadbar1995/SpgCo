package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class SettingAllResponse implements Parcelable {


    private SettingAllResult result;
    private boolean success;

    public SettingAllResult getResult() {
        return result;
    }

    public void setResult(SettingAllResult result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.result, flags);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
    }

    public SettingAllResponse() {
    }

    protected SettingAllResponse(Parcel in) {
        this.result = in.readParcelable(SettingAllResult.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Creator<SettingAllResponse> CREATOR = new Creator<SettingAllResponse>() {
        @Override
        public SettingAllResponse createFromParcel(Parcel source) {
            return new SettingAllResponse(source);
        }

        @Override
        public SettingAllResponse[] newArray(int size) {
            return new SettingAllResponse[size];
        }
    };
}
