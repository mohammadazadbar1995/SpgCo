package com.spg.sgpco.service.ResponseModel;

import java.util.ArrayList;
import java.util.List;

public class GetProjectListResponse{
	private ArrayList<ProjectListResultItem> result;
	private boolean success;

	public ArrayList<ProjectListResultItem> getResult() {
		return result;
	}

	public void setResult(ArrayList<ProjectListResultItem> result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}