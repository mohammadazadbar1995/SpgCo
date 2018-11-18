package com.spg.sgpco.service.ResponseModel;

public class GetInfoResponse{
	private boolean success;
	private GetInfo reuslt;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public GetInfo getReuslt() {
		return reuslt;
	}

	public void setReuslt(GetInfo reuslt) {
		this.reuslt = reuslt;
	}
}
