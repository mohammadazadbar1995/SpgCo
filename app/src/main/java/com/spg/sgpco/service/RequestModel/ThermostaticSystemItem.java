package com.spg.sgpco.service.RequestModel;

public class ThermostaticSystemItem {
    private int metr;
    private int type_of_space_id;
    private int floor_type_id;
    private int cold_area;

    private String type_of_space_title;
    private String floor_type_title;

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
}