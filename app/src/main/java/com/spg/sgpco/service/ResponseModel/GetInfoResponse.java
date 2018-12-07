package com.spg.sgpco.service.ResponseModel;

public class GetInfoResponse{
	private boolean success;
	private GetInfo result;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public GetInfo getReuslt() {
		return result;
	}

	public void setReuslt(GetInfo reuslt) {
		this.result = reuslt;
	}
}
