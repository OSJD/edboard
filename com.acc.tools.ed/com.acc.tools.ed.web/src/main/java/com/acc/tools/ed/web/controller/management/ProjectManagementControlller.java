package com.acc.tools.ed.web.controller.management;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.JsonResponse;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.ReleasePlan;
import com.acc.tools.ed.integration.dto.ReleaseWeek;
import com.acc.tools.ed.web.controller.common.AbstractEdbBaseController;

@Controller
@SessionAttributes({ "edbUser" }) 
public class ProjectManagementControlller extends AbstractEdbBaseController { 

	private static final Logger LOG = LoggerFactory
			.getLogger(ProjectManagementControlller.class);


	@RequestMapping(value = "/projectPlan.do")
	public String projectPlan(Model model) {
		model.addAttribute("addProjectForm", new ProjectForm());
		model.addAttribute("addReleaseForm", new ReleaseForm());		
		model.addAttribute("editProjectForm", new ProjectForm());
		
		return "/projectmanagement/projectPlan";
	}

	@RequestMapping(value = "/addProject.do")
	public String addProject(
			@ModelAttribute("addProjectForm") ProjectForm addProjectForm,
			@ModelAttribute("projectList") List<ReferenceData> projectList,
			Model model) {
		model.addAttribute("editProjectForm", new ProjectForm());
		LOG.debug("Project Name:{} | Id:{}", addProjectForm.getProjectName(),addProjectForm.getProjectId());
		LOG.debug("Existing Program Id:{}", addProjectForm.getExistingProgram());
		LOG.debug("New Program Name:{} Id:{}", addProjectForm.getNewProgramName(),addProjectForm.getNewProgramId());
		final ReferenceData newProject = getProjectManagementService().addProject(addProjectForm);
		projectList.add(newProject);		
		model.addAttribute("projectList", projectList);
		model.addAttribute("programList",getProgramList());
		LOG.debug("Add Project retruned --> Project Id: {} | Project Name:{}", newProject.getId(),newProject.getLabel());
		return "/projectmanagement/index";
	}
	
	
	@RequestMapping(value = "/fetchInitialProjectSetupDetails.do")
	public @ResponseBody Map<String,List<ReferenceData>> fetchInitialProjectSetupDetails(){
		Map<String,List<ReferenceData>> initialProjSetupDeteails=new HashMap<String,List<ReferenceData>>(); 
		initialProjSetupDeteails.put("programList",getProgramList());
		initialProjSetupDeteails.put("resourceList",getResourceList());
		initialProjSetupDeteails.put("projectLeadList",getProjectLeadList());
		return initialProjSetupDeteails;
	}
	
	@RequestMapping(value = "/viewProjectDetails.do")
	public @ResponseBody Map<String,Object> viewProjectDetails(@RequestParam("projectId") int projectId, Model model){
		
		LOG.debug("View Project details of project Id:{}",projectId);
		final Map<String,Object> initialProjSetupDeteails=new HashMap<String,Object>(); 
		initialProjSetupDeteails.put("programList",getProgramList());
		initialProjSetupDeteails.put("resourceList",getResourceList());
		initialProjSetupDeteails.put("projectLeadList",getProjectLeadList());
		ProjectForm projectDetails = getProjectManagementService().viewProject(projectId);
		
		LOG.debug("Project details :{}",projectDetails.toString());
		
		initialProjSetupDeteails.put("projectDetails", projectDetails);
		return initialProjSetupDeteails;
	}
	
	@RequestMapping(value = "/checkProjName.do")
	public @ResponseBody int checkProjName(@RequestParam("projectName") String projectName, @RequestParam("progId") int progId,Model model){
		int flag = getProjectManagementService().checkProjName(projectName, progId);
		return flag;
	}
	
	@RequestMapping(value = "/getPrjDate.do")
	public @ResponseBody Map<String,String> getPrjDate(
			@RequestParam("projectId") String projectId,
			Model model) {
		
		LOG.debug("Project Name:[{}]",projectId);
		Map<String,String> projDates=getProjectManagementService().getProjectDate(projectId);
		LOG.debug("Project: [{}] Dates:[{}]",projectId,projDates);
		return projDates;
	}
	
	@RequestMapping(value = "/getReleaseDetails.do")
	public @ResponseBody Map<String,Object> getReleaseDetails(
			@RequestParam("releaseId") Integer releaseId,
			Model model) {
		
		LOG.debug("Fetch Release details for  Id:[{}]",releaseId);
		ProjectForm projectForm = getProjectManagementService().getReleaseDetails(releaseId);
		Map<String,Object> jsonMap=new HashMap<String, Object>();
		jsonMap.put("projectName", projectForm.getProjectName());
		jsonMap.put("startDate", projectForm.getStartDate());
		jsonMap.put("endDate", projectForm.getEndDate());
		jsonMap.put("release", projectForm.getReleases().get(0));
		return jsonMap;
	}
	
	@RequestMapping(value = "/createReleasePlan.do")
	public String createReleasePlan(
			@RequestParam("releaseStartDate") String releaseStartDate,
			@RequestParam("releaseEndDate") String releaseEndDate,
			@RequestParam("projId") String projId,
			Model model) throws ParseException {
		
		LOG.debug("Release Start Date:{} End Date:{}",releaseStartDate,releaseEndDate);
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy");
			
		DateTime stDate =  format.parseDateTime(releaseStartDate);
		DateTime etDate =  format.parseDateTime(releaseEndDate);

		ReleasePlan releasePlan=getProjectManagementService().buildReleasePlan(stDate,etDate, Integer.valueOf(projId));
		model.addAttribute("releasePlan",releasePlan);
		return "/projectmanagement/releasePlan";
	}	
	
	@RequestMapping(value = "/getReleasePlan.do")
	public String getReleasePlan(
			@RequestParam("releaseStartDate") String releaseStartDate,
			@RequestParam("releaseEndDate") String releaseEndDate,
			@RequestParam("releaseId") Integer releaseId,
			@RequestParam("projectId") Integer projectId,
			Model model) throws ParseException {
		
		LOG.debug("Release Start Date:{} End Date:{}",releaseStartDate,releaseEndDate);
		DateTimeFormatter format = DateTimeFormat.forPattern("MM/dd/yyyy");
			
		DateTime stDate =  format.parseDateTime(releaseStartDate);
		DateTime etDate =  format.parseDateTime(releaseEndDate);

		ReleasePlan releasePlan=getProjectManagementService().fetchReleasePlan(stDate,etDate, releaseId,projectId);
		model.addAttribute("releasePlan",releasePlan);
		return "/projectmanagement/releasePlan";
	}	
	
	
	
	@RequestMapping(value = "/addRelease.do")
	public @ResponseBody ReferenceData addRelease(
			@RequestBody  ReleaseForm addReleaseForm,
			Model model) throws ParseException {
		LOG.debug("Project Id:{} | Release Id:[{}]",
				addReleaseForm.getProjectId(), addReleaseForm.getReleaseId());
		final ReferenceData refData = getProjectManagementService().addRelease(addReleaseForm);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		LocalDate dateStart =  new LocalDate(sdf.parse(addReleaseForm.getReleaseStartDate()));
		LocalDate dateEnd =  new LocalDate(sdf.parse(addReleaseForm.getReleaseEndDate()));	
		LocalDate releaseStrtDate = dateStart;
		LocalDate tempDateStart = dateStart;
		LocalDate tempDateEnd = dateEnd;	
		String weekOfYear=dateStart.weekOfWeekyear().getAsShortText();
		int dayFromIndex;
		int dayToIndex;				
		
	
			
	    for ( String empId : addReleaseForm.getResourcesAndHours().keySet()) {
	    	dayFromIndex = 0;
	    	dayToIndex = 0;
	    	tempDateStart = releaseStrtDate;
	    	dateStart = releaseStrtDate;
	    	weekOfYear=dateStart.weekOfWeekyear().getAsShortText();
		  	while(tempDateStart.isBefore(tempDateEnd) || tempDateStart.equals(tempDateEnd)){
											
				if(weekOfYear.equalsIgnoreCase(tempDateStart.weekOfWeekyear().getAsShortText()))										
					 dayToIndex++;	
				else{										 
				     getProjectManagementService().addReleasePlan(addReleaseForm, empId,dateStart,tempDateStart.minusDays(1),dayFromIndex,dayToIndex,false);				    
					 weekOfYear=tempDateStart.weekOfWeekyear().getAsShortText();
					 dateStart = tempDateStart;
					 dayFromIndex = dayToIndex++;
				    }
				 
				tempDateStart = tempDateStart.plusDays(1);
		         }			  					 
		     getProjectManagementService().addReleasePlan(addReleaseForm,empId,dateStart,tempDateStart.minusDays(1),dayFromIndex,dayToIndex,true);     
		     
		}
		
		
		return refData;
	}
	
	
	@RequestMapping(value = "/editRelease.do")
	public @ResponseBody ReferenceData editRelease(
			@RequestBody  ReleaseForm editReleaseForm,
			Model model) throws ParseException {
		final ReferenceData refData = new ReferenceData();
		refData.setId(""+editReleaseForm.getReleaseId());
		refData.setLabel(editReleaseForm.getReleaseName());
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		LocalDate dateStart =  new LocalDate(sdf.parse(editReleaseForm.getReleaseStartDate()));
		LocalDate dateEnd =  new LocalDate(sdf.parse(editReleaseForm.getReleaseEndDate()));	
		LOG.debug("Edit Release Id:[{}] Start Date:[{}] End Date:[{}]",new Object[]{editReleaseForm.getReleaseId(),editReleaseForm.getReleaseStartDate(),editReleaseForm.getReleaseEndDate()});
/*		LocalDate releaseStrtDate = dateStart;
		LocalDate tempDateStart = dateStart;
		LocalDate tempDateEnd = dateEnd;	
		String weekOfYear=dateStart.weekOfWeekyear().getAsShortText();
		int dayFromIndex;
		int dayToIndex;	*/		
		Map<String,Map<String,ReleaseWeek>> resourceWeekHoursMap=new HashMap<String, Map<String,ReleaseWeek>>();
		
		getProjectManagementService().editRelease(editReleaseForm.getReleaseId(),editReleaseForm.getReleaseArtifacts(),editReleaseForm.getReleaseStartDate(),editReleaseForm.getReleaseEndDate());
		int status=getProjectManagementService().deleteReleasePlan(editReleaseForm.getReleaseId());
		//if(status>0){

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
						} else {
							final ReleaseWeek releaseWeek=new ReleaseWeek();
							System.out.println("tempDateEnd:"+tempDateEnd.toString("MM/dd/yyyy"));
							if(weekHoursMap.size()>=1){
								releaseWeek.setWeekStart(tempDateEnd.plusDays(1));	
							}else{
								releaseWeek.setWeekStart(dateStart);
							}
							releaseWeek.setWeekStart(dateStart);
							final Long[] days=releaseWeek.getHours();
							int dayPosition=dayPosition(day);
							tempDateEnd=dateStart.plusDays(6-dayPosition);
							releaseWeek.setWeekEnd(tempDateEnd);
							days[dayPosition]=hours;
							weekHoursMap.put(week, releaseWeek);
						}
					}
					resourceWeekHoursMap.put(empId, weekHoursMap);
			}
			for(String empId:resourceWeekHoursMap.keySet()){
				System.out.println("Emp:"+empId);
				Map<String, ReleaseWeek> empHoursMap=resourceWeekHoursMap.get(empId);
				for(String week:empHoursMap.keySet()){
					ReleaseWeek releaseWeek=empHoursMap.get(week);
					System.out.println("\tWeek:"+week +" | Start:"+releaseWeek.getWeekStart().toString("MM/dd/yyyy")+"| End:"+releaseWeek.getWeekEnd().toString("MM/dd/yyyy"));	
					System.out.print("\tHours: ");
					for(Long hours:releaseWeek.getHours()){
						if(hours!=null)
						System.out.print(hours.longValue()+" | ");
						else
							System.out.print("0 | ");
					}
					System.out.println();
					
				}
			}

		//}
			
/*		    for ( String empId : editReleaseForm.getResourcesAndHours().keySet()) {
		    	dayFromIndex = 0;
		    	dayToIndex = 0;
		    	tempDateStart = releaseStrtDate;
		    	dateStart = releaseStrtDate;
		    	weekOfYear=dateStart.weekOfWeekyear().getAsShortText();
			  	while(tempDateStart.isBefore(tempDateEnd) || tempDateStart.equals(tempDateEnd)){
												
					if(weekOfYear.equalsIgnoreCase(tempDateStart.weekOfWeekyear().getAsShortText()))										
						 dayToIndex++;	
					else{										 
					    getProjectManagementService().addReleasePlan(editReleaseForm, empId,dateStart,tempDateStart.minusDays(1),dayFromIndex,dayToIndex,false);				    
						 weekOfYear=tempDateStart.weekOfWeekyear().getAsShortText();
						 dateStart = tempDateStart;
						 dayFromIndex = dayToIndex++;
					    }
					 
					tempDateStart = tempDateStart.plusDays(1);
			    }			  					 
			  	getProjectManagementService().addReleasePlan(editReleaseForm,empId,dateStart,tempDateStart.minusDays(1),dayFromIndex,dayToIndex,true);     
			     
			}*/
		//}
		
		
		return refData;
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
	
	@RequestMapping(value = "/editProject.do")
	public String editProject(
			@ModelAttribute("editProjectForm") ProjectForm editProjectForm,
			Model model) {
		LOG.debug("Project Id:[{}] Desc:[{}] Start date:[{}] End date:[{}] Developers:{}",new Object[]{editProjectForm.getProjectId(), editProjectForm.getProjectDescription(), editProjectForm.getStartDate(), editProjectForm.getEndDate(),editProjectForm.getSelectedResources()});
		getProjectManagementService().editProject(editProjectForm);
		model.addAttribute("addProjectForm", new ProjectForm());
		model.addAttribute("addReleaseForm", new ReleaseForm());		
		model.addAttribute("editProjectForm", new ProjectForm());
		model.addAttribute("projectList", getProjectList());
		return "/projectmanagement/index";
	}
	
	
	@RequestMapping(value = "/deleteProject.do")
	public @ResponseBody JsonResponse deleteProject(
			@RequestParam("projectId") String projectId,
			Model model) {

		final JsonResponse response =getProjectManagementService().deleteProject(projectId);
		
		LOG.debug("Project id to delete :[{}] | Status:[{}]", projectId,response.getMessage());


		return response;
	}
	
	
	@RequestMapping(value = "/deleteRelease.do")
	public String deleteRelease(
			@RequestParam("releaseId") Integer releaseId,
			Model model) {
		LOG.debug("Project Name:[{--}] DELETE RELEASE NAME Name:[{}]", releaseId);
		
		getProjectManagementService().deleteRelease(releaseId);
		model.addAttribute("addProjectForm", new ProjectForm());
		model.addAttribute("addReleaseForm", new ReleaseForm());
		return "/projectmanagement/projectPlan";
	}
	
	
	@RequestMapping(value = "/fetchReleases.do" ,method = RequestMethod.POST)
	public @ResponseBody
	List<ReferenceData> getReleaseList(@RequestParam("projectId") String projectId,Model model) {
		return getProjectManagementService().getProjectReleaseIds(projectId);
	}
	

	@RequestMapping(value = "/viewProjectReleaseDetails.do")
	public String viewProjectReleaseDetails(@RequestBody ReleaseForm addReleaseForm,
				Model model) {
		LOG.debug("Select Project Id:[{}] and Selected Release Id:[{}]",
				addReleaseForm.getProjectId(), addReleaseForm.getReleaseId());
		
		ProjectForm planData = getProjectManagementService().getProjectPlanDetails(addReleaseForm.getReleaseId(), addReleaseForm.getProjectId());
		
		LOG.debug("Result Data -> Project Id:[{}] Release Id:[{}]",planData.getProjectId(),planData.getReleases().get(0).getReleaseId());
		
		model.addAttribute("viewProjRelDetails", planData);
		
		return "/projectmanagement/viewProjectRelease";
	}
	
	@RequestMapping(value = "/addComponent.do")
	public @ResponseBody ComponentForm addComponent(
			@RequestParam("projectId") Integer projectId,
			@RequestParam("componentName") String componentName,
			@RequestParam("functionalDesc") String functionalDesc,
			@RequestParam("compStartDate") String compStartDate,
			@RequestParam("compEndDate") String compEndDate,
			@RequestParam("compResource") Integer compResource,
			@RequestParam("releaseId") Integer releaseId,
			@RequestParam("phaseId") Integer phaseId,
			@RequestParam("workDesc") String workDesc,
			Model model) {

		LOG.debug("addComponent :[{},{},{},{},{},{},{},{}]",new Object[]{projectId,componentName,functionalDesc,compStartDate,compEndDate,compResource,releaseId,phaseId});
		return	getProjectManagementService().addComponent(projectId,phaseId,componentName,functionalDesc,compStartDate,compEndDate,compResource,releaseId,workDesc);
				
	}
	
	@RequestMapping(value = "/getCompDetails.do")
	public @ResponseBody ComponentForm getComponentDetails(
			@RequestParam("cmpName") String componentName,
			@RequestParam("cmpPhase") Integer phaseId,
			@RequestParam("cmpRelease") Integer releaseId,
			Model model) {
		return  getProjectManagementService().getComponentDetails(componentName, phaseId, releaseId);
		
	}
	

}
