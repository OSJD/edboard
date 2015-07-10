package com.acc.tools.ed.web.controller.management;

import java.util.List;

import org.jfree.util.Log;
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
		LOG.debug("Inside resourceManagement.do");
		List<String> skillList = projectManagementService.getSkill();
		List<String> levelList = projectManagementService.getLevel();
		List<String> capabilityList = projectManagementService.getCapability();
		model.addAttribute("skillList", skillList);
		model.addAttribute("levelList", levelList);
		model.addAttribute("capabilityList", capabilityList);
		model.addAttribute("addEmpDetailsForm",new ResourceDetails());
		model.addAttribute("updateEmpDetailsForm",new ResourceDetails());
		return "/resourcemanagement/resourceManagement";
		
	}
	
	@RequestMapping(value = "/loadResource.do")
	public String loadResource(Model model){
		LOG.debug("Inside loadResource.do");
		List<MasterEmployeeDetails> empList= projectManagementService.getAllEmployees();
		List<String> skillList = projectManagementService.getSkill();
		List<String> levelList = projectManagementService.getLevel();
		List<String> capabilityList = projectManagementService.getCapability();
		model.addAttribute("skillList", skillList);
		model.addAttribute("levelList", levelList);
		model.addAttribute("capabilityList", capabilityList);
		model.addAttribute("empList", empList);
		model.addAttribute("addEmpDetailsForm",new ResourceDetails());
		model.addAttribute("updateEmpDetailsForm",new ResourceDetails());
		return "/resourcemanagement/resourceManagement";
		
	}
	
	
	
	@RequestMapping(value = "/addEmpDetailsForm.do")
	public  @ResponseBody String addResource(
			@ModelAttribute("addEmpDetailsForm") ResourceDetails addEmpDetailsForm,
			Model model){
		LOG.debug("Inside addEmpDetailsForm.do");
		final ReferenceData newProject = getProjectManagementService().addResource(addEmpDetailsForm);
		
		model.addAttribute("addEmpDetailsForm",addEmpDetailsForm);
		model.addAttribute("updateEmpDetailsForm",new ResourceDetails());
		model.addAttribute("addProjectForm",new ProjectForm());
		model.addAttribute("editProjectForm", new ProjectForm());
		model.addAttribute("addTaskForm",new TaskForm());
		model.addAttribute("statusForm",new WeeklyStatusReportData());
		if (newProject.getId() == null) {
			LOG.debug("Update Resource returned --> Resource added successfully");
		} else {
			LOG.debug("Update Resource returned --> " + newProject.getLabel());
		}
		return newProject.getId();
	}
	
	@RequestMapping(value = "/updateEmpDetailsForm.do")
	public  @ResponseBody String updateResource(
			
			@ModelAttribute("updateEmpDetailsForm") ResourceDetails updateEmpDetailsForm,
			Model model){
		
		LOG.debug("Inside updateEmpDetailsForm.do");
		final ReferenceData newProject = getProjectManagementService().updateResource(updateEmpDetailsForm);
		model.addAttribute("addEmpDetailsForm",new ResourceDetails());
		model.addAttribute("updateEmpDetailsForm",updateEmpDetailsForm);
		model.addAttribute("addProjectForm",new ProjectForm());
		model.addAttribute("editProjectForm", new ProjectForm());
		model.addAttribute("addTaskForm",new TaskForm());
		model.addAttribute("statusForm",new WeeklyStatusReportData());
		
		if (newProject.getId() == null) {
			LOG.debug("Update Resource returned --> Resource updated successfully");
		} else {
			LOG.debug("Update Resource returned --> " + newProject.getLabel());
		}
		return newProject.getId();
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
		LOG.debug("capability Name --> "+ editSkillForm.getCapabilityName() + "new skill name-->" + editSkillForm.getSkillName()+ "Existing skill name -->"+editSkillForm.getExistingSkill());
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
	
	@RequestMapping(value = "/getAllSkills.do")
	public @ResponseBody List<String> getAllSkills(Model model) {
		LOG.debug("Inside getAllSkills.do");
		List<String> skillList = projectManagementService.getSkill();
		return skillList;
	}
	
	@RequestMapping(value = "/viewResourceDetails.do")
	public @ResponseBody ResourceDetails viewResourceDetails(@RequestParam("existingEmpId") String existingEmpId,Model model) {
		LOG.debug("Inside viewResourceDetails.do");
		List<String> skillList = projectManagementService.getSkill();
		List<String> levelList = projectManagementService.getLevel();
		List<String> capabilityList = projectManagementService.getCapability();
		model.addAttribute("skillList", skillList);
		model.addAttribute("levelList", levelList);
		model.addAttribute("capabilityList", capabilityList);
		ResourceDetails empDtls = projectManagementService.getEmployeeDetails(existingEmpId);
		return empDtls;
	}
	
	@RequestMapping(value = "/deleteResource.do")
	public @ResponseBody String deleteResource(
			@RequestParam("existingEmpId") String existingEmpId,
			Model model) {

		final ReferenceData deletedResource = getProjectManagementService()
				.deleteResource(existingEmpId);

		if (deletedResource.getId() == null) {
			LOG.debug("Delete Resource returned --> Resource deleted successfully");
		} else {
			LOG.debug("Delete Resource returned --> "
					+ deletedResource.getLabel());
		}
		return deletedResource.getId();
	}

}
