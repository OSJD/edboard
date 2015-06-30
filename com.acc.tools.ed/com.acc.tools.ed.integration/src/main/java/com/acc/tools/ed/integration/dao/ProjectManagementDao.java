package com.acc.tools.ed.integration.dao;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.acc.tools.ed.integration.dto.Capability;
import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.Level;
import com.acc.tools.ed.integration.dto.MasterEmployeeDetails;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.ReleaseWeek;
import com.acc.tools.ed.integration.dto.ResourceDetails;
import com.acc.tools.ed.integration.dto.Skill;
import com.acc.tools.ed.integration.dto.WeekDates;

public interface ProjectManagementDao {
	
	public ReferenceData addProject(ProjectForm project);
	public boolean isProjectExist(String projectName);
	public ReferenceData addRelease(ReleaseForm release);
	public int addEmployee(MasterEmployeeDetails empDetail);
	public List<ReferenceData> getAllProjectIds();
	public List<ReferenceData> getProjectReleaseIds(String projectId);
	public ProjectForm getProjectPlanDetails(Integer releaseId, Integer projectId);
	public List<ReferenceData> editProject(ProjectForm project);
	public List<ReferenceData> editRelease(Integer releaseId, String editRelArti,String editRelStartDate, String editRelEndDate);
	public String deleteProject(String projectId);
	public Integer deleteRelease(Integer releaseId);
	public List<ReferenceData> getProgramList();
	public List<ReferenceData> getResourceList();
	public List<ReferenceData> getPrjLeadList();
	public Map<String,String> getProjectDate(String projectId);
	public ComponentForm addComponent(Integer projectId,Integer phaseId,String componentName,String functionalDesc,String compStartDate,String compEndDate,Integer compResource,Integer relaseId,String workDesc);
	public ComponentForm getComponentDetails(Integer phaseId, String componentName,Integer releaseId);
	public boolean isComponentAssignedToEmployee(Integer componentId,Integer empId);
	public List<MasterEmployeeDetails> getAllEmployees();
	public List<ReferenceData> getProjectResourceDetails(Integer projectId);
	public void addReleasePlan(int releaseId,Map<String,Map<String,ReleaseWeek>> resourceWeekHoursMap);
	public int deleteReleasePlan(int releaseId);
	public Map<Integer,Map<DateTime,Integer>> getReleasePlan(Integer releaseId);
	public ProjectForm viewProject(int projectId);
	public int checkProjName(String projectName, int progId);
	public Map<String,List<WeekDates>> getVacationDetailsByEmployeeIds(List<ReferenceData> employeeIds);
	public ProjectForm getReleaseData(Integer releaseId);
	public int releaseCountByProjectId(String projectId);
	public List<ReferenceData> getResourcesByProjectId(Integer projectId);
	public List<String> getSkill();
	public List<String> getLevel();
	public List<String> getCapability();
	public ReferenceData addResource(ResourceDetails resourceDetails);
	public boolean isCapabilityExist(String capabilityName,String capabilitySpecialty);
	public ReferenceData addCapability(Capability capabilityDetails);
	public boolean isLevelExist(String levelName);
	public ReferenceData addLevel(Level levelDetails);
	public boolean isSkillExist(String skillCategory,String skillName);
	public ReferenceData addSkill(Skill skillDetails);
	public ReferenceData updateResource(ResourceDetails resourceDetails);
	public String getEmployeeName(String empID);
}
