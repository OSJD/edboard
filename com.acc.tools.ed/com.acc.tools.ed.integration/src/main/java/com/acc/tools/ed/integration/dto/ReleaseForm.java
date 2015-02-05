package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ReleaseForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int projectId;
	private int releaseId;
	private String projName;
	private String releaseName;
	private String releaseArtifacts;
	private String releaseStartDate;
	private String releaseEndDate;
	private String releaseDesc;
	private List<ComponentForm> components;
	private Map<String,List<Long>> resourcesAndHours;
	private List<ReferenceData> userData;
	private Map<String,List<ComponentForm>> teamTasks;
	
	public Map<String, List<Long>> getResourcesAndHours() {
		return resourcesAndHours;
	}
	public void setResourcesAndHours(Map<String, List<Long>> resourcesAndHours) {
		this.resourcesAndHours = resourcesAndHours;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	public String getReleaseArtifacts() {
		return releaseArtifacts;
	}
	public void setReleaseArtifacts(String releaseArtifacts) {
		this.releaseArtifacts = releaseArtifacts;
	}
	public String getReleaseStartDate() {
		return releaseStartDate;
	}
	public void setReleaseStartDate(String releaseStartDate) {
		this.releaseStartDate = releaseStartDate;
	}
	public String getReleaseEndDate() {
		return releaseEndDate;
	}
	public void setReleaseEndDate(String releaseEndDate) {
		this.releaseEndDate = releaseEndDate;
	}
	
	public List<ComponentForm> getComponents() {
		return components;
	}
	public void setComponents(List<ComponentForm> components) {
		this.components = components;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(int releaseId) {
		this.releaseId = releaseId;
	}
	public String getReleaseDesc() {
		return releaseDesc;
	}
	public void setReleaseDesc(String releaseDesc) {
		this.releaseDesc = releaseDesc;
	}
	public List<ReferenceData> getUserData() {
		return userData;
	}
	public void setUserData(List<ReferenceData> userData) {
		this.userData = userData;
	}
	public Map<String, List<ComponentForm>> getTeamTasks() {
		return teamTasks;
	}
	public void setTeamTasks(Map<String, List<ComponentForm>> teamTasks) {
		this.teamTasks = teamTasks;
	}

}
