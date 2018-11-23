package com.spg.sgpco.service.ResponseModel;

public class CreateOrdinaryProjectResponse{
	private OrdinaryProcetResult result;
	private boolean success;
	private String message;

	public OrdinaryProcetResult getResult() {
		return result;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
}
