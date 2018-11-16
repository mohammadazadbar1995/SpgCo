package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class SystemsItem implements Parcelable {
	private String code;
	private int id;
	private String title;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		dest.writeString(this.code);
		dest.writeInt(this.id);
		dest.writeString(this.title);
	}

	public SystemsItem() {
	}

	protected SystemsItem(Parcel in) {
		this.code = in.readString();
		this.id = in.readInt();
		this.title = in.readString();
	}

	public static final Parcelable.Creator<SystemsItem> CREATOR = new Parcelable.Creator<SystemsItem>() {
		@Override
		public SystemsItem createFromParcel(Parcel source) {
			return new SystemsItem(source);
		}

		@Override
		public SystemsItem[] newArray(int size) {
			return new SystemsItem[size];
		}
	};
}
