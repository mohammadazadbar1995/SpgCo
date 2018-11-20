package com.spg.sgpco.service.ResponseModel;

import java.util.ArrayList;

public class GalleryResponse {
    private ArrayList<GalleryItem> result;
    private boolean success;

    public ArrayList<GalleryItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<GalleryItem> result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}