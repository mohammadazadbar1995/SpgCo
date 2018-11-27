package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class CitiesListItem implements Parcelable {

	private String city;
	private String id;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.city);
		dest.writeString(this.id);
		dest.writeString(this.type);
	}

	public CitiesListItem() {
	}

	protected CitiesListItem(Parcel in) {
		this.city = in.readString();
		this.id = in.readString();
		this.type = in.readString();
	}

	public static final Creator<CitiesListItem> CREATOR = new Creator<CitiesListItem>() {
		@Override
		public CitiesListItem createFromParcel(Parcel source) {
			return new CitiesListItem(source);
		}

		@Override
		public CitiesListItem[] newArray(int size) {
			return new CitiesListItem[size];
		}
	};
}
