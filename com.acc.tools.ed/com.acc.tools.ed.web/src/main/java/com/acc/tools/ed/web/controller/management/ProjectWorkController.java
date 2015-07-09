package com.acc.tools.ed.web.controller.management;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.dto.Issue;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.TaskForm;
import com.acc.tools.ed.integration.dto.TaskLedgerForm;
import com.acc.tools.ed.integration.service.ProjectManagementService;
import com.acc.tools.ed.integration.service.ProjectWorkService;
import com.acc.tools.ed.web.controller.common.AbstractEdbBaseController;

@Controller
@SessionAttributes({ "edbUser" })
public class ProjectWorkController extends AbstractEdbBaseController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProjectWorkController.class);
	
	@Autowired
	private ProjectWorkService projectWorkService;
	
	@Autowired
	private ProjectManagementService projectManagementService;
	
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
	public @ResponseBody Map<String,Object> getTaskIdsByComponentId(
			@ModelAttribute("componentId") Integer componentId,
			@ModelAttribute("projectId") Integer projectId,
			@ModelAttribute("edbUser")EDBUser edbUser,
			Model model){
		Map<String,Object> taskPopupData=new HashMap<String, Object>();
		taskPopupData.put("myTasks", projectWorkService.getTasksByComponentId(componentId,edbUser.getEmployeeId()));
		List<ReferenceData> resources=projectManagementService.getResourcesByProjectId(projectId);
		for(Iterator<ReferenceData> resItr=resources.iterator();resItr.hasNext();){
			ReferenceData resource=(ReferenceData)resItr.next();
			if(resource.getId().equalsIgnoreCase(""+edbUser.getEmployeeId())){
				resItr.remove();
			}
		}
		taskPopupData.put("reviewerList", resources);
		LOG.debug("Task Popup Data:{}",taskPopupData);
		return taskPopupData;
	}

	
	@RequestMapping(value = "/getTaskByTaskId.do")
	public @ResponseBody  Map<String,Object> getTaskByTaskId(
			@ModelAttribute("taskId") Integer taskId,
			@ModelAttribute("projectId") Integer projectId,
			@ModelAttribute("edbUser")EDBUser edbUser,			
			Model model){
		final TaskForm taskForm=projectWorkService.getTaskByTaskId(taskId);
		taskForm.setUserId(edbUser.getEmployeeId());
		Map<String,Object> taskPopupData=new HashMap<String, Object>();
		taskPopupData.put("task",taskForm);
		if(projectId!=0){
			List<ReferenceData> resources=projectManagementService.getResourcesByProjectId(projectId);
			taskPopupData.put("reviewerList", resources);
		}
		return taskPopupData;
	}
	
	
	@RequestMapping(value = "/teamTasks.do")
	public String teamTasks(@ModelAttribute("edbUser")EDBUser edbUser,Model model) {
		List<ProjectForm> projData =projectWorkService.getMyTeamTasks(edbUser.getEmployeeId());
		model.addAttribute("addTaskForm", new TaskForm());
		model.addAttribute("projData", projData);
		return "/projectwork/teamTasks";
	}
	
	@RequestMapping(value = "/addTask.do")
	public  String addTask(
			@RequestBody TaskForm taskform,
			@ModelAttribute("edbUser")EDBUser edbUser,
			Model model) {
		
		LOG.debug("Task Id:{} | Task Name:{} | Today Work:{} | Status:{}",new Object[]{taskform.getTaskId(),taskform.getTaskName(),taskform.getTaskComments(),taskform.getTaskStatus()});
		
		if(taskform.getTaskId() == -1){
			taskform.setEmployeeId(edbUser.getEmployeeId());
			taskform.setEmployeeName(edbUser.getEnterpriseId());
			final int taskId=getProjectWorkService().addTasks(taskform);
			taskform.setTaskId(taskId);	
		}
		
		if(taskform.getTaskReviewUser()>0){
			getProjectWorkService().assignTaskReviewer(taskform.getTaskId(), taskform.getTaskReviewUser(), "1");
			taskform.setTaskReviewUserName(projectWorkService.getEmpNameByEmpId(taskform.getTaskReviewUser()));
		}
		
		for(TaskLedgerForm ledger:taskform.getTaskLedger()){
			addTaskLedger(taskform.getTaskId(),ledger);
		}
		
		TaskForm taskData=projectWorkService.retrieveTasks();
		taskform.setTaskId(taskData.getTaskId());
		taskform.setTaskCreateDate(taskData.getTaskCreateDate());
		taskform.setWorkType("Build");
		model.addAttribute("addTaskForm", taskform);
		return "/projectwork/newTask";
	}

	private void addTaskLedger(int taskId,TaskLedgerForm taskLedgerform){
		final TaskLedgerForm ledgerForm=new TaskLedgerForm();
		ledgerForm.setTaskId(taskId);
		ledgerForm.setTaskHrs(taskLedgerform.getTaskHrs());
		ledgerForm.setTaskDvlprComments(taskLedgerform.getTaskDvlprComments());
		ledgerForm.setTaskStatus(taskLedgerform.getTaskStatus());
		getProjectWorkService().addTaskLedger(ledgerForm);
	}
	
	@RequestMapping(value = "/deleteTask.do")
	public String deleteTask(@RequestParam("taskId") int taskId,Model model) {
		
		LOG.debug("Project Name:[{--}] addTask:[{}]");
		getProjectWorkService().deleteTasks(taskId);
		return "/projectwork/newTask";
	}
	
	@RequestMapping(value = "/editTask.do")
	public @ResponseBody List<TaskForm> editTask(@RequestBody TaskForm taskform,Model model) {
		LOG.debug("Task Id:{} | Task ledger size:{} | Today review size:{} ",new Object[]{taskform.getTaskId(),taskform.getTaskLedger().size(),taskform.getTaskReviewHistory().size()});
		if(taskform.getTaskId()>0){
			for(TaskLedgerForm ledger:taskform.getTaskLedger()){
				addTaskLedger(taskform.getTaskId(),ledger);
			}
		}
		projectWorkService.addTaskReviewComments(taskform);
		projectWorkService.addTaskReviewDeveloperComments(taskform);
		return null;
	}
	
	@RequestMapping(value = "/keyIssues.do")
	public String keyIssues(@ModelAttribute("projectList") List<ReferenceData> projectList,Model model){
		model.addAttribute("projectList", getProjectList());
		return "/projectwork/keyIssues";
	}
	
	@RequestMapping(value = "/getIssues.do")
	public String getIssues(@RequestParam("projectId") Integer projectId,@RequestParam("releaseId") Integer releaseId,Model model) {
		model.addAttribute("issueList", projectWorkService.getIssues(projectId, releaseId));
		return "/projectwork/keyIssues";
	}
	
	@RequestMapping(value = "/addIssue.do")
	public void addIssue(@RequestParam("projectId") Integer projectId,@RequestParam("releaseId") Integer releaseId,@ModelAttribute("issue") Issue issue){
		getProjectWorkService().addIssue(issue, projectId, releaseId);
	}
	
	@RequestMapping(value = "/editIssue.do")
	public String editIssue(@RequestParam("projectId") Integer projectId,@RequestParam("releaseId") Integer releaseId,@ModelAttribute("issue") Issue issue,Model model){
		getProjectWorkService().editIssue(issue);
		model.addAttribute("issueList", projectWorkService.getIssues(projectId, releaseId));
		return "/projectwork/keyIssues";
	}
	
	@RequestMapping(value = "/deleteIssue.do")
	public String deleteIssue(@RequestParam("projectId") Integer projectId,@RequestParam("releaseId") Integer releaseId,@RequestParam("issueId") Integer issueId,Model model){
		getProjectWorkService().deleteIssue(issueId);
		model.addAttribute("issueList", projectWorkService.getIssues(projectId, releaseId));
		return "/projectwork/keyIssues";
	}
}
