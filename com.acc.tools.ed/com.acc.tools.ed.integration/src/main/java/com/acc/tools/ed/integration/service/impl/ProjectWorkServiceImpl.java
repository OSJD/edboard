package com.acc.tools.ed.integration.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.ProjectWorkDao;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.TaskForm;
import com.acc.tools.ed.integration.dto.TaskLedgerForm;
import com.acc.tools.ed.integration.dto.VacationForm;
import com.acc.tools.ed.integration.service.ProjectWorkService;

@Service("projectWorkService")
public class ProjectWorkServiceImpl implements ProjectWorkService {
	
	@Autowired
	private ProjectWorkDao projectWorkDao;
	
	public int addVacation(VacationForm vacationForm){
		return projectWorkDao.addVacation(vacationForm);
	}
	
	public int approveVacation(VacationForm vacationForm){
		return projectWorkDao.approveVacation(vacationForm);
	}
	
	public List<ProjectForm> getMyTasks(Integer userId){
		return projectWorkDao.getMyTasks(userId);
	}

	public void  addTasks(TaskForm taskForm) {
		
		 projectWorkDao.addTasks(taskForm);
	}
	public List<ProjectForm> getMyTeamTasks(Integer supervisorId){
		return projectWorkDao.getMyTeamTasks(supervisorId);
	}
	
	public void deleteTasks(int taskId) {
		
		projectWorkDao.deleteTasks(taskId);
	}
	public List<TaskForm> editTasks(int taskId) {
		
		return projectWorkDao.editTasks(taskId);
	}
	public void saveTasks(TaskForm taskForm) {
		
		projectWorkDao.saveTasks(taskForm);
	}
	public TaskForm retrieveTasks() {
		
		return projectWorkDao.retrieveTasks();
	}
	
	public List<ReferenceData> getTasksByComponentId(Integer componentId){
		return projectWorkDao.getTasksByComponentId(componentId);
	}
	
	public TaskForm getTaskByTaskId(Integer taskId){
		return projectWorkDao.getTaskByTaskId(taskId);
	}
	
	public List<VacationForm> getVacationDetails(Integer employeeId){
		return projectWorkDao.getVacationDetails(employeeId);
	}
	
	public List<VacationForm> getDeveloperVacationDetails(Integer employeeId){
		return projectWorkDao.getDeveloperVacationDetails(employeeId);
	}

	public String editVacation(VacationForm vacationForm)
	{
		return projectWorkDao.editVacation(vacationForm);
	}
	
	public void deleteVacation(int vacationId)
	{
		projectWorkDao.deleteVacation(vacationId);
	}
	public List<ReferenceData> getTaskActivities(Integer taskId)
	{
		return projectWorkDao.getTaskActivities(taskId);
	}
	public void addTaskComments(int taskId, String devloperComments)
	{
		projectWorkDao.addTaskComments(taskId,devloperComments);
	}
}
