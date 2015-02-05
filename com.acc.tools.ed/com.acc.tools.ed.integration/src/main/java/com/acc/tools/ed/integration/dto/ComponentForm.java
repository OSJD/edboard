package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.List;

public class ComponentForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int componentId;
	private int projectId;
	private String projectName;
	private String componentName;
	private String functionalDesc;
	private String startDate;
	private String endDate;
	private int resourceId;
	private String resourceName;
	private String workDesc;
	private String phaseId;
	private List<TaskForm> taskFormList;

	public int getComponentId() {
		return componentId;
	}

	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getFunctionalDesc() {
		return functionalDesc;
	}

	public void setFunctionalDesc(String functionalDesc) {
		this.functionalDesc = functionalDesc;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public List<TaskForm> getTaskFormList() {
		return taskFormList;
	}

	public void setTaskFormList(List<TaskForm> taskFormList) {
		this.taskFormList = taskFormList;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getWorkDesc() {
		return workDesc;
	}

	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
	}

	public String getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}

}
