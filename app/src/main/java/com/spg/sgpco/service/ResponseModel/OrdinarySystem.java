package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class OrdinarySystem implements Parcelable {
	private String metr;
	private SettingResultItem floor_type;
	private String cold_area;

	public String getMetr() {
		return metr;
	}

	public void setMetr(String metr) {
		this.metr = metr;
	}

	public SettingResultItem getFloor_type() {
		return floor_type;
	}

	public void setFloor_type(SettingResultItem floor_type) {
		this.floor_type = floor_type;
	}

	public String getCold_area() {
		return cold_area;
	}

	public void setCold_area(String cold_area) {
		this.cold_area = cold_area;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.metr);
		dest.writeParcelable(this.floor_type, flags);
		dest.writeString(this.cold_area);
	}

	public OrdinarySystem() {
	}

	protected OrdinarySystem(Parcel in) {
		this.metr = in.readString();
		this.floor_type = in.readParcelable(SettingResultItem.class.getClassLoader());
		this.cold_area = in.readString();
	}

	public static final Parcelable.Creator<OrdinarySystem> CREATOR = new Parcelable.Creator<OrdinarySystem>() {
		@Override
		public OrdinarySystem createFromParcel(Parcel source) {
			return new OrdinarySystem(source);
		}

		@Override
		public OrdinarySystem[] newArray(int size) {
			return new OrdinarySystem[size];
		}
	};
}
