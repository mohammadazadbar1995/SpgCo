package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class SettingResultItem implements Parcelable {
    private int id;
    private String title;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
    }

    public SettingResultItem() {
    }

    protected SettingResultItem(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<SettingResultItem> CREATOR = new Parcelable.Creator<SettingResultItem>() {
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
