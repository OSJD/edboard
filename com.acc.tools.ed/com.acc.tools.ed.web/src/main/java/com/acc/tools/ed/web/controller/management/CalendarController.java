package com.acc.tools.ed.web.controller.management;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.VacationForm;
import com.acc.tools.ed.integration.service.ProjectManagementService;
import com.acc.tools.ed.integration.service.ProjectWorkService;

@Controller
@SessionAttributes({ "edbUser" }) 
public class CalendarController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CalendarController.class);
	
	@Autowired
	private ProjectWorkService projectWorkService;
	
	@Autowired
	private ProjectManagementService projectManagementService;
	
	 @RequestMapping({"/calendar.do"})
	  public String calendar(Model model,			
			  @ModelAttribute("edbUser") EDBUser edbUser){
		  
		 final List<VacationForm> calendar= projectWorkService.getVacationDetailsByEmployeeId(edbUser.getEmployeeId(),edbUser.getEmployeeId());
		 if(edbUser.getRole().equalsIgnoreCase("SUPERVISOR")){
			 calendar.addAll(projectWorkService.getVacationDetailsBySupervisorId(edbUser.getEmployeeId(),edbUser.getEmployeeId()));
		 }
		  
		 model.addAttribute("edbUser", edbUser);
		 model.addAttribute("calendar", calendar);
		
	    return "/projectwork/calendar";
	  }
	  
	  @RequestMapping(value = "/addVacation.do")
		public @ResponseBody String addVacation(
				@ModelAttribute("vacationForm") VacationForm vacationForm,
				@ModelAttribute("edbUser") EDBUser edbUser,
				Model model){
			LOG.debug("Vacation Type:{}",vacationForm.getVacationType());
			int status=0;
			vacationForm.setEmployeeId(edbUser.getEmployeeId());
			vacationForm.setResourceName(edbUser.getEnterpriseId());
			vacationForm.setStatus("Submitted");
			vacationForm.setSupervisorId(edbUser.getSupervisorId());
			if(!vacationForm.getVacationType().equalsIgnoreCase("-4")){
				status=projectWorkService.addVacation(vacationForm);
			} else {
				status=projectWorkService.addHoliday(vacationForm);
			}
			if(status>0){
				return "success";
			}else{
				return "fail";
			}
		}
	  
	  @RequestMapping(value = "/getBackUpList.do")
	  public @ResponseBody List<ReferenceData> getBackUpResource(@ModelAttribute("edbUser") EDBUser edbUser,
				Model model){
		  List<ReferenceData> resources=projectManagementService.getResourcesByProjectId(edbUser.getProjectId());
			for(Iterator<ReferenceData> resItr=resources.iterator();resItr.hasNext();){
				ReferenceData resource=(ReferenceData)resItr.next();
				if(resource.getId().equalsIgnoreCase(""+edbUser.getEmployeeId())){
					resItr.remove();
				}
			}
		  return resources;
	  }
	  
	  @RequestMapping(value = "/approveVacation.do")
		public @ResponseBody String approveVacation(
				@ModelAttribute("updateVacationRequestForm") VacationForm updateVacationRequestForm,
				@ModelAttribute("edbUser") EDBUser edbUser,
				Model model){
			LOG.debug("Vacation Type:{}",updateVacationRequestForm.getVacationId());
			LOG.debug("Vacation Status:{} | Comments:{}",updateVacationRequestForm.getStatus(),updateVacationRequestForm.getApproverComments());
			projectWorkService.approveVacation(updateVacationRequestForm);			

			return updateVacationRequestForm.getStatus();
		}
	  
	  
	  @RequestMapping(value = "/updateVacation.do")
			public @ResponseBody String updateVacation(
					@RequestParam("vacationId") int vacationId,
					@RequestParam("startDate") String startDate,
					@RequestParam("endDate") String endDate,
					@RequestParam("comments") String comments,
					@ModelAttribute("edbUser") EDBUser edbUser,
					Model model){
				LOG.debug("Vacation Type:{}",vacationId);
				LOG.debug("Vacation Status:{}",comments);
				LOG.debug("Vacation start date:{}",startDate);
				LOG.debug("Vacation end date:{}",endDate);
				final VacationForm vacationForm=new VacationForm();
				vacationForm.setVacationId(vacationId);
				vacationForm.setStartDate(startDate);
				vacationForm.setEndDate(endDate);
				vacationForm.setComments(comments);
				projectWorkService.editVacation(vacationForm);			

				return "success";
			}
	  
	 
	  @RequestMapping(value = "/deleteVacation.do")
		public @ResponseBody String deleteVacation(
				@RequestParam("vacationId") int vacationId,
				@ModelAttribute("edbUser") EDBUser edbUser,
				Model model){
			LOG.debug("Vacation Type:{}",vacationId);
			
			final VacationForm vacationForm=new VacationForm();
			vacationForm.setVacationId(vacationId);
			
			vacationForm.setSupervisorId(edbUser.getSupervisorId());
			projectWorkService.deleteVacation(vacationId);			

			return "success";
		}

	  
}
