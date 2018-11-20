package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class ShowContentItem implements Parcelable {
	private String post_title;
	private String image;
	private String post_date;
	private int ID;
	private String post_excerpt;


	public String getPost_title() {
		return post_title;
	}

	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPost_date() {
		return post_date;
	}

	public void setPost_date(String post_date) {
		this.post_date = post_date;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getPost_excerpt() {
		return post_excerpt;
	}

	public void setPost_excerpt(String post_excerpt) {
		this.post_excerpt = post_excerpt;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.post_title);
		dest.writeString(this.image);
		dest.writeString(this.post_date);
		dest.writeInt(this.ID);
		dest.writeString(this.post_excerpt);
	}

	public ShowContentItem() {
	}

	protected ShowContentItem(Parcel in) {
		this.post_title = in.readString();
		this.image = in.readString();
		this.post_date = in.readString();
		this.ID = in.readInt();
		this.post_excerpt = in.readString();
	}

	public static final Parcelable.Creator<ShowContentItem> CREATOR = new Parcelable.Creator<ShowContentItem>() {
		@Override
		public ShowContentItem createFromParcel(Parcel source) {
			return new ShowContentItem(source);
		}

		@Override
		public ShowContentItem[] newArray(int size) {
			return new ShowContentItem[size];
		}
	};
}
