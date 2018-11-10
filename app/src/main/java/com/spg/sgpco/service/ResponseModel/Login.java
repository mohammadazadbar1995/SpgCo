package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class Login implements Parcelable {


	private String user_email;
	private String user_nicename;
	private String user_display_name;
	private String token;

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_nicename() {
		return user_nicename;
	}

	public void setUser_nicename(String user_nicename) {
		this.user_nicename = user_nicename;
	}

	public String getUser_display_name() {
		return user_display_name;
	}

	public void setUser_display_name(String user_display_name) {
		this.user_display_name = user_display_name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.user_email);
		dest.writeString(this.user_nicename);
		dest.writeString(this.user_display_name);
		dest.writeString(this.token);
	}

	public Login() {
	}

	protected Login(Parcel in) {
		this.user_email = in.readString();
		this.user_nicename = in.readString();
		this.user_display_name = in.readString();
		this.token = in.readString();
	}

	public static final Parcelable.Creator<Login> CREATOR = new Parcelable.Creator<Login>() {
		@Override
		public Login createFromParcel(Parcel source) {
			return new Login(source);
		}

		@Override
		public Login[] newArray(int size) {
			return new Login[size];
		}
	};
}
