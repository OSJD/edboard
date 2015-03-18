package com.acc.tools.ed.web.controller.management;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.VacationForm;
import com.acc.tools.ed.integration.service.ProjectWorkService;

@Controller
@SessionAttributes({ "edbUser" }) 
public class CalendarController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CalendarController.class);
	
	@Autowired
	private ProjectWorkService projectWorkService;
	
	 @RequestMapping({"/calendar.do"})
	  public String calendar(Model model,
			
			  @ModelAttribute("edbUser") EDBUser edbUser){
		  
		 List<VacationForm> calendar= projectWorkService.getVacationDetails(edbUser.getEmployeeId());
		 for(VacationForm vctn: calendar)
		 {
			 LOG.debug("Resource Name:[{}]",vctn.getVacationType());
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
			vacationForm.setEmployeeId(edbUser.getEmployeeId());
			vacationForm.setStatus("Submitted");
			vacationForm.setSupervisorId(edbUser.getSupervisorId());
			if(vacationForm.getVacationType()!="-4"){
				projectWorkService.addVacation(vacationForm);			
			} else {
				System.out.println("-------------------------------------------->holiday");
			}

			return "success";
		}
	  
	  @RequestMapping(value = "/approveVacation.do")
		public @ResponseBody String approveVacation(
				@RequestParam("vacationId") int vacationId,
				@RequestParam("status") String status,
				@RequestParam("approverComments") String approverComments,
				@ModelAttribute("edbUser") EDBUser edbUser,
				Model model){
			LOG.debug("Vacation Type:{}",vacationId);
			LOG.debug("Vacation Status:{}",status);
			final VacationForm vacationForm=new VacationForm();
			vacationForm.setVacationId(vacationId);
			vacationForm.setStatus(status);
			vacationForm.setApproverComments(approverComments);
			vacationForm.setSupervisorId(edbUser.getSupervisorId());
			projectWorkService.approveVacation(vacationForm);			

			return "success";
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

	  @RequestMapping({"/dvlpCalendar.do"})
	  public String developerCalendar(Model model,
			
			  @ModelAttribute("edbUser") EDBUser edbUser){
		  
		 List<VacationForm> calendar= projectWorkService.getDeveloperVacationDetails(edbUser.getEmployeeId());
		 for(VacationForm vctn: calendar)
		 {
			 LOG.debug("Resource Name:[{}]",vctn.getVacationType());
		 }
		  System.out.println("working in developer vacation method");
		model.addAttribute("edbUser", edbUser);
		model.addAttribute("calendar", calendar);
		
	    return "/projectwork/calendar";
	  }
	  
	  
	 
	  
}
