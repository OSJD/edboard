package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.List;

public class EditProjectForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int existingProgramEdit;
	private int newProgramIdEdit;
	private String newProgramNameEdit;
	private int projectIdEdit;
	private String projectNameEdit;
	
	private String endDateEdit;
	private String startDateEdit;
	private List<String> phasesEdit;
	private String projectDescriptionEdit;
	private List<String> stringResourcesEdit;
	private List<String> selectedResourcesEdit;
//	private List<ReferenceData> resourcesEdit;
	private List<ReleaseForm> releasesEdit;
	private String projectLeadEdit;
	private String addResourceEdit;
	
	
	
	
	public int getExistingProgramEdit() {
		return existingProgramEdit;
	}
	public void setExistingProgramEdit(int existingProgramEdit) {
		this.existingProgramEdit = existingProgramEdit;
	}
	public int getNewProgramIdEdit() {
		return newProgramIdEdit;
	}
	public void setNewProgramIdEdit(int newProgramIdEdit) {
		this.newProgramIdEdit = newProgramIdEdit;
	}
	public String getNewProgramNameEdit() {
		return newProgramNameEdit;
	}
	public void setNewProgramNameEdit(String newProgramNameEdit) {
		this.newProgramNameEdit = newProgramNameEdit;
	}
	public int getProjectIdEdit() {
		return projectIdEdit;
	}
	public void setProjectIdEdit(int projectIdEdit) {
		this.projectIdEdit = projectIdEdit;
	}
	public String getProjectNameEdit() {
		return projectNameEdit;
	}
	public void setProjectNameEdit(String projectNameEdit) {
		this.projectNameEdit = projectNameEdit;
	}
	public String getEndDateEdit() {
		return endDateEdit;
	}
	public void setEndDateEdit(String endDateEdit) {
		this.endDateEdit = endDateEdit;
	}
	public String getStartDateEdit() {
		return startDateEdit;
	}
	public void setStartDateEdit(String startDateEdit) {
		this.startDateEdit = startDateEdit;
	}
	public List<String> getPhasesEdit() {
		return phasesEdit;
	}
	public void setPhasesEdit(List<String> phasesEdit) {
		this.phasesEdit = phasesEdit;
	}
	public String getProjectDescriptionEdit() {
		return projectDescriptionEdit;
	}
	public void setProjectDescriptionEdit(String projectDescriptionEdit) {
		this.projectDescriptionEdit = projectDescriptionEdit;
	}
	public List<String> getStringResourcesEdit() {
		return stringResourcesEdit;
	}
	public void setStringResourcesEdit(List<String> stringResourcesEdit) {
		this.stringResourcesEdit = stringResourcesEdit;
	}
	public List<String> getSelectedResourcesEdit() {
		return selectedResourcesEdit;
	}
	public void setSelectedResourcesEdit(List<String> selectedResourcesEdit) {
		this.selectedResourcesEdit = selectedResourcesEdit;
	}
	//check abu
	/*public List<ReferenceData> getResourcesEdit() {
		if(resourcesEdit==null){
			resourcesEdit=new ArrayList<ReferenceData>();
			for(String id:selectedResourcesEdit){
				ReferenceData refData=new ReferenceData();
				refData.setId(id);
				resourcesEdit.add(refData);
			}
			
		}
		return resourcesEdit;
	}*/
	/*public void setResourcesEdit(List<ReferenceData> resourcesEdit) {
		this.resourcesEdit = resourcesEdit;
	}*/
	public List<ReleaseForm> getReleasesEdit() {
		return releasesEdit;
	}
	public void setReleasesEdit(List<ReleaseForm> releasesEdit) {
		this.releasesEdit = releasesEdit;
	}
	public String getProjectLeadEdit() {
		return projectLeadEdit;
	}
	public void setProjectLeadEdit(String projectLeadEdit) {
		this.projectLeadEdit = projectLeadEdit;
	}
	public String getAddResourceEdit() {
		return addResourceEdit;
	}
	public void setAddResourceEdit(String addResourceEdit) {
		this.addResourceEdit = addResourceEdit;
	}
}
