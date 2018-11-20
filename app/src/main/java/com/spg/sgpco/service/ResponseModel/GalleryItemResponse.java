package com.spg.sgpco.service.ResponseModel;

public class GalleryItemResponse {
    private GalleryItemResult result;
    private boolean success;

    public GalleryItemResult getResult() {
        return result;
    }

    public void setResult(GalleryItemResult result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
