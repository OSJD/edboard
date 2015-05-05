package com.acc.tools.ed.integration.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.LoginDao;
import com.acc.tools.ed.integration.dao.ProjectManagementDao;
import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.dto.JsonResponse;
import com.acc.tools.ed.integration.dto.MasterEmployeeDetails;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.ReleasePlan;
import com.acc.tools.ed.integration.dto.ReleaseWeek;
import com.acc.tools.ed.integration.dto.ResourceWeekWorkPlan;
import com.acc.tools.ed.integration.dto.ResourceWorkPlan;
import com.acc.tools.ed.integration.dto.WeekDates;
import com.acc.tools.ed.integration.service.ProjectManagementService;

@Service("projectManagementService")
public class ProjectManagementServiceImpl implements ProjectManagementService{
	
	private static final Logger LOG = LoggerFactory.getLogger(ProjectManagementServiceImpl.class);
	
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
	
	public boolean isProjectExist(String projectName){
		return projectManagementDao.isProjectExist(projectName);
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
		//projectManagementDao.addReleasePlan(releaseForm.getReleaseId(),empId,weekDateStart, weekDateEnd, weekHourSubList, weeklyPlannedHr, isLastWeek);
	}
	
	public ReleasePlan fetchReleasePlan(DateTime relDateStart,DateTime relDateEnd,Integer releaseId,Integer projId) {

		final Map<Integer,Map<DateTime,Integer>> resourceHoursMap=projectManagementDao.getReleasePlan(releaseId);
		final List<ReferenceData> resourceDetails = projectManagementDao.getProjectResourceDetails(projId);
		final Map<String,List<WeekDates>> vacationDetails=projectManagementDao.getVacationDetailsByEmployeeIds(resourceDetails);

		final ReleasePlan plan=new ReleasePlan();
		final List<ResourceWorkPlan> resourceWorkPlanList=new LinkedList<ResourceWorkPlan>();
		final Map<String,Integer> weakPlannedWorkHours=new LinkedHashMap<String,Integer>();
		plan.setWeakPlannedWorkHours(weakPlannedWorkHours);
		plan.setResourceWorkPlan(resourceWorkPlanList);
		DateTime dateStart=relDateStart;
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
				rebuildWeekWorkPlan(weekWorkPlanMap,dateStart,weekCount,resourceHoursMap.get(Integer.parseInt(employee.getId())),vacationDetails.get(employee.getId()),weakPlannedWorkHours);

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
	
	private void rebuildWeekWorkPlan(Map<String,List<ResourceWeekWorkPlan>> weeksWorkPlanMap,
			DateTime dateStart,int weekCount,Map<DateTime,Integer> planedDates,List<WeekDates> vacationDates,Map<String,Integer> weakPlannedWorkHours){
		
		Integer weekPlannedHours=0;
		if(weakPlannedWorkHours.size()>0 && weakPlannedWorkHours.containsKey("Week-"+weekCount)){
			weekPlannedHours=weakPlannedWorkHours.get("Week-"+weekCount);
		}
		
		if(weeksWorkPlanMap.size()>0 && weeksWorkPlanMap.containsKey("Week-"+weekCount)){
			final List<ResourceWeekWorkPlan> weekPlanList=weeksWorkPlanMap.get("Week-"+weekCount);
			ResourceWeekWorkPlan weekPlan=new ResourceWeekWorkPlan();
			weekPlan.setDay(dateStart.dayOfWeek().getAsShortText());
			weekPlan.setDate(dateStart.toString("MM/dd/yyyy"));
			if(planedDates!=null && !planedDates.isEmpty() && planedDates.containsKey(dateStart)){
				final int hours=planedDates.get(dateStart);
				if(hours>0){
					weekPlannedHours=weekPlannedHours+hours;
				}
				weekPlan.setHours(""+hours);	
			} else {
				vacationDaysToWeekPlanMapping(vacationDates,weekPlan,dateStart);
				final int hours=Integer.parseInt(weekPlan.getHours());
				if(hours>0){
					weekPlannedHours=weekPlannedHours+hours;
				}
			}
			
			weekPlanList.add(weekPlan);
		} else {
			final List<ResourceWeekWorkPlan> weekPlanList=new LinkedList<ResourceWeekWorkPlan>();
			ResourceWeekWorkPlan weekPlan=new ResourceWeekWorkPlan();
			weekPlan.setDay(dateStart.dayOfWeek().getAsShortText());
			weekPlan.setDate(dateStart.toString("MM/dd/yyyy"));
			if(planedDates!=null && !planedDates.isEmpty() && planedDates.containsKey(dateStart)){
				final int hours=planedDates.get(dateStart);
				if(hours>0){
					weekPlannedHours=weekPlannedHours+hours;
				}
				weekPlan.setHours(""+hours);	
			} else {
				vacationDaysToWeekPlanMapping(vacationDates,weekPlan,dateStart);
				final int hours=Integer.parseInt(weekPlan.getHours());
				if(hours>0){
					weekPlannedHours=weekPlannedHours+hours;
				}
			}
			weekPlanList.add(weekPlan);
			weeksWorkPlanMap.put("Week-"+weekCount, weekPlanList);
		}
		weakPlannedWorkHours.put("Week-"+weekCount, weekPlannedHours);
	}
	
	public ReleasePlan buildReleasePlan(DateTime relDateStart,DateTime relDateEnd,Integer projId) {
		

		final List<ReferenceData> resourceDetails = projectManagementDao.getProjectResourceDetails(projId);
		final Map<String,List<WeekDates>> vacationDetails=projectManagementDao.getVacationDetailsByEmployeeIds(resourceDetails);
		
		final ReleasePlan plan=new ReleasePlan();
		final List<ResourceWorkPlan> resourceWorkPlanList=new LinkedList<ResourceWorkPlan>();
		final Map<String,Integer> weakPlannedWorkHours=new LinkedHashMap<String,Integer>();
		plan.setWeakPlannedWorkHours(weakPlannedWorkHours);
		plan.setResourceWorkPlan(resourceWorkPlanList);
		DateTime dateStart=relDateStart;
		String tempCurrentWeek = dateStart.weekOfWeekyear().getAsShortText();
		int weekCount = 1;
		
		for(ReferenceData employee:resourceDetails){
			LOG.debug("Release plan for emp:{} Vacation detais:{}",employee.getId(),vacationDetails.get(employee.getId()));
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
				buildWeekWorkPlan(weekWorkPlanMap,dateStart,weekCount,vacationDetails.get(employee.getId()),weakPlannedWorkHours);

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
	
	private void vacationDaysToWeekPlanMapping(List<WeekDates> vacationDetails,ResourceWeekWorkPlan weekPlan,DateTime dateStart){
		if(vacationDetails!=null && !vacationDetails.isEmpty()){
			for(WeekDates vacationDates :vacationDetails){
				if(dateStart.getMillis() >= vacationDates.getWeekStartDate().getMillis() && dateStart.getMillis() <= vacationDates.getWeekEndDate().getMillis()){
					weekPlan.setHours("-1"); //-1 -> Vacation , -2 -> Public Holiday
				} else {
					if(weekPlan.getDay().equalsIgnoreCase("Sun") || weekPlan.getDay().equalsIgnoreCase("Sat")){
						weekPlan.setHours("0");
					} else {
						weekPlan.setHours("9");
					}
				}
			}
		} else {
			if(weekPlan.getDay().equalsIgnoreCase("Sun") || weekPlan.getDay().equalsIgnoreCase("Sat")){
				weekPlan.setHours("0");
			} else {
				weekPlan.setHours("9");
			}
		}
	}
	
	
	private void buildWeekWorkPlan(Map<String,List<ResourceWeekWorkPlan>> weeksWorkPlanMap,
			DateTime dateStart,int weekCount,List<WeekDates> vacationDetails,Map<String,Integer> weakPlannedWorkHours){
		
		Integer weekPlannedHours=0;
		if(weakPlannedWorkHours.size()>0 && weakPlannedWorkHours.containsKey("Week-"+weekCount)){
			weekPlannedHours=weakPlannedWorkHours.get("Week-"+weekCount);
		}
		
		if(weeksWorkPlanMap.size()>0 && weeksWorkPlanMap.containsKey("Week-"+weekCount)){
			final List<ResourceWeekWorkPlan> weekPlanList=weeksWorkPlanMap.get("Week-"+weekCount);
			ResourceWeekWorkPlan weekPlan=new ResourceWeekWorkPlan();
			weekPlan.setDay(dateStart.dayOfWeek().getAsShortText());
			weekPlan.setDate(dateStart.toString("MM/dd/yyyy"));
			vacationDaysToWeekPlanMapping(vacationDetails,weekPlan,dateStart);
			final int hours=Integer.parseInt(weekPlan.getHours());
			if(hours>0){
				weekPlannedHours=weekPlannedHours+hours;
			}
			weekPlanList.add(weekPlan);
		} else {
			final List<ResourceWeekWorkPlan> weekPlanList=new LinkedList<ResourceWeekWorkPlan>();
			ResourceWeekWorkPlan weekPlan=new ResourceWeekWorkPlan();
			weekPlan.setDay(dateStart.dayOfWeek().getAsShortText());
			weekPlan.setDate(dateStart.toString("MM/dd/yyyy"));
			vacationDaysToWeekPlanMapping(vacationDetails,weekPlan,dateStart);
			final int hours=Integer.parseInt(weekPlan.getHours());
			if(hours>0){
				weekPlannedHours=weekPlannedHours+hours;
			}
			weekPlanList.add(weekPlan);
			weeksWorkPlanMap.put("Week-"+weekCount, weekPlanList);
		}
		weakPlannedWorkHours.put("Week-"+weekCount, weekPlannedHours);
	}
	
	
	public ReferenceData addProject(ProjectForm project) {
		try {
			final boolean isProjectExist=projectManagementDao.isProjectExist(project.getProjectName());
			if(!isProjectExist){
			 return projectManagementDao.addProject(project);
			} else {
				ReferenceData errorData=new ReferenceData();
				errorData.setId("-2");
				errorData.setLabel("Project with name "+project.getProjectName()+"already exist!");
				return errorData;				
			}
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
	
	public JsonResponse deleteProject(String projectId) {
		final JsonResponse response=new JsonResponse();
		final int count=projectManagementDao.releaseCountByProjectId(projectId);
		if(count==0){
			projectManagementDao.deleteProject(projectId);
			response.setErrorCode(0);
			response.setMessage("Project deleted successfully!");
		} else {
			response.setErrorCode(1);
			response.setMessage("Delete all release before deleting project!");
		}
		return response;
	}
	
	public Integer deleteRelease(Integer releaseId) {
		return projectManagementDao.deleteRelease(releaseId);
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
	
	public List<ReferenceData> editProject(ProjectForm project) {
		try {
			 return projectManagementDao.editProject(project);
		}catch (Exception e)
		{
			LOG.error("Error updating Project table",e);
		}
		return null;
	}
	
	public List<ReferenceData> editRelease(Integer releaseId, String editRelArti,
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
			String compStartDate,String compEndDate,Integer compResource, Integer relaseId, String workDesc) {

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
	
	public ProjectForm viewProject(int projectId) {
		try {
			 return projectManagementDao.viewProject(projectId);
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
	public int deleteReleasePlan(int releaseId) {
		return projectManagementDao.deleteReleasePlan(releaseId);
	}
	
	public ProjectForm getReleaseDetails(Integer releaseId){
		return projectManagementDao.getReleaseData(releaseId);
	}
	
	public List<ReferenceData> getResourcesByProjectId(Integer projectId){
		return projectManagementDao.getResourcesByProjectId(projectId);
	}

	public void addReleasePlanUpdate(ReleaseForm editReleaseForm) throws ParseException {
		
		final Map<String,Map<String,ReleaseWeek>> resourceWeekHoursMap=new HashMap<String, Map<String,ReleaseWeek>>();
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		final LocalDate dateStart =  new LocalDate(sdf.parse(editReleaseForm.getReleaseStartDate()));
		
		for(String empId : editReleaseForm.getResourcesAndWorkHours().keySet()){
		    LocalDate tempDateEnd = new LocalDate();
			Map<String,Long> resourceWorkHoursMap=editReleaseForm.getResourcesAndWorkHours().get(empId);
				Map<String,ReleaseWeek> weekHoursMap=new HashMap<String, ReleaseWeek>();
				for(String date:resourceWorkHoursMap.keySet()){
					final String week=date.substring(0, date.length()-3);
					final String day=date.substring(date.length()-3,date.length());
					final Long hours=resourceWorkHoursMap.get(date);
					if(weekHoursMap.containsKey(week)){
						final ReleaseWeek releaseWeek=weekHoursMap.get(week);
						final Long[] days=releaseWeek.getHours();
						days[dayPosition(day)]=hours;
						Long weekPlannedHr=releaseWeek.getWeekPlannedHr();
						releaseWeek.setWeekPlannedHr(weekPlannedHr+hours);
					} else {
						final ReleaseWeek releaseWeek=new ReleaseWeek();
						final Long[] days=releaseWeek.getHours();
						int dayPosition=dayPosition(day);
						if(weekHoursMap.size()>=1){
							releaseWeek.setWeekStart(tempDateEnd.plusDays(1));
							tempDateEnd=tempDateEnd.plusDays(7-dayPosition);
							releaseWeek.setWeekEnd(tempDateEnd);
						}else{
							releaseWeek.setWeekStart(dateStart);
							tempDateEnd=dateStart.plusDays(6-dayPosition);
							releaseWeek.setWeekEnd(tempDateEnd);
						}
						days[dayPosition]=hours;
						Long weekPlannedHr=releaseWeek.getWeekPlannedHr();
						releaseWeek.setWeekPlannedHr(weekPlannedHr+hours);
						weekHoursMap.put(week, releaseWeek);
					}
				}
				resourceWeekHoursMap.put(empId, weekHoursMap);
		}
		
		projectManagementDao.addReleasePlan(editReleaseForm.getReleaseId(),resourceWeekHoursMap);
		
	}
	
	private int dayPosition(String day){
		int dayPosition=0;
		if(day.equalsIgnoreCase("Mon")){
			dayPosition=0;
		}else if(day.equalsIgnoreCase("Tue")){
			dayPosition=1;
		}else if(day.equalsIgnoreCase("Wed")){
			dayPosition=2;							
		}else if(day.equalsIgnoreCase("Thu")){
			dayPosition=3;
		}else if(day.equalsIgnoreCase("Fri")){
			dayPosition=4;
		}else if(day.equalsIgnoreCase("Sat")){
			dayPosition=5;
		}else if(day.equalsIgnoreCase("Sun")){
			dayPosition=6;
		}
		return dayPosition;
	}
	
}
