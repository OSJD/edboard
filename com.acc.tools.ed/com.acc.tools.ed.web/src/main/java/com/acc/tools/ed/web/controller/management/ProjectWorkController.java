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

import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.TaskForm;
import com.acc.tools.ed.integration.service.ProjectWorkService;
import com.acc.tools.ed.web.controller.common.AbstractEdbBaseController;

@Controller
@SessionAttributes({ "edbUser" })
public class ProjectWorkController extends AbstractEdbBaseController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProjectWorkController.class);
	
	@Autowired
	private ProjectWorkService projectWorkService;
	
	@RequestMapping(value = "/projectWork.do")
	public String resourceManagement(@ModelAttribute("edbUser")EDBUser edbUser,Model model) {

		List<ProjectForm> projData =projectWorkService.getMyTasks(edbUser.getEmployeeId());
		model.addAttribute("projData", projData);
		model.addAttribute("addTaskForm", new TaskForm());
		return "/projectwork/index";
	}
	
	@RequestMapping(value = "/myTasks.do")
	public String myTasks(@ModelAttribute("edbUser")EDBUser edbUser,Model model) {
		
		List<ProjectForm> projData =projectWorkService.getMyTasks(edbUser.getEmployeeId());
		model.addAttribute("projData", projData);
		model.addAttribute("addTaskForm", new TaskForm());

		return "/projectwork/myTasks";
	}
	
	@RequestMapping(value = "/getTaskIdsByComponentId.do")
	public @ResponseBody List<ReferenceData> getTaskIdsByComponentId(
			@ModelAttribute("componentId") Integer componentId,
			Model model){
		return projectWorkService.getTasksByComponentId(componentId);
	}

	
	@RequestMapping(value = "/getTaskByTaskId.do")
	public @ResponseBody TaskForm getTaskByTaskId(
			@ModelAttribute("taskId") Integer taskId,
			Model model){
		return projectWorkService.getTaskByTaskId(taskId);
	}
	
	
	@RequestMapping(value = "/fetchtaskActivityDetails.do")
	public @ResponseBody List<ReferenceData> getTaskActivities(
			@ModelAttribute("taskId") Integer taskId,
			Model model){
		//model.addAttribute("activityList", getTaskActivities(taskId, model));
		List<ReferenceData> test = projectWorkService.getTaskActivities(taskId);
		for(ReferenceData data : test)
		{
			System.out.println("task id ::" +data.getId());
			System.out.println("activity is ::"+data.getLabel());
		}
		return test;
	}
	

	
	@RequestMapping(value = "/teamTasks.do")
	public String teamTasks(@ModelAttribute("edbUser")EDBUser edbUser,Model model) {
		List<ProjectForm> projData =projectWorkService.getMyTeamTasks(edbUser.getEmployeeId());
		model.addAttribute("addTaskForm", new TaskForm());
		model.addAttribute("projData", projData);
		return "/projectwork/teamTasks";
	}
	
	@RequestMapping(value = "/addTask.do")
	public  String addTask(@ModelAttribute("addTaskForm")TaskForm taskform,Model model) {
		
		LOG.debug("addTask:{} | Today Work:{}",taskform.getTaskName(),taskform.getTaskComments());
		getProjectWorkService().addTasks(taskform);
		TaskForm taskData=projectWorkService.retrieveTasks();
		taskform.setTaskId(taskData.getTaskId());
		model.addAttribute("addTaskForm", taskform);
		return "/projectwork/newTask";
	}
	
	@RequestMapping(value = "/deleteTask.do")
	public String deleteTask(@RequestParam("taskId") int taskId,Model model) {
		
		LOG.debug("Project Name:[{--}] addTask:[{}]");
		getProjectWorkService().deleteTasks(taskId);
		return "/projectwork/newTask";
	}
	
	@RequestMapping(value = "/editTask.do")
	public @ResponseBody List<TaskForm> editTask(@RequestParam("taskId") int taskId,Model model) {
		
		LOG.debug("Project Name:[{--}] addTask:[{}]");
		List<TaskForm> taskFormList = projectWorkService.editTasks(taskId);
		return taskFormList;
	}
	
	@RequestMapping(value = "/saveTask.do")
	public String saveTask(@ModelAttribute("addTaskForm")TaskForm taskform,Model model) {
		
		LOG.debug("Project Name:[{--}] addTask:[{}]");
		getProjectWorkService().saveTasks(taskform);
		model.addAttribute("addTaskForm", taskform);
		return "/projectwork/newTask";
	}
	
	 @RequestMapping(value = "/addTaskComments.do")
		public @ResponseBody String addTaskComments(
				@RequestParam("taskId") int taskId,
				@RequestParam("devloperComments") String devloperComments,
				@ModelAttribute("edbUser") EDBUser edbUser,
				Model model){
			LOG.debug("taskId:{}",taskId);
			LOG.debug("devloperComments:{}",devloperComments);
			
			projectWorkService.addTaskComments(taskId,devloperComments);			

			return "success";
		}
}
