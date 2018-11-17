package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerItem implements Parcelable {
    private String date;
    private String name;
    private int id;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.name);
        dest.writeInt(this.id);
    }

    public CustomerItem() {
    }

    protected CustomerItem(Parcel in) {
        this.date = in.readString();
        this.name = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<CustomerItem> CREATOR = new Parcelable.Creator<CustomerItem>() {
        @Override
        public CustomerItem createFromParcel(Parcel source) {
            return new CustomerItem(source);
        }

        @Override
        public CustomerItem[] newArray(int size) {
            return new CustomerItem[size];
        }
    };
}
