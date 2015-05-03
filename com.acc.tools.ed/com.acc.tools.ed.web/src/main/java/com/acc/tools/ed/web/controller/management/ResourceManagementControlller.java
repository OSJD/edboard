package com.acc.tools.ed.web.controller.management;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.integration.dto.MasterEmployeeDetails;
import com.acc.tools.ed.integration.service.ProjectManagementService;
import com.acc.tools.ed.web.controller.common.AbstractEdbBaseController;

@Controller
@SessionAttributes({ "edbUser" })
public class ResourceManagementControlller extends AbstractEdbBaseController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProjectManagementControlller.class);
	
	@Autowired
	ProjectManagementService projectManagementService;

	@RequestMapping(value = "/resourceManagement.do")
	public String resourceManagement(Model model) {
		List<MasterEmployeeDetails> empList= projectManagementService.getAllEmployees();
		for(MasterEmployeeDetails emp : empList){
			LOG.debug("Name [{}]", emp.getEmployeeName());
		}
		model.addAttribute("empList", empList);
		return "/resourcemanagement/resourceManagement";
	}

}
