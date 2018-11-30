package com.spg.sgpco.service.RequestModel;

public class UpdateOrdinaryProjectReq {
	private int project_id;
	private String title;
	private int customer_id;
	private int city_id;
	private int project_type_id;
	private int heat_source_id;
	private OrdinarySystem ordinary_system;
	private int systems_type_id;
	private String content;


	public int getHeat_source_id() {
		return heat_source_id;
	}

	public void setHeat_source_id(int heat_source_id) {
		this.heat_source_id = heat_source_id;
	}

	public OrdinarySystem getOrdinary_system() {
		return ordinary_system;
	}

	public void setOrdinary_system(OrdinarySystem ordinary_system) {
		this.ordinary_system = ordinary_system;
	}

	public int getSystems_type_id() {
		return systems_type_id;
	}

	public void setSystems_type_id(int systems_type_id) {
		this.systems_type_id = systems_type_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}

	public int getProject_type_id() {
		return project_type_id;
	}

	public void setProject_type_id(int project_type_id) {
		this.project_type_id = project_type_id;
	}
}
