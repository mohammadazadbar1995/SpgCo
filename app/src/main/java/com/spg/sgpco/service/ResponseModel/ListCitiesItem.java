package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ListCitiesItem implements Parcelable {
	private ArrayList<CitiesListItem> cities_list;
	private String state;

	public ArrayList<CitiesListItem> getCities_list() {
		return cities_list;
	}

	public void setCities_list(ArrayList<CitiesListItem> cities_list) {
		this.cities_list = cities_list;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(this.cities_list);
		dest.writeString(this.state);
	}

	public ListCitiesItem() {
	}

	protected ListCitiesItem(Parcel in) {
		this.cities_list = new ArrayList<CitiesListItem>();
		in.readList(this.cities_list, CitiesListItem.class.getClassLoader());
		this.state = in.readString();
	}

	public static final Parcelable.Creator<ListCitiesItem> CREATOR = new Parcelable.Creator<ListCitiesItem>() {
		@Override
		public ListCitiesItem createFromParcel(Parcel source) {
			return new ListCitiesItem(source);
		}

		@Override
		public ListCitiesItem[] newArray(int size) {
			return new ListCitiesItem[size];
		}
	};
}