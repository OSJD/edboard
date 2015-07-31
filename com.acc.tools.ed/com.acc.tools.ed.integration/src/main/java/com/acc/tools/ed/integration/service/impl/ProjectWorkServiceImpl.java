package com.acc.tools.ed.integration.service.impl;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.ProjectWorkDao;
import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.Issue;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.TaskForm;
import com.acc.tools.ed.integration.dto.TaskLedgerForm;
import com.acc.tools.ed.integration.dto.VacationForm;
import com.acc.tools.ed.integration.service.ProjectWorkService;

@Service("projectWorkService")
public class ProjectWorkServiceImpl implements ProjectWorkService {
	public ProjectWorkServiceImpl(){
		setTaskStatus();
	}
private Map<String,String> taskStatus=new LinkedHashMap<String, String>();
	@Autowired
	private ProjectWorkDao projectWorkDao;
	public void  setTaskStatus(){
	taskStatus.put("1", "Build Completed");
		taskStatus.put("2", "Build In Progress");
		taskStatus.put("3", "Build On Hold");
		taskStatus.put("4", "Review Completed");
		taskStatus.put("5", "Review In Progress");
		taskStatus.put("6", "Review On Hold");
		taskStatus.put("7", "Rework Completed");
		taskStatus.put("8", "Rework In Progress");
		taskStatus.put("9", "Rework On Hold");
		taskStatus.put("10", "Task Closed");
		/*taskStatus.put("11", "Review Completed");
		taskStatus.put("12", "Review In Progress");
		taskStatus.put("13", "Review On Hold");*/
	}
	public Map<String,String>   getTaskStatus(){
		
		return taskStatus;
	}
	public int addVacation(VacationForm vacationForm){
		return projectWorkDao.addVacation(vacationForm);
	}
	
	public int addHoliday(VacationForm vacationForm){
		return projectWorkDao.addHoliday(vacationForm);
	}
	
	public int approveVacation(VacationForm vacationForm){
		return projectWorkDao.approveVacation(vacationForm);
	}
	
public List<ProjectForm> getMyTasks(Integer userId){
		
		List<ProjectForm> projects=projectWorkDao.getMyTasks(userId);
		setPercentage(projects);
		return projects;
	}

	public int  addTasks(TaskForm taskForm) {
		 return projectWorkDao.addTasks(taskForm);
	}
	
	public List<ProjectForm> getMyTeamTasks(Integer supervisorId){
		 List<ProjectForm> projects=projectWorkDao.getMyTeamTasks(supervisorId);
		setPercentage(projects);
		return projects;
	}
	
	public void deleteTasks(int taskId) {
		
		projectWorkDao.deleteTasks(taskId);
	}
	public int[] addTaskReviewComments(TaskForm taskForm) {
		
		return projectWorkDao.addTaskReviewComments(taskForm);
	}
	public int[] addTaskReviewDeveloperComments(TaskForm taskForm){
		
		return projectWorkDao.addTaskReviewDeveloperComments(taskForm);
	}
	public TaskForm retrieveTasks() {
		
		return projectWorkDao.retrieveTasks();
	}
	
	public List<ReferenceData> getTasksByComponentId(Integer componentId,Integer employeeId){
		return projectWorkDao.getTasksByComponentId(componentId,employeeId);
	}
	
	public TaskForm getTaskByTaskId(Integer taskId){
		return projectWorkDao.getTaskByTaskId(taskId);
	}
	public List<VacationForm> getVacationDetailsBySupervisorId(Integer employeeId){
		return projectWorkDao.getVacationDetailsBySupervisorId(employeeId);
	}

	public List<VacationForm> getVacationDetailsByEmployeeId(Integer employeeId){
		return projectWorkDao.getVacationDetailsByEmployeeId(employeeId);
	}
	public List<VacationForm> getHolidays(){
		return projectWorkDao.getHolidays();
	}
	public String editVacation(VacationForm vacationForm){
		return projectWorkDao.editVacation(vacationForm);
	}
	
	public void deleteVacation(int vacationId){
		projectWorkDao.deleteVacation(vacationId);
	}

	public void addTaskLedger(TaskLedgerForm ledgerForm){
		projectWorkDao.addTaskLedger(ledgerForm);
	}
	
	public void assignTaskReviewer(Integer taskId,Integer reviewerId,String status){
		projectWorkDao.assignTaskReviewer(taskId, reviewerId, status);
	}

	public String getEmpNameByEmpId(Integer empId) {
		return projectWorkDao.getEmpNameByEmpId(empId);
	}
	
	public List<Issue> getIssues(Integer projectId, Integer releaseId) {
		return projectWorkDao.getIssues(projectId, releaseId);
	}
	
	public void addIssue(Issue issue,Integer projectId,Integer releaseId) {
		projectWorkDao.addIssue(issue, projectId, releaseId);
	}
	
	public void editIssue(Issue issue) {
		projectWorkDao.editIssue(issue);
	}
	
	public void deleteIssue(Integer issueId) {
		projectWorkDao.deleteIssue(issueId);
	}
		public void setPercentage(List<ProjectForm> projects){
		for(ProjectForm project:projects){
			List<ReleaseForm> releases=	project.getReleases();
			float totalPercentage=0;
			for(ReleaseForm releaseForm:releases){
				
			List<ComponentForm> components=	releaseForm.getComponents();
			if(components!=null){
				int noOfComponents=components.size();
			for(ComponentForm componentForm:components){
				List<TaskForm> tasks=componentForm.getTaskFormList();
				float noOfTasksCompleted=0;
				float percentage=0;
				if(tasks!=null){
				for(TaskForm taskForm:tasks){
					taskForm.setTaskStatus(taskStatus.get(taskForm.getTaskStatus()));
					if("Task Closed".equals(taskForm.getTaskStatus())){
						noOfTasksCompleted++;
					}
				}
				int total=tasks.size();
				percentage=(noOfTasksCompleted/total)*100;
				totalPercentage=totalPercentage+percentage;
				}
				componentForm.setPercentage((new Float(percentage)).intValue());
			}
			releaseForm.setPercentage((new Float(totalPercentage/noOfComponents)).intValue());
			}
			}
			}
	}
		public void deleteComponent(int componentId) {
			projectWorkDao.deleteComponent(componentId);
			
		}
}
