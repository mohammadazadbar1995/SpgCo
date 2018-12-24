package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.spg.sgpco.service.RequestModel.ThermostaticSystemItem;

import java.util.ArrayList;

public class UpdateProjectResult implements Parcelable {
	private String date;
	private SettingResultItem project_type;
	private OrdinarySystem ordinary_system;
	private ArrayList<ThermostaticSystemItem> thermostatic_system;
	private SettingResultItem heat_source;
	private CitiesListItem city;
	private int state_id;
	private SystemsItem systems_type;
	private String content;
	private int id;
	private String title;
	private CustomerItem customer;


	public ArrayList<ThermostaticSystemItem> getThermostatic_system() {
		return thermostatic_system;
	}

	public void setThermostatic_system(ArrayList<ThermostaticSystemItem> thermostatic_system) {
		this.thermostatic_system = thermostatic_system;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public SettingResultItem getProject_type() {
		return project_type;
	}

	public void setProject_type(SettingResultItem project_type) {
		this.project_type = project_type;
	}

	public OrdinarySystem getOrdinary_system() {
		return ordinary_system;
	}

	public void setOrdinary_system(OrdinarySystem ordinary_system) {
		this.ordinary_system = ordinary_system;
	}

	public SettingResultItem getHeat_source() {
		return heat_source;
	}

	public void setHeat_source(SettingResultItem heat_source) {
		this.heat_source = heat_source;
	}

	public CitiesListItem getCity() {
		return city;
	}

	public void setCity(CitiesListItem city) {
		this.city = city;
	}

	public SystemsItem getSystems_type() {
		return systems_type;
	}

	public void setSystems_type(SystemsItem systems_type) {
		this.systems_type = systems_type;
	}

	public String getDescription() {
		return content;
	}

	public void setDescription(String description) {
		this.content = description;
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

	public CustomerItem getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerItem customer) {
		this.customer = customer;
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
		dest.writeString(this.date);
		dest.writeParcelable(this.project_type, flags);
		dest.writeParcelable(this.ordinary_system, flags);
		dest.writeList(this.thermostatic_system);
		dest.writeParcelable(this.heat_source, flags);
		dest.writeParcelable(this.city, flags);
		dest.writeInt(this.state_id);
		dest.writeParcelable(this.systems_type, flags);
		dest.writeString(this.content);
		dest.writeInt(this.id);
		dest.writeString(this.title);
		dest.writeParcelable(this.customer, flags);
	}

	public UpdateProjectResult() {
	}

	protected UpdateProjectResult(Parcel in) {
		this.date = in.readString();
		this.project_type = in.readParcelable(SettingResultItem.class.getClassLoader());
		this.ordinary_system = in.readParcelable(OrdinarySystem.class.getClassLoader());
		this.thermostatic_system = new ArrayList<ThermostaticSystemItem>();
		in.readList(this.thermostatic_system, ThermostaticSystemItem.class.getClassLoader());
		this.heat_source = in.readParcelable(SettingResultItem.class.getClassLoader());
		this.city = in.readParcelable(CitiesListItem.class.getClassLoader());
		this.state_id = in.readInt();
		this.systems_type = in.readParcelable(SystemsItem.class.getClassLoader());
		this.content = in.readString();
		this.id = in.readInt();
		this.title = in.readString();
		this.customer = in.readParcelable(CustomerItem.class.getClassLoader());
	}

	public static final Creator<UpdateProjectResult> CREATOR = new Creator<UpdateProjectResult>() {
		@Override
		public UpdateProjectResult createFromParcel(Parcel source) {
			return new UpdateProjectResult(source);
		}

		@Override
		public UpdateProjectResult[] newArray(int size) {
			return new UpdateProjectResult[size];
		}
	};
}
