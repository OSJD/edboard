package com.acc.tools.ed.integration.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.EditProjectForm;
import com.acc.tools.ed.integration.dto.MasterEmployeeDetails;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.ReleasePlan;

public interface ProjectManagementService { 
	public ReferenceData addProject(ProjectForm project);
	public ReferenceData addRelease(ReleaseForm project);
	public int addEmployees(Collection<MasterEmployeeDetails> empDetails);
	public List<ReferenceData> getAllProjectIds();
	public List<ReferenceData> getProjectReleaseIds(String projectId);
	public ProjectForm getProjectPlanDetails(Integer releaseId, Integer projectId);
	public List<ReferenceData> editProject(String projectId,String editPrjDesc,String editPrjStartDate,String editPrjEndDate);
	public List<ReferenceData> editRelease(String releaseId, String editRelArti,String editRelStartDate, String editRelEndDate);
	public String deleteProject(String projectId);
	public String deleteRelease(String releaseId);
	public List<ReferenceData> getProgramList();
	public List<ReferenceData> getResourceList();
	public List<ReferenceData> getPrjLeadList();
	public Map<String,String> getProjectDate(String projectId);
	public ComponentForm addComponent(Integer projectId,Integer phaseId,String componentName,String functionalDesc,String compStartDate,String compEndDate,String compResource,Integer releaseId,String workDesc);
	public List<MasterEmployeeDetails> getAllEmployees(); 
	public ComponentForm getComponentDetails(String componentName,Integer phaseId,Integer releaseId);
	public boolean isComponentAssignedToEmployee(Integer componentId,Integer empId);	
	public void addReleasePlan(ReleaseForm addReleaseForm, String empId,LocalDate dateStart, LocalDate tempDateStart, int dayFromIndex,int dayToIndex, boolean isLastWeek);
	public List<EditProjectForm> editProject(int projectId);
	public int checkProjName(String projectName, int progId);
	public ReleasePlan buildReleasePlan(DateTime relDateStart,DateTime relDateEnd,Integer projId);
	public ReleasePlan fetchReleasePlan(DateTime relDateStart,DateTime relDateEnd,Integer releaseId);
}
