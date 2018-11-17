package com.spg.sgpco.service.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GetListCustomerResponse implements Parcelable {
	private ArrayList<CustomerItem> result;
	private boolean success;

	public ArrayList<CustomerItem> getResult() {
		return result;
	}

	public void setResult(ArrayList<CustomerItem> result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(this.result);
		dest.writeByte(this.success ? (byte) 1 : (byte) 0);
	}

	public GetListCustomerResponse() {
	}

	protected GetListCustomerResponse(Parcel in) {
		this.result = in.createTypedArrayList(CustomerItem.CREATOR);
		this.success = in.readByte() != 0;
	}

	public static final Creator<GetListCustomerResponse> CREATOR = new Creator<GetListCustomerResponse>() {
		@Override
		public GetListCustomerResponse createFromParcel(Parcel source) {
			return new GetListCustomerResponse(source);
		}

		@Override
		public GetListCustomerResponse[] newArray(int size) {
			return new GetListCustomerResponse[size];
		}
	};
}