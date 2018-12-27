package com.spg.sgpco.service.RequestModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.spg.sgpco.service.ResponseModel.SettingResultItem;

public class ThermostaticSystemItem implements Parcelable {
    private int metr;
    private int type_of_space_id;
    private int floor_type_id;
    private int cold_area;

    private String type_of_space_title;
    private String floor_type_title;

    private SettingResultItem type_of_space;
    private SettingResultItem floor_type;

    public int getMetr() {
        return metr;
    }

    public void setMetr(int metr) {
        this.metr = metr;
    }

    public int getType_of_space_id() {
        return type_of_space_id;
    }

    public void setType_of_space_id(int type_of_space_id) {
        this.type_of_space_id = type_of_space_id;
    }

    public int getFloor_type_id() {
        return floor_type_id;
    }

    public void setFloor_type_id(int floor_type_id) {
        this.floor_type_id = floor_type_id;
    }

    public int getCold_area() {
        return cold_area;
    }

    public void setCold_area(int cold_area) {
        this.cold_area = cold_area;
    }

    public String getType_of_space_title() {
        return type_of_space_title;
    }

    public void setType_of_space_title(String type_of_space_title) {
        this.type_of_space_title = type_of_space_title;
    }

    public String getFloor_type_title() {
        return floor_type_title;
    }

    public void setFloor_type_title(String floor_type_title) {
        this.floor_type_title = floor_type_title;
    }

    public SettingResultItem getType_of_space() {
        return type_of_space;
    }

    public void setType_of_space(SettingResultItem type_of_space) {
        this.type_of_space = type_of_space;
    }

    public SettingResultItem getFloor_type() {
        return floor_type;
    }

    public void setFloor_type(SettingResultItem floor_type) {
        this.floor_type = floor_type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.metr);
        dest.writeInt(this.type_of_space_id);
        dest.writeInt(this.floor_type_id);
        dest.writeInt(this.cold_area);
        dest.writeString(this.type_of_space_title);
        dest.writeString(this.floor_type_title);
        dest.writeParcelable(this.type_of_space, flags);
        dest.writeParcelable(this.floor_type, flags);
    }

    public ThermostaticSystemItem() {
    }

    protected ThermostaticSystemItem(Parcel in) {
        this.metr = in.readInt();
        this.type_of_space_id = in.readInt();
        this.floor_type_id = in.readInt();
        this.cold_area = in.readInt();
        this.type_of_space_title = in.readString();
        this.floor_type_title = in.readString();
        this.type_of_space = in.readParcelable(SettingResultItem.class.getClassLoader());
        this.floor_type = in.readParcelable(SettingResultItem.class.getClassLoader());
    }

    public static final Parcelable.Creator<ThermostaticSystemItem> CREATOR = new Parcelable.Creator<ThermostaticSystemItem>() {
        @Override
        public ThermostaticSystemItem createFromParcel(Parcel source) {
            return new ThermostaticSystemItem(source);
        }

        @Override
        public ThermostaticSystemItem[] newArray(int size) {
            return new ThermostaticSystemItem[size];
        }
    };
}
