package com.acc.tools.ed.integration.dao;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.EditProjectForm;
import com.acc.tools.ed.integration.dto.MasterEmployeeDetails;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;

public interface ProjectManagementDao {
	
	public ReferenceData addProject(ProjectForm project);
	public ReferenceData addRelease(ReleaseForm release);
	public int addEmployee(MasterEmployeeDetails empDetail);
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
	public ComponentForm addComponent(Integer projectId,Integer phaseId,String componentName,String functionalDesc,String compStartDate,String compEndDate,String compResource,Integer relaseId,String workDesc);
	public ComponentForm getComponentDetails(Integer phaseId, String componentName,Integer releaseId);
	public boolean isComponentAssignedToEmployee(Integer componentId,Integer empId);
	public List<MasterEmployeeDetails> getAllEmployees();
	public List<ReferenceData> getProjectResourceDetails(Integer projectId);
	public void addReleasePlan(int releaseId, String empId, LocalDate weekDateStart, LocalDate weekDateEnd, List<Long> weekHourList, Long weeklyPlannedHr, boolean isLastWeek);
	
	public List<EditProjectForm> editProject(int projectId);
	public int checkProjName(String projectName, int progId);
}
