package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class ProjectForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int existingProgram;
	private int newProgramId;
	private String newProgramName;
	private int projectId;
	private String projectName;
	
	private DateTime endDate;
	private DateTime startDate;
	private List<String> phases;
	private String projectDescription;
	private List<String> stringResources;
	private List<String> selectedResources;
	private List<ReferenceData> resources;
	private List<ReleaseForm> releases;
	private String projectLead;
	private String addResource;
	
	
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public DateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}
	public DateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public int getExistingProgram() {
		return existingProgram;
	}
	public void setExistingProgram(int existingProgram) {
		this.existingProgram = existingProgram;
	}
	public int getNewProgramId() {
		return newProgramId;
	}
	public void setNewProgramId(int newProgramId) {
		this.newProgramId = newProgramId;
	}
	public String getNewProgramName() {
		return newProgramName;
	}
	public void setNewProgramName(String newProgramName) {
		this.newProgramName = newProgramName;
	}
	public List<ReleaseForm> getReleases() {
		return releases;
	}
	public void setReleases(List<ReleaseForm> releases) {
		this.releases = releases;
	}
	public String getProjectLead() {
		return projectLead;
	}
	public void setProjectLead(String projectLead) {
		this.projectLead = projectLead;
	}
	public List<ReferenceData> getResources() {
		if(resources==null){
			resources=new ArrayList<ReferenceData>();
			for(String id:selectedResources){
				ReferenceData refData=new ReferenceData();
				refData.setId(id);
				resources.add(refData);
			}
			
		}
		return resources;
	}
	public void setResources(List<ReferenceData> resources) {
		this.resources = resources;
	}
	public List<String> getStringResources() {
		return stringResources;
	}
	public void setStringResources(List<String> stringResources) {
		this.stringResources = stringResources;
	}
	public List<String> getPhases() {
		return phases;
	}
	public void setPhases(List<String> phases) {
		this.phases = phases;
	}
	public String getAddResource() {
		return addResource;
	}
	public void setAddResource(String addResource) {
		this.addResource = addResource;
	}
	public List<String> getSelectedResources() {
		return selectedResources;
	}
	public void setSelectedResources(List<String> selectedResources) {
		this.selectedResources = selectedResources;
	}
}
