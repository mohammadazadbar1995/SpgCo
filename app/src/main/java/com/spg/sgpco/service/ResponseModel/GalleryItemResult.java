package com.spg.sgpco.service.ResponseModel;

import java.util.ArrayList;

public class GalleryItemResult {
    private GalleryItemPost post;
    private ArrayList<GalleryItemList> gallery;

    public GalleryItemPost getPost() {
        return post;
    }

    public void setPost(GalleryItemPost post) {
        this.post = post;
    }

    public ArrayList<GalleryItemList> getGallery() {
        return gallery;
    }

    public void setGallery(ArrayList<GalleryItemList> gallery) {
        this.gallery = gallery;
    }
}