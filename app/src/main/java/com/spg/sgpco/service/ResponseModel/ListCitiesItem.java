package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ListCitiesItem implements Parcelable {
	private ArrayList<CitiesListItem> cities_list;
	private String state;
	private int state_id;

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

	public int getState_id() {
		return state_id;
	}

	public void setState_id(int state_id) {
		this.state_id = state_id;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(this.cities_list);
		dest.writeString(this.state);
		dest.writeInt(this.state_id);
	}

	public ListCitiesItem() {
	}

	protected ListCitiesItem(Parcel in) {
		this.cities_list = in.createTypedArrayList(CitiesListItem.CREATOR);
		this.state = in.readString();
		this.state_id = in.readInt();
	}

	public static final Creator<ListCitiesItem> CREATOR = new Creator<ListCitiesItem>() {
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