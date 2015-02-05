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

import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReleaseForm;
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
		for(ProjectForm pf:projData){
			LOG.debug("Project Name:[{}]",pf.getProjectName());
			for(ReleaseForm rf:pf.getReleases()){
				LOG.debug("\tRelease Name:[{}]",rf.getReleaseName());
				for(ComponentForm cf:rf.getComponents()){
					LOG.debug("\t\tComponent Name:[{}]",cf.getComponentName());
					for(TaskForm tf:cf.getTaskFormList()){
						LOG.debug("\t\t\tTaskName Name:[{}]",tf.getTaskName());
					}
				}
			}
		}
		model.addAttribute("projData", projData);
		model.addAttribute("addTaskForm", new TaskForm());
		return "/projectwork/index";
	}
	
	@RequestMapping(value = "/myTasks.do")
	public String myTasks(@ModelAttribute("edbUser")EDBUser edbUser,Model model) {
		List<ProjectForm> projData =projectWorkService.getMyTasks(edbUser.getEmployeeId());
		for(ProjectForm pf:projData){
			LOG.debug("Project Name:[{}]",pf.getProjectName());
			for(ReleaseForm rf:pf.getReleases()){
				LOG.debug("\tRelease Name:[{}]",rf.getReleaseName());
				for(ComponentForm cf:rf.getComponents()){
					LOG.debug("\t\tComponent Name:[{}]",cf.getComponentName());
					for(TaskForm tf:cf.getTaskFormList()){
						LOG.debug("\t\t\tTaskName Name:[{}]",tf.getTaskName());
					}
				} 
			}
		}
		model.addAttribute("projData", projData);
		model.addAttribute("addTaskForm", new TaskForm());
		return "/projectwork/myTasks";
	}
	
	@RequestMapping(value = "/teamTasks.do")
	public String teamTasks(@ModelAttribute("edbUser")EDBUser edbUser,Model model) {
		List<ProjectForm> projData =projectWorkService.getMyTeamTasks(edbUser.getEmployeeId());
		for(ProjectForm pf:projData){
			LOG.debug("Project Name:[{}]",pf.getProjectName());
			for(ReleaseForm rf:pf.getReleases()){
				LOG.debug("\tRelease Name:[{}]",rf.getReleaseName());
				for(ComponentForm cf:rf.getComponents()){
					LOG.debug("\t\tComponent Name:[{}]",cf.getComponentName());
					for(TaskForm tf:cf.getTaskFormList()){
						LOG.debug("\t\t\tTaskName Name:[{}]",tf.getTaskName());
					}
				}
			}
		}
		model.addAttribute("addTaskForm", new TaskForm());
		model.addAttribute("projData", projData);
		return "/projectwork/teamTasks";
	}
	
	@RequestMapping(value = "/addTask.do")
	public  String addTask(@ModelAttribute("addTaskForm")TaskForm taskform,Model model) {
		
		LOG.debug("addTask:[{}]",taskform.getTaskName());
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
	

}
