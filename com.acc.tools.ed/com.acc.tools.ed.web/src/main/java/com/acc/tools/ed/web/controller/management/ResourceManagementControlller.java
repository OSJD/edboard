package com.acc.tools.ed.web.controller.management;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.integration.dto.MasterEmployeeDetails;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ResourceDetails;
import com.acc.tools.ed.integration.dto.TaskForm;
import com.acc.tools.ed.integration.service.ProjectManagementService;
import com.acc.tools.ed.web.controller.common.AbstractEdbBaseController;

@Controller
@SessionAttributes({ "edbUser" })
public class ResourceManagementControlller extends AbstractEdbBaseController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProjectManagementControlller.class);
	
	@Autowired
	ProjectManagementService projectManagementService;

	@RequestMapping(value = "/resourceManagement.do")
	public String resourceManagement(Model model){
		
		List<String> skillList = projectManagementService.getSkill();
		List<String> levelList = projectManagementService.getLevel();
		List<String> capabilityList = projectManagementService.getCapability();
		model.addAttribute("skillList", skillList);
		model.addAttribute("levelList", levelList);
		model.addAttribute("capabilityList", capabilityList);
		model.addAttribute("addEmpDetailsForm",new ResourceDetails());
		
		return "/resourcemanagement/addResource";
		
	}
	
	
	@RequestMapping(value = "/loadResource.do")
	public String loadResource(Model model){
		
		List<MasterEmployeeDetails> empList= projectManagementService.getAllEmployees();
		model.addAttribute("empList", empList);
		model.addAttribute("addEmpDetailsForm",new ResourceDetails());
		return "/resourcemanagement/resourceManagement";
		
	}
	
	
	
	@RequestMapping(value = "/addEmpDetailsForm.do")
	public String addResource(
			@ModelAttribute("addEmpDetailsForm") ResourceDetails addEmpDetailsForm,
			Model model){
		final ReferenceData newProject = getProjectManagementService().addResource(addEmpDetailsForm);
		LOG.debug("Add Project retruned --> Resource Id: {} | Resource Name:{}", newProject.getId(),newProject.getLabel());
		
		model.addAttribute("addEmpDetailsForm",addEmpDetailsForm);
		model.addAttribute("addProjectForm",new ProjectForm());
		model.addAttribute("editProjectForm", new ProjectForm());
		model.addAttribute("addTaskForm",new TaskForm());
		return "/projectmanagement/index";
	}
	
}
