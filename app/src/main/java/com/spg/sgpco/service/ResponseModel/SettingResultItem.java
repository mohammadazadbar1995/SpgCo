package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class SettingResultItem implements Parcelable {
    private int id;
    private String title;
    private boolean zero;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isZero() {
        return zero;
    }

    public void setZero(boolean zero) {
        this.zero = zero;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeByte(this.zero ? (byte) 1 : (byte) 0);
    }

    public SettingResultItem() {
    }

    protected SettingResultItem(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.zero = in.readByte() != 0;
    }

    public static final Creator<SettingResultItem> CREATOR = new Creator<SettingResultItem>() {
        @Override
        public SettingResultItem createFromParcel(Parcel source) {
            return new SettingResultItem(source);
        }

        @Override
        public SettingResultItem[] newArray(int size) {
            return new SettingResultItem[size];
        }
    };
}
