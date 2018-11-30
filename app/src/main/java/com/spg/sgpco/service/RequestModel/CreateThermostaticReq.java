package com.spg.sgpco.service.RequestModel;

import java.util.ArrayList;

public class CreateThermostaticReq {
    private int project_id;
    private int project_type_id;
    private int heat_source_id;
    private int systems_type_id;
    private String title;
    private String name;
    private String content;
    private ArrayList<ThermostaticSystemItem> thermostatic_system;
    private int customer_id;
    private int city_id;

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProject_type_id() {
        return project_type_id;
    }

    public void setProject_type_id(int project_type_id) {
        this.project_type_id = project_type_id;
    }

    public int getHeat_source_id() {
        return heat_source_id;
    }

    public void setHeat_source_id(int heat_source_id) {
        this.heat_source_id = heat_source_id;
    }

    public int getSystems_type_id() {
        return systems_type_id;
    }

    public void setSystems_type_id(int systems_type_id) {
        this.systems_type_id = systems_type_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ThermostaticSystemItem> getThermostatic_system() {
        return thermostatic_system;
    }

    public void setThermostatic_system(ArrayList<ThermostaticSystemItem> thermostatic_system) {
        this.thermostatic_system = thermostatic_system;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }
}