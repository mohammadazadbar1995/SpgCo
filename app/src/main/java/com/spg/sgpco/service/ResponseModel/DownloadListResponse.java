package com.spg.sgpco.service.ResponseModel;

import java.util.ArrayList;

public class DownloadListResponse{
	private ArrayList<DownloadListItem> result;
	private boolean success;

	public ArrayList<DownloadListItem> getResult() {
		return result;
	}

	public void setResult(ArrayList<DownloadListItem> result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}