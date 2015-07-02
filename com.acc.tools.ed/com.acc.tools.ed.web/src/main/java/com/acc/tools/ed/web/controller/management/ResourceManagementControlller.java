package com.acc.tools.ed.web.controller.management;

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
		model.addAttribute("editCapabilityForm", new Capability());
		model.addAttribute("editLevelForm", new Level());
		model.addAttribute("editSkillForm", new Skill());
		List<String> capabilityList = projectManagementService.getCapability();
		model.addAttribute("capabilityList", capabilityList);
		List<String> levelList = projectManagementService.getLevel();
		model.addAttribute("levelList", levelList);
		return "/resourcemanagement/capabilitylevelskillmanagement";

	}

	@RequestMapping(value = "/addCapabilityForm.do")
	public @ResponseBody String addCapability(
			@ModelAttribute("addCapabilityForm") Capability addCapabilityForm,
			Model model) {
		final ReferenceData newCapability = getProjectManagementService()
				.addCapability(addCapabilityForm);

		if (newCapability.getId() == null) {
			LOG.debug("Add Capability returned --> Capability added successfully");
			model.addAttribute("status", "success");
			List<String> capabilityList = projectManagementService
					.getCapability();
			capabilityList.add(newCapability.getLabel());
		} else {
			LOG.debug("Add Capability returned --> " + newCapability.getLabel());
			model.addAttribute("status", "fail");
		}

		return newCapability.getId();

	}

	@RequestMapping(value = "/addLevelForm.do")
	public @ResponseBody String addLevel(
			@ModelAttribute("addLevelForm") Level addLevelForm, Model model) {
		final ReferenceData newLevel = getProjectManagementService().addLevel(
				addLevelForm);
		if (newLevel.getId() == null) {
			LOG.debug("Add Level returned --> Level added successfully");
			model.addAttribute("status", "success");
			List<String> levelList = projectManagementService.getLevel();
			model.addAttribute("levelList", levelList);
		} else {
			LOG.debug("Add Level returned --> " + newLevel.getLabel());
			model.addAttribute("status", "fail");
		}
		return newLevel.getId();

	}

	@RequestMapping(value = "/addSkillForm.do")
	public @ResponseBody String addSkill(
			@ModelAttribute("addSkillForm") Skill addSkillForm, Model model) {
		final ReferenceData newSkill = getProjectManagementService().addSkill(
				addSkillForm);

		if (newSkill.getId() == null) {
			LOG.debug("Add Skill returned --> Skill added successfully");
			model.addAttribute("status", "success");
		} else {
			LOG.debug("Add Skill returned --> " + newSkill.getLabel());
			model.addAttribute("status", "fail");
		}
		return newSkill.getId();
	}

	@RequestMapping(value = "/editCapabilityForm.do")
	public @ResponseBody String editCapability(
			@ModelAttribute("editCapabilityForm") Capability editCapabilityForm,
			Model model) {
		final ReferenceData newCapability = getProjectManagementService()
				.editCapability(editCapabilityForm);

		if (newCapability.getId() == null) {
			LOG.debug("Edit Capability returned --> Capability edited successfully");
			model.addAttribute("status", "success");
			List<String> capabilityList = projectManagementService
					.getCapability();
			model.addAttribute("capabilityList", capabilityList);
		} else {
			LOG.debug("Edit Capability returned --> "
					+ newCapability.getLabel());
			model.addAttribute("status", "fail");
		}

		return newCapability.getId();

	}

	@RequestMapping(value = "/viewCapabilityDetails.do")
	public @ResponseBody Capability viewCapabilityDetails(
			@RequestParam("existingCapability") String existingCapability,
			Model model) {

		LOG.debug("View Capability details of :{}", existingCapability);
		Capability capability = new Capability();
		capability.setExistingCapability(existingCapability);
		return capability;
	}

	@RequestMapping(value = "/deleteCapability.do")
	public @ResponseBody String deleteCapability(
			@RequestParam("existingCapability") String existingCapability,
			Model model) {

		final ReferenceData deletedCapability = getProjectManagementService()
				.deleteCapability(existingCapability);

		if (deletedCapability.getId() == null) {
			LOG.debug("Delete Capability returned --> Capability deleted successfully");
			model.addAttribute("status", "success");
			List<String> capabilityList = projectManagementService
					.getCapability();
			model.addAttribute("capabilityList", capabilityList);
		} else {
			LOG.debug("Delete Capability returned --> "
					+ deletedCapability.getLabel());
			model.addAttribute("status", "fail");
		}
		return deletedCapability.getId();
	}

	@RequestMapping(value = "/editLevelForm.do")
	public @ResponseBody String editLevel(
			@ModelAttribute("editLevelForm") Level editLevelForm, Model model) {
		final ReferenceData newLevel = getProjectManagementService().editLevel(
				editLevelForm);

		if (newLevel.getId() == null) {
			LOG.debug("Edit Level returned --> Level edited successfully");
			model.addAttribute("status", "success");
			List<String> levelList = projectManagementService.getLevel();
			model.addAttribute("levelList", levelList);
		} else {
			LOG.debug("Edit Capability returned --> " + newLevel.getLabel());
			model.addAttribute("status", "fail");
		}

		return newLevel.getId();

	}

	@RequestMapping(value = "/viewLevelDetails.do")
	public @ResponseBody Level viewLevelDetails(
			@RequestParam("existingLevel") String existingLevel, Model model) {

		LOG.debug("View Level details of :{}", existingLevel);
		Level level = new Level();
		level.setExistingLevel(existingLevel);
		return level;
	}

	@RequestMapping(value = "/deleteLevel.do")
	public @ResponseBody String deleteLevel(
			@RequestParam("existingLevel") String existingLevel, Model model) {

		final ReferenceData deletedLevel = getProjectManagementService()
				.deleteLevel(existingLevel);

		if (deletedLevel.getId() == null) {
			LOG.debug("Delete Level returned --> Level deleted successfully");
			model.addAttribute("status", "success");
			List<String> levelList = projectManagementService.getLevel();
			model.addAttribute("levelList", levelList);
		} else {
			LOG.debug("Delete Level returned --> " + deletedLevel.getLabel());
			model.addAttribute("status", "fail");
		}
		return deletedLevel.getId();
	}
	
	@RequestMapping(value = "/loadSkillDetails.do")
	public @ResponseBody Skill loadSkillDetails(
			@RequestParam("existingCapability") String existingCapability, Model model) {
		LOG.debug("Load Skill details of Capability:{}", existingCapability);
		Skill skill = new Skill();
		skill.setCapabilityName(existingCapability);
		return skill;
	}
	
	@RequestMapping(value = "/viewSkillDetails.do")
	public @ResponseBody Skill viewSkillDetails(
			@RequestParam("existingCapability") String existingCapability,@RequestParam("existingSkill") String existingSkill, Model model) {
		LOG.debug("View Skill details of Capability:{} and Skill:{}", existingCapability,existingSkill);
		Skill skill = new Skill();
		skill.setCapabilityName(existingCapability);
		skill.setExistingSkill(existingSkill);
		return skill;
	}

	@RequestMapping(value = "/getSkill.do")
	public @ResponseBody List<String> getSkill(
			@RequestParam("existingCapability") String existingCapability) {

		LOG.debug("Get Skill details of capability:{}", existingCapability);
		return projectManagementService.getSkill(existingCapability);
	}
	
	@RequestMapping(value = "/editSkillForm.do")
	public @ResponseBody String editSkill(
			@ModelAttribute("editSkillForm") Skill editSkillForm, Model model) {
		final ReferenceData newSkill = getProjectManagementService().editSkill(
				editSkillForm);
		if (newSkill.getId() == null) {
			LOG.debug("Edit Skill returned --> Skill edited successfully");
			model.addAttribute("status", "success");
		} else {
			LOG.debug("Edit Skill returned --> " + newSkill.getLabel());
			model.addAttribute("status", "fail");
		}
		return newSkill.getId();
	}
	
	@RequestMapping(value = "/deleteSkill.do")
	public @ResponseBody String deleteSkill(
			@RequestParam("existingCapability") String existingCapability,@RequestParam("existingSkill") String existingSkill, Model model) {

		final ReferenceData deletedSkill = getProjectManagementService()
				.deleteSkill(existingCapability,existingSkill);

		if (deletedSkill.getId() == null) {
			LOG.debug("Delete Skill returned --> Skill deleted successfully");
			model.addAttribute("status", "success");
		} else {
			LOG.debug("Delete Skill returned --> " + deletedSkill.getLabel());
			model.addAttribute("status", "fail");
		}
		return deletedSkill.getId();
	}
	
	@RequestMapping(value = "/checkResource.do")
	public String checkResource(@ModelAttribute("addEmpDetailsForm") ResourceDetails addEmpDetailsForm,Model model){
		String empId = addEmpDetailsForm.getEmployeeNumber();
		String resourceExits= projectManagementService.getEmployeeName(empId);
		addEmpDetailsForm.setResourceFlag(resourceExits);
		return "/resourcemanagement/addResource";
		
	}

}
