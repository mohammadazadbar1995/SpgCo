package com.spg.sgpco.service.ResponseModel;

public class UpdateProjectResponse{
	private UpdateProjectResult result;
	private boolean success;


	public UpdateProjectResult getResult() {
		return result;
	}

	public void setResult(UpdateProjectResult result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
