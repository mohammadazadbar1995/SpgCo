package com.spg.sgpco.service.ResponseModel;

public class GetShowItemResponse{
	private GetShowItem result;
	private boolean success;


	public GetShowItem getResult() {
		return result;
	}

	public void setResult(GetShowItem result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
