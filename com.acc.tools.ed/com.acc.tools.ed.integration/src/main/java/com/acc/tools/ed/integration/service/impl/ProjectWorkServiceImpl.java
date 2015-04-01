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
	
	public int addHoliday(VacationForm vacationForm){
		return projectWorkDao.addHoliday(vacationForm);
	}
	
	public int approveVacation(VacationForm vacationForm){
		return projectWorkDao.approveVacation(vacationForm);
	}
	
	public List<ProjectForm> getMyTasks(Integer userId){
		return projectWorkDao.getMyTasks(userId);
	}

	public int  addTasks(TaskForm taskForm) {
		 return projectWorkDao.addTasks(taskForm);
	}
	
	public List<ProjectForm> getMyTeamTasks(Integer supervisorId){
		return projectWorkDao.getMyTeamTasks(supervisorId);
	}
	
	public void deleteTasks(int taskId) {
		
		projectWorkDao.deleteTasks(taskId);
	}
	public List<TaskForm> editTasks(TaskForm taskForm) {
		
		return projectWorkDao.editTasks(taskForm);
	}
	public void saveTasks(TaskForm taskForm) {
		
		projectWorkDao.saveTasks(taskForm);
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
	public List<VacationForm> getVacationDetailsBySupervisorId(Integer employeeId,Integer loginUserId){
		return projectWorkDao.getVacationDetailsBySupervisorId(employeeId,loginUserId);
	}

	public List<VacationForm> getVacationDetailsByEmployeeId(Integer employeeId,Integer loginUserId){
		return projectWorkDao.getVacationDetailsByEmployeeId(employeeId,loginUserId);
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
}
