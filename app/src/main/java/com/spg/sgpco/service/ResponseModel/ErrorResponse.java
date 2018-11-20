package com.spg.sgpco.service.ResponseModel;

public class ErrorResponse{
	private String code;
	private ErrorCode data;
	private String message;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ErrorCode getData() {
		return data;
	}

	public void setData(ErrorCode data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
