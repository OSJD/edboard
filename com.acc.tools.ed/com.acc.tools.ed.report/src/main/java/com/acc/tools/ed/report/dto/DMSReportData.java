package com.acc.tools.ed.report.dto;

import java.io.Serializable;

public class DMSReportData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String projectName;
	private String componentName;
	private String functionalDesc;
	private Integer phaseId;
	private String releaseName;
	private String programName;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	public Integer getPhaseId() {
		return phaseId;
	}
	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}

}
