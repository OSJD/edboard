package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResourceWorkPlan implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String employeeId;
	private String employeeName;
	private Map<String,List<ResourceWeekWorkPlan>> resourceWeekWorkPlan=new LinkedHashMap<String, List<ResourceWeekWorkPlan>>();
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Map<String, List<ResourceWeekWorkPlan>> getResourceWeekWorkPlan() {
		return resourceWeekWorkPlan;
	}
	public void setResourceWeekWorkPlan(
			Map<String, List<ResourceWeekWorkPlan>> resourceWeekWorkPlan) {
		this.resourceWeekWorkPlan = resourceWeekWorkPlan;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

}
