package com.acc.tools.ed.integration.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.ProjectManagementDao;
import com.acc.tools.ed.integration.dto.EditProjectForm;
import com.acc.tools.ed.integration.dto.MasterEmployeeDetails;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.ReleasePlan;
import com.acc.tools.ed.integration.service.ProjectManagementService;
import com.acc.tools.ed.integration.util.CalendarEnum;

@Service("projectManagementService")
public class ProjectManagementServiceImpl implements ProjectManagementService{
	
	@Autowired
	private ProjectManagementDao projectManagementDao;
	
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
	
	public ReleasePlan createReleasePlan(String releaseStartDate,String releaseEndDate, Integer projId){
		
		List<ReferenceData> resourceDetails = projectManagementDao.getProjectResourceDetails(projId);
		
		LocalDate relDateStart = new LocalDate(releaseStartDate);
		LocalDate dateStart = relDateStart;
		System.out.println(CalendarEnum.getMonthName(dateStart.getMonthOfYear()));
		LocalDate relDateEnd = new LocalDate(releaseEndDate);	
		LocalDate dateEnd = relDateEnd;
		
	    ReleasePlan releasePlan = new ReleasePlan();	
		
		 calculateAndSetWeeksAndDays(dateStart, dateEnd, releasePlan);
		 dateStart = relDateStart;dateEnd = relDateEnd;
		 calculateAndSetMonthsNoOfDays(dateStart, dateEnd, releasePlan);
		 dateStart = relDateStart;dateEnd = relDateEnd;
		 calculateAndSetResourceHours(dateStart, dateEnd,resourceDetails, releasePlan);
		 dateStart = relDateStart;dateEnd = relDateEnd;
		 calculateAndSetTotalHoursPerWeek(dateStart, dateEnd,resourceDetails, releasePlan);
		
		
		
		
		return releasePlan;
		
		
		
	}

	private void calculateAndSetTotalHoursPerWeek(LocalDate dateStart,
			LocalDate dateEnd, List<ReferenceData> resourceDetails, ReleasePlan releasePlan) {
		 // List<Long> weeklyTotalHoursList = new ArrayList<Long>();
		  List<String> weekTotHourStrList = new ArrayList<String>();	  
		  Long weeklyTotalHours = 0L;
		  String weekOfYear=dateStart.weekOfWeekyear().getAsShortText();		
		  Long noOfdaysInWeek = 0L;
		  					  
			while(dateStart.isBefore(dateEnd) || dateStart.equals(dateEnd)){
				
		      for (int i = 0; i < resourceDetails.size(); i++) {
				
				if(weekOfYear.equalsIgnoreCase(dateStart.weekOfWeekyear().getAsShortText())){
			           if(!StringUtils.containsIgnoreCase("SunSat", dateStart.dayOfWeek().getAsShortText())){
			        	   weeklyTotalHours = weeklyTotalHours+9L; 
			        	   noOfdaysInWeek++;
			                   }
			           else{
			        	   noOfdaysInWeek++;
			           }
				           }
				else{	
					//weeklyTotalHoursList.add(weeklyTotalHours);
					weekTotHourStrList.add(String.valueOf(noOfdaysInWeek/resourceDetails.size())+"~"+weeklyTotalHours);
					noOfdaysInWeek = 1L;
					weeklyTotalHours = 9L;  
					weekOfYear=dateStart.weekOfWeekyear().getAsShortText();
				    }						
			
			    
		          }	
		      dateStart=dateStart.plusDays(1);
			 }
			weekTotHourStrList.add(String.valueOf(noOfdaysInWeek/resourceDetails.size())+"~"+weeklyTotalHours);
			//weeklyTotalHoursList.add(weeklyTotalHours);
			releasePlan.setWeeklyTotalHours(weekTotHourStrList);
			
		
	}
	private void calculateAndSetResourceHours(LocalDate dateStart,
			LocalDate dateEnd, List<ReferenceData> resourceDetails, ReleasePlan releasePlan) {
		Map<ReferenceData,List<Long>> resourcesAndHours = new LinkedHashMap<ReferenceData, List<Long>>();		
		LocalDate tempDateStart;
		LocalDate tempDateEnd;
		
		String weekOfYear=dateStart.weekOfWeekyear().getAsShortText();
		

		

		
		for (ReferenceData resource : resourceDetails) {	
			List<Long> tempHours = new ArrayList<Long>();
			tempDateStart = dateStart;
			tempDateEnd = dateEnd;
			weekOfYear = tempDateStart.weekOfWeekyear().getAsShortText();
		while(tempDateStart.isBefore(tempDateEnd) || tempDateStart.equals(tempDateEnd)){
				
				if(weekOfYear.equalsIgnoreCase(tempDateStart.weekOfWeekyear().getAsShortText())){
			           if(!StringUtils.containsIgnoreCase("SunSat", tempDateStart.dayOfWeek().getAsShortText())){
			        	   tempHours.add(9L); 				                  
			                   }
			           else{
			        	   tempHours.add(0L);
			           }
				           }
				else{	
					tempHours.add(9L); 
					weekOfYear=tempDateStart.weekOfWeekyear().getAsShortText();
				    }						        
				tempDateStart = tempDateStart.plusDays(1);
		         }
		resourcesAndHours.put(resource, new ArrayList<Long>(tempHours));
		tempHours.clear();
		tempHours = null;
		}
		
		releasePlan.setResourcesAndHours(resourcesAndHours);
		
	}
	private void calculateAndSetMonthsNoOfDays(LocalDate dateStart,
			LocalDate dateEnd, ReleasePlan releasePlan) {
		Map<String,Long> monthsNoOfDays = new LinkedHashMap<String, Long>();
		String month = dateStart.monthOfYear().getAsShortText();
		int year = dateStart.getYear();
		Long days = 0L;
		
		
		while(dateStart.isBefore(dateEnd) || dateStart.equals(dateEnd)){
			if(month.equalsIgnoreCase(dateStart.monthOfYear().getAsShortText())){				
				days++;
				}
				else{
					monthsNoOfDays.put(month+"/"+String.valueOf(year),days);
					month = dateStart.monthOfYear().getAsShortText();
					year = dateStart.getYear();
					days = 1L;
				}
				  dateStart = dateStart.plusDays(1);
		}
		monthsNoOfDays.put(month+"/"+String.valueOf(year),days);
		releasePlan.setMonthsNoOfDays(monthsNoOfDays);
	}
	
	
	public void calculateAndSetWeeksAndDays(
			LocalDate dateStart, LocalDate dateEnd, ReleasePlan releasePlan) {
		Map<String,List<String>> weeksAndDays = new LinkedHashMap<String, List<String>>();
		List<String> days = new ArrayList<String>();
		String weekOfYear=dateStart.weekOfWeekyear().getAsShortText();
		Long weekCount = 0L;
		
		while(dateStart.isBefore(dateEnd) || dateStart.equals(dateEnd)){
			if(weekOfYear.equalsIgnoreCase(dateStart.weekOfWeekyear().getAsShortText())){
			weekOfYear =dateStart.weekOfWeekyear().getAsShortText();
			days.add(dateStart.dayOfWeek().getAsShortText()+"-"+dateStart.dayOfMonth().getAsShortText());
			}
			else{
				List<String> actualDays= new ArrayList<String>(days);				
				weeksAndDays.put("Week-"+(++weekCount),actualDays);
				weekOfYear = dateStart.weekOfWeekyear().getAsShortText();
				days.clear();
				days.add(dateStart.dayOfWeek().getAsShortText()+"-"+dateStart.dayOfMonth().getAsShortText());
			}
			  dateStart = dateStart.plusDays(1);
		}
		weeksAndDays.put("Week-"+(++weekCount),days);
	/*	for(Map.Entry<String, List<String>> week:weeksAndDays.entrySet()) {
			System.out.println("inside weeksAndDays:");
			System.out.println("Key:" + week.getKey());
			for (Iterator<String> it = week.getValue().iterator(); it.hasNext();) {
				System.out.println("days: " + it.next());

			}
		}
		*/
	releasePlan.setWeeksAndDays(weeksAndDays);
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
	
	public ProjectForm addComponent(Integer projectId,Integer phaseId,String componentName,String functionalDesc,
			String compStartDate,String compEndDate,String compResource, Integer relaseId, String workDesc) {

		try {
			 return projectManagementDao.addComponent(projectId,phaseId,componentName, functionalDesc, compStartDate, compEndDate, compResource,relaseId,workDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<MasterEmployeeDetails> getAllEmployees(){
		return projectManagementDao.getAllEmployees();
	}
	public List<Object> getComponentDetails(String componentName,
			Integer phaseId, Integer releaseId) {
		try {
			 return projectManagementDao.getComponentDetails(phaseId, componentName, releaseId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
