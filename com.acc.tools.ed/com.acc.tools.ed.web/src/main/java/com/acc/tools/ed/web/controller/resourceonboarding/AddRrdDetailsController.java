package com.acc.tools.ed.web.controller.resourceonboarding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.integration.service.ProjectManagementService;
import com.acc.tools.ed.web.controller.common.AbstractEdbBaseController;

@Controller
@SessionAttributes({ "edbUser" })
public class AddRrdDetailsController extends AbstractEdbBaseController {

	private static final Logger LOG = LoggerFactory
			.getLogger(AddRrdDetailsController.class);

	@Autowired
	ProjectManagementService projectManagementService;

	@RequestMapping(value = "/resourceDemand.do")
	public String resourceDemand(Model model) {
		
		return "/resourcemanagement/resourceDemandDetails";
	}

}
