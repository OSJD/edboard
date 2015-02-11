package com.acc.tools.ed.integration.service.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.LoginDao;
import com.acc.tools.ed.integration.dao.ProjectManagementDao;
import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.dto.EditProjectForm;
import com.acc.tools.ed.integration.dto.MasterEmployeeDetails;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.ReleasePlan;
import com.acc.tools.ed.integration.dto.ResourceWeekWorkPlan;
import com.acc.tools.ed.integration.dto.ResourceWorkPlan;
import com.acc.tools.ed.integration.service.ProjectManagementService;

@Service("projectManagementService")
public class ProjectManagementServiceImpl implements ProjectManagementService{
	
	@Autowired
	private ProjectManagementDao projectManagementDao;
	
	@Autowired
	private LoginDao loginDao;
	
	public List<ReferenceData> getAllProjectIds(){
		return projectManagementDao.getAllProjectIds();
	}
	public List<ReferenceData> getProjectReleaseIds(String projectId){
		return projectManagementDao.getProjectReleaseIds(projectId);
	}
    /**
     * This method splits resource hours week wise before saving the release plan week wise.
     */
	public void addReleasePlan(ReleaseForm releaseForm, String empId,
			LocalDate weekDateStart, LocalDate weekDateEnd, int dayFromIndex, int dayToIndex, boolean isLastWeek) {
		 Long weeklyPlannedHr = 0L;	
		 List<Long> hours = releaseForm.getResourcesAndHours().get(empId);
		 List<Long> weekHourSubList = hours.subList(dayFromIndex, dayToIndex);
		 for (Long hr : weekHourSubList) {
			 if(hr!=null)
			 weeklyPlannedHr = weeklyPlannedHr +hr;
		  }		
		projectManagementDao.addReleasePlan(releaseForm.getReleaseId(),empId,weekDateStart, weekDateEnd, weekHourSubList, weeklyPlannedHr, isLastWeek);
	}
	
	public ReleasePlan buildReleasePlan(LocalDate relDateStart,LocalDate relDateEnd,Integer projId) {
		

		List<ReferenceData> resourceDetails = projectManagementDao.getProjectResourceDetails(projId);
		
		ReleasePlan plan=new ReleasePlan();
		List<ResourceWorkPlan> resourceWorkPlanList=new LinkedList<ResourceWorkPlan>();
		plan.setResourceWorkPlan(resourceWorkPlanList);
		LocalDate dateStart=relDateStart;
		String tempCurrentWeek = dateStart.weekOfWeekyear().getAsShortText();
		int weekCount = 1;
		
		for(ReferenceData employee:resourceDetails){
			
			final ResourceWorkPlan resourceWorkPlan=new ResourceWorkPlan();
			resourceWorkPlan.setEmployeeId(employee.getId());
			resourceWorkPlan.setEmployeeName(employee.getLabel());
			final Map<String, List<ResourceWeekWorkPlan>> weekWorkPlanMap=resourceWorkPlan.getResourceWeekWorkPlan();
			resourceWorkPlan.setResourceWeekWorkPlan(weekWorkPlanMap);
			while(dateStart.isBefore(relDateEnd) || dateStart.equals(relDateEnd)){
				final String currentWeek=dateStart.weekOfWeekyear().getAsShortText();			
				if(!tempCurrentWeek.equalsIgnoreCase(currentWeek)){
					weekCount++;
					tempCurrentWeek=currentWeek;
				}
				buildWeekWorkPlan(weekWorkPlanMap,dateStart,weekCount,employee.getId());

			  dateStart = dateStart.plusDays(1);
			}
			dateStart=relDateStart;
			weekCount=1;
			tempCurrentWeek = dateStart.weekOfWeekyear().getAsShortText();
			resourceWorkPlanList.add(resourceWorkPlan);
			plan.setReleasePlanHeader(resourceWorkPlan);
		}
		
		
		return plan;
	}
	
	
	private void buildWeekWorkPlan(Map<String,List<ResourceWeekWorkPlan>> weeksWorkPlanMap,LocalDate dateStart,int weekCount,String empId){
		if(weeksWorkPlanMap.size()>0 && weeksWorkPlanMap.containsKey("Week-"+weekCount)){
			final List<ResourceWeekWorkPlan> weekPlanList=weeksWorkPlanMap.get("Week-"+weekCount);
			ResourceWeekWorkPlan weekPlan=new ResourceWeekWorkPlan();
			weekPlan.setDay(dateStart.dayOfWeek().getAsShortText());
			weekPlan.setDate(dateStart.toString("MM/dd/yyyy"));
			weekPlan.setHours(9);
			weekPlanList.add(weekPlan);
		} else {
			final List<ResourceWeekWorkPlan> weekPlanList=new LinkedList<ResourceWeekWorkPlan>();
			ResourceWeekWorkPlan weekPlan=new ResourceWeekWorkPlan();
			weekPlan.setDay(dateStart.dayOfWeek().getAsShortText());
			weekPlan.setDate(dateStart.toString("MM/dd/yyyy"));
			weekPlan.setHours(9);
			weekPlanList.add(weekPlan);
			weeksWorkPlanMap.put("Week-"+weekCount, weekPlanList);
		}
	}
	
	public ReferenceData addProject(ProjectForm project) {
		try {
			
			 return projectManagementDao.addProject(project);
		}catch (Exception e)
		{
			ReferenceData errorData=new ReferenceData();
			errorData.setId("-1");
			errorData.setLabel(e.getMessage());
			return errorData;
		}
	}
	
	public ReferenceData addRelease(ReleaseForm release){
		try {
			
			 return projectManagementDao.addRelease(release);
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	public Map<String,String> getProjectDate(String projectId) {
		try {
			
			 return projectManagementDao.getProjectDate(projectId);
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String deleteProject(String projectId) {
		projectManagementDao.deleteProject(projectId);
		return "";
	}
	
	public String deleteRelease(String releaseId) {
		projectManagementDao.deleteRelease(releaseId);
		return "";
	}
	
	public int addEmployees(Collection<MasterEmployeeDetails> empDetails) {
		for(MasterEmployeeDetails empDetail:empDetails){
			projectManagementDao.addEmployee(empDetail);
		}
		return 0;
	}
	
	public ProjectForm getProjectPlanDetails(Integer releaseId,Integer projectId) {
		try {
			
			 return projectManagementDao.getProjectPlanDetails(releaseId, projectId);
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<ReferenceData> editProject(String projectId, String editPrjDesc,
			String editPrjStartDate, String editPrjEndDate) {
		try {
			
			 return projectManagementDao.editProject(projectId, editPrjDesc, editPrjStartDate, editPrjEndDate);
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ReferenceData> editRelease(String releaseId, String editRelArti,
			String editRelStartDate, String editRelEndDate) {
		try {
			
			 return projectManagementDao.editRelease(releaseId, editRelArti, editRelStartDate, editRelEndDate);
			 
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ReferenceData> getProgramList() {
		
		try {
			
			 return projectManagementDao.getProgramList();
			 
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<ReferenceData> getResourceList() {
		try {
			 return projectManagementDao.getResourceList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ReferenceData> getPrjLeadList() {
		try {
			 return projectManagementDao.getPrjLeadList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ComponentForm addComponent(Integer projectId,Integer phaseId,String componentName,String functionalDesc,
			String compStartDate,String compEndDate,String compResource, Integer relaseId, String workDesc) {

		try {
			ComponentForm component= projectManagementDao.addComponent(projectId,phaseId,componentName, functionalDesc, compStartDate, compEndDate, compResource,relaseId,workDesc);
			EDBUser user=loginDao.getEmployeeById(compResource);
			component.setResourceName(user.getEnterpriseId());
			component.setWorkDesc(workDesc);
			return component;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<MasterEmployeeDetails> getAllEmployees(){
		return projectManagementDao.getAllEmployees();
	}
	public ComponentForm getComponentDetails(String componentName,
			Integer phaseId, Integer releaseId) {
		try {
			 return projectManagementDao.getComponentDetails(phaseId, componentName, releaseId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isComponentAssignedToEmployee(Integer componentId,Integer empId){
		return projectManagementDao.isComponentAssignedToEmployee(componentId, empId);
	}
	
	public List<EditProjectForm> editProject(int projectId) {
		try {
			 return projectManagementDao.editProject(projectId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public int checkProjName(String projectName, int progId) {
		try {
			 return projectManagementDao.checkProjName(projectName, progId);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	
}
