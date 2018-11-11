package com.spg.sgpco.service.RequestModel;

import android.os.Parcel;
import android.os.Parcelable;

public class ForgetPassReq implements Parcelable {


    private String phonenumber;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phonenumber);
    }

    public ForgetPassReq() {
    }

    protected ForgetPassReq(Parcel in) {
        this.phonenumber = in.readString();
    }

    public static final Parcelable.Creator<ForgetPassReq> CREATOR = new Parcelable.Creator<ForgetPassReq>() {
        @Override
        public ForgetPassReq createFromParcel(Parcel source) {
            return new ForgetPassReq(source);
        }

        @Override
        public ForgetPassReq[] newArray(int size) {
            return new ForgetPassReq[size];
        }
    };
}
