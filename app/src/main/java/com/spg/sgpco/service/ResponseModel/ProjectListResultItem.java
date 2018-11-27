package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectListResultItem implements Parcelable {
	private String date;
	private String city;
	private String systems_type;
	private String name;
	private String description;
	private int id;
	private String customer;
	private String link;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSystems_type() {
		return systems_type;
	}

	public void setSystems_type(String systems_type) {
		this.systems_type = systems_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.date);
		dest.writeString(this.city);
		dest.writeString(this.systems_type);
		dest.writeString(this.name);
		dest.writeString(this.description);
		dest.writeInt(this.id);
		dest.writeString(this.customer);
	}

	public ProjectListResultItem() {
	}

	protected ProjectListResultItem(Parcel in) {
		this.date = in.readString();
		this.city = in.readString();
		this.systems_type = in.readString();
		this.name = in.readString();
		this.description = in.readString();
		this.id = in.readInt();
		this.customer = in.readString();
	}

	public static final Parcelable.Creator<ProjectListResultItem> CREATOR = new Parcelable.Creator<ProjectListResultItem>() {
		@Override
		public ProjectListResultItem createFromParcel(Parcel source) {
			return new ProjectListResultItem(source);
		}

		@Override
		public ProjectListResultItem[] newArray(int size) {
			return new ProjectListResultItem[size];
		}
	};
}
