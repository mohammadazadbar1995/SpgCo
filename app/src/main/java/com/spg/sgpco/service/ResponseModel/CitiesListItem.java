package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class CitiesListItem implements Parcelable {

	private String city;
	private String id;

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
	}

	public CitiesListItem() {
	}

	protected CitiesListItem(Parcel in) {
		this.city = in.readString();
		this.id = in.readString();
	}

	public static final Parcelable.Creator<CitiesListItem> CREATOR = new Parcelable.Creator<CitiesListItem>() {
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
