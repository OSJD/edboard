package com.acc.tools.ed.web.controller.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.integration.dto.EDBUser;
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
		  
		  projectWorkService.getVacationDetails(edbUser.getEmployeeId());
		  
		model.addAttribute("edbUser", edbUser);
		
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

}
