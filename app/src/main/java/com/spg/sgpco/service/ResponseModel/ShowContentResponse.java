package com.spg.sgpco.service.ResponseModel;

import java.util.ArrayList;

public class ShowContentResponse {
    private ArrayList<ShowContentItem> result;
    private boolean success;


    public ArrayList<ShowContentItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<ShowContentItem> result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}