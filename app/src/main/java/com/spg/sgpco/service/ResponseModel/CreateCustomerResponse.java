package com.spg.sgpco.service.ResponseModel;

public class CreateCustomerResponse{

	private CreateCustomerModel result;
	private boolean success;
	private String message;

	public CreateCustomerModel getResult() {
		return result;
	}

	public void setResult(CreateCustomerModel result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
