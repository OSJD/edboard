package com.acc.tools.ed.integration.service;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.JsonResponse;
import com.acc.tools.ed.integration.dto.MasterEmployeeDetails;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.ReleasePlan;

public interface ProjectManagementService { 
	public ReferenceData addProject(ProjectForm project);
	public boolean isProjectExist(String projectName);
	public ReferenceData addRelease(ReleaseForm project);
	public int addEmployees(Collection<MasterEmployeeDetails> empDetails);
	public List<ReferenceData> getAllProjectIds();
	public List<ReferenceData> getProjectReleaseIds(String projectId);
	public ProjectForm getProjectPlanDetails(Integer releaseId, Integer projectId);
	public List<ReferenceData> editProject(ProjectForm project);
	public List<ReferenceData> editRelease(Integer releaseId, String editRelArti,String editRelStartDate, String editRelEndDate);
	public JsonResponse deleteProject(String projectId);
	public Integer deleteRelease(Integer releaseId);
	public ProjectForm getReleaseDetails(Integer releaseId);
	public int deleteReleasePlan(int releaseId);
	public List<ReferenceData> getProgramList();
	public List<ReferenceData> getResourceList();
	public List<ReferenceData> getPrjLeadList();
	public Map<String,String> getProjectDate(String projectId);
	public ComponentForm addComponent(Integer projectId,Integer phaseId,String componentName,String functionalDesc,String compStartDate,String compEndDate,Integer compResource,Integer releaseId,String workDesc);
	public List<MasterEmployeeDetails> getAllEmployees(); 
	public ComponentForm getComponentDetails(String componentName,Integer phaseId,Integer releaseId);
	public boolean isComponentAssignedToEmployee(Integer componentId,Integer empId);	
	public void addReleasePlan(ReleaseForm addReleaseForm, String empId,LocalDate dateStart, LocalDate tempDateStart, int dayFromIndex,int dayToIndex, boolean isLastWeek);
	public ProjectForm viewProject(int projectId);
	public int checkProjName(String projectName, int progId);
	public ReleasePlan buildReleasePlan(DateTime relDateStart,DateTime relDateEnd,Integer projId);
	public ReleasePlan fetchReleasePlan(DateTime relDateStart,DateTime relDateEnd,Integer releaseId,Integer projId);
	public List<ReferenceData> getResourcesByProjectId(Integer projectId);
	public void addReleasePlanUpdate(ReleaseForm editReleaseForm) throws ParseException;
}
