package com.acc.tools.ed.integration.service;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.acc.tools.ed.integration.dto.Capability;
import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.JsonResponse;
import com.acc.tools.ed.integration.dto.Level;
import com.acc.tools.ed.integration.dto.MasterEmployeeDetails;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.ReleasePlan;
import com.acc.tools.ed.integration.dto.ResourceDetails;
import com.acc.tools.ed.integration.dto.Skill;


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
	public List<String> getSkill();
	public List<String> getLevel();
	public List<String> getCapability();
	public ReferenceData addResource(ResourceDetails resourceData);
	public ReferenceData updateResource(ResourceDetails resourceData);
	public ReferenceData addCapability(Capability capabilityDetails);
	public ReferenceData addLevel(Level levelDetails);
	public ReferenceData addSkill(Skill skillDetails);
	public String getEmployeeName(String empID);
	public List<ReferenceData> getProgramProjectIds(String programId);
	public ReferenceData editCapability(Capability capabilityDetails);
	public ReferenceData deleteCapability(String capabilityName);
	public ReferenceData editLevel(Level levelDetails);
	public ReferenceData deleteLevel(String levelName);
	public List<String> getSkill(String capabilityName);
	public ReferenceData editSkill(Skill skillDetails);
	public ReferenceData deleteSkill(String capabilityName, String skillName);
	public ResourceDetails getEmployeeDetails(String existingEmpId);
	public ReferenceData deleteResource(String existingEmpId);
}
