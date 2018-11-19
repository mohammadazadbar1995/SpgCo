package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class GalleryItemList implements Parcelable {
    private String image;
    private String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        dest.writeString(this.image);
        dest.writeString(this.title);
    }

    public GalleryItemList() {
    }

    protected GalleryItemList(Parcel in) {
        this.image = in.readString();
        this.title = in.readString();
    }

    public static final Creator<GalleryItemList> CREATOR = new Creator<GalleryItemList>() {
        @Override
        public GalleryItemList createFromParcel(Parcel source) {
            return new GalleryItemList(source);
        }

        @Override
        public GalleryItemList[] newArray(int size) {
            return new GalleryItemList[size];
        }
    };
}
