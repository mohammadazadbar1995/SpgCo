package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SettingAllResult implements Parcelable {

    private ArrayList<ListCitiesItem> list_cities;
    private ArrayList<SettingResultItem> list_floor;
    private ArrayList<SettingResultItem> list_project_type;
    private ArrayList<SystemsItem> systems;
    private ArrayList<SettingResultItem> heat_source;
    private ArrayList<SettingResultItem> type_of_space;


    public ArrayList<ListCitiesItem> getList_cities() {
        return list_cities;
    }

    public void setList_cities(ArrayList<ListCitiesItem> list_cities) {
        this.list_cities = list_cities;
    }

    public ArrayList<SettingResultItem> getList_floor() {
        return list_floor;
    }

    public void setList_floor(ArrayList<SettingResultItem> list_floor) {
        this.list_floor = list_floor;
    }

    public ArrayList<SettingResultItem> getList_project_type() {
        return list_project_type;
    }

    public void setList_project_type(ArrayList<SettingResultItem> list_project_type) {
        this.list_project_type = list_project_type;
    }

    public ArrayList<SystemsItem> getSystems() {
        return systems;
    }

    public void setSystems(ArrayList<SystemsItem> systems) {
        this.systems = systems;
    }

    public ArrayList<SettingResultItem> getHeat_source() {
        return heat_source;
    }

    public void setHeat_source(ArrayList<SettingResultItem> heat_source) {
        this.heat_source = heat_source;
    }

    public ArrayList<SettingResultItem> getType_of_space() {
        return type_of_space;
    }

    public void setType_of_space(ArrayList<SettingResultItem> type_of_space) {
        this.type_of_space = type_of_space;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.list_cities);
        dest.writeList(this.list_floor);
        dest.writeList(this.list_project_type);
        dest.writeList(this.systems);
        dest.writeList(this.heat_source);
        dest.writeList(this.type_of_space);
    }

    public SettingAllResult() {
    }

    protected SettingAllResult(Parcel in) {
        this.list_cities = new ArrayList<ListCitiesItem>();
        in.readList(this.list_cities, ListCitiesItem.class.getClassLoader());
        this.list_floor = new ArrayList<SettingResultItem>();
        in.readList(this.list_floor, SettingResultItem.class.getClassLoader());
        this.list_project_type = new ArrayList<SettingResultItem>();
        in.readList(this.list_project_type, SettingResultItem.class.getClassLoader());
        this.systems = new ArrayList<SystemsItem>();
        in.readList(this.systems, SystemsItem.class.getClassLoader());
        this.heat_source = new ArrayList<SettingResultItem>();
        in.readList(this.heat_source, SettingResultItem.class.getClassLoader());
        this.type_of_space = new ArrayList<SettingResultItem>();
        in.readList(this.type_of_space, SettingResultItem.class.getClassLoader());
    }

    public static final Parcelable.Creator<SettingAllResult> CREATOR = new Parcelable.Creator<SettingAllResult>() {
        @Override
        public SettingAllResult createFromParcel(Parcel source) {
            return new SettingAllResult(source);
        }

        @Override
        public SettingAllResult[] newArray(int size) {
            return new SettingAllResult[size];
        }
    };
}