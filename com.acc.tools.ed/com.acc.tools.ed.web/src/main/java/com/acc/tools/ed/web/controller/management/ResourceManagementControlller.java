package com.acc.tools.ed.web.controller.management;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.integration.dto.Capability;
import com.acc.tools.ed.integration.dto.Level;
import com.acc.tools.ed.integration.dto.MasterEmployeeDetails;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.ResourceDetails;
import com.acc.tools.ed.integration.dto.Skill;
import com.acc.tools.ed.integration.dto.TaskForm;
import com.acc.tools.ed.integration.service.ProjectManagementService;
import com.acc.tools.ed.report.dto.WeeklyStatusReportData;
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
	
	@RequestMapping(value = "/resourceManagementUpdate.do")
	public String resourceManagementUpdate(Model model){
		
		List<String> skillList = projectManagementService.getSkill();
		List<String> levelList = projectManagementService.getLevel();
		List<String> capabilityList = projectManagementService.getCapability();		
		/*List<String> employeeNumberList = projectManagementService.getEmployeeNumber();*/
		
		model.addAttribute("skillList", skillList);
		model.addAttribute("levelList", levelList);
		model.addAttribute("capabilityList", capabilityList);		
		/*model.addAttribute("employeeNumberList", employeeNumberList);*/
		
		model.addAttribute("addEmpDetailsForm",new ResourceDetails());
		//model.addAttribute("updateEmpDetailsForm",new ResourceDetails());
		
		return "/resourcemanagement/updateResource";
		
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
		model.addAttribute("statusForm",new WeeklyStatusReportData());
		return "/projectmanagement/index";
	}
	
	@RequestMapping(value = "/updateEmpDetailsForm.do")
	public String updateResource(
			@ModelAttribute("addEmpDetailsForm") ResourceDetails addEmpDetailsForm,
			Model model){
		
		System.out.println();
		
		final ReferenceData newProject = getProjectManagementService().updateResource(addEmpDetailsForm);
		
		LOG.debug("Add Project retruned --> Resource Id: {} | Resource Name:{}", newProject.getId(),newProject.getLabel());
		
		model.addAttribute("addEmpDetailsForm",addEmpDetailsForm);
		model.addAttribute("addProjectForm",new ProjectForm());
		model.addAttribute("editProjectForm", new ProjectForm());
		model.addAttribute("addTaskForm",new TaskForm());
		model.addAttribute("statusForm",new WeeklyStatusReportData());
		return "/projectmanagement/index";
	}

	@RequestMapping(value = "/capabilitylevelskillmanagement.do")
	public String capabilitylevelskillmanagement(Model model) {
		model.addAttribute("addCapabilityForm", new Capability());
		model.addAttribute("addLevelForm", new Level());
		model.addAttribute("addSkillForm", new Skill());
		return "/resourcemanagement/capabilitylevelskillmanagement";

	}

	@RequestMapping(value = "/addCapabilityForm.do")
	public @ResponseBody String addCapability(
			@ModelAttribute("addCapabilityForm") Capability addCapabilityForm,
			Model model) {
		final ReferenceData newCapability = getProjectManagementService()
				.addCapability(addCapabilityForm);

		model.addAttribute("addProjectForm", new ProjectForm());
		model.addAttribute("addReleaseForm", new ReleaseForm());		
		model.addAttribute("editProjectForm", new ProjectForm());
		model.addAttribute("addEmpDetailsForm",new ResourceDetails());
		model.addAttribute("projectList", getProjectList());
		
		if(newCapability.getId()==null){
			LOG.debug(
					"Add Capability returned --> Capability added successfully");
			model.addAttribute("status", "success");
		}
		else{
			LOG.debug("Add Capability returned --> "+ newCapability.getLabel());
		model.addAttribute("status", "fail");
		}
		return newCapability.getId();
		
	}

	@RequestMapping(value = "/addLevelForm.do")
	public @ResponseBody String addLevel(
			@ModelAttribute("addLevelForm") Level addLevelForm,
			Model model) {
		final ReferenceData newLevel = getProjectManagementService()
				.addLevel(addLevelForm);

		model.addAttribute("addProjectForm", new ProjectForm());
		model.addAttribute("addReleaseForm", new ReleaseForm());		
		model.addAttribute("editProjectForm", new ProjectForm());
		model.addAttribute("addEmpDetailsForm",new ResourceDetails());
		model.addAttribute("projectList", getProjectList());
		if(newLevel.getId()==null){
			LOG.debug(
					"Add Level returned --> Level added successfully");
			model.addAttribute("status", "success");
		}
		else{
		LOG.debug("Add Level returned --> "+ newLevel.getLabel());
		model.addAttribute("status", "fail");
		}
		return newLevel.getId();

	}
	
	@RequestMapping(value = "/addSkillForm.do")
	public @ResponseBody String addSkill(
			@ModelAttribute("addSkillForm") Skill addSkillForm,
			Model model) {
		final ReferenceData newSkill = getProjectManagementService()
				.addSkill(addSkillForm);

		model.addAttribute("addProjectForm", new ProjectForm());
		model.addAttribute("addReleaseForm", new ReleaseForm());		
		model.addAttribute("editProjectForm", new ProjectForm());
		model.addAttribute("addEmpDetailsForm",new ResourceDetails());
		model.addAttribute("projectList", getProjectList());
		if(newSkill.getId()==null){
			LOG.debug(
					"Add Skill returned --> Skill added successfully");
			model.addAttribute("status", "success");
		}
		else{
			LOG.debug("Add Skill returned --> "+ newSkill.getLabel());
		model.addAttribute("status", "fail");
		}
		return newSkill.getId();
	}
	
	@RequestMapping(value = "/checkResource.do")
	public String checkResource(@ModelAttribute("addEmpDetailsForm") ResourceDetails addEmpDetailsForm,Model model){
		String empId = addEmpDetailsForm.getEmployeeNumber();
		String resourceExits= projectManagementService.getEmployeeName(empId);
		addEmpDetailsForm.setResourceFlag(resourceExits);
		return "/resourcemanagement/addResource";
		
	}

}
