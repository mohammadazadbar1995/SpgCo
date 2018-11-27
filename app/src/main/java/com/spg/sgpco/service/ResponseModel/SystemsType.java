package com.spg.sgpco.service.ResponseModel;

public class SystemsType{
	private String code;
	private int id;
	private String title;

	@Override
 	public String toString(){
		return 
			"SystemsType{" + 
			"code = '" + code + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}
