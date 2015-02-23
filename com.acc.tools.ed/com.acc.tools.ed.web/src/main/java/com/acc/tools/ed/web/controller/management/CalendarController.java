package com.acc.tools.ed.web.controller.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.integration.dto.EDBUser;

@Controller
@SessionAttributes({ "edbUser" }) 
public class CalendarController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CalendarController.class);
	
	  @RequestMapping({"/calendar.do"})
	  public String calendar(Model model,
			  @ModelAttribute("edbUser") EDBUser edbUser){
		  
		model.addAttribute("edbUser", edbUser);
		
	    return "/projectwork/calendar";
	  }

}
