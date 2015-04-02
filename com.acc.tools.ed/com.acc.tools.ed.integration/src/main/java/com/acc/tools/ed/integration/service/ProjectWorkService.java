package com.acc.tools.ed.integration.service;

import java.util.List;

import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.TaskForm;
import com.acc.tools.ed.integration.dto.TaskLedgerForm;
import com.acc.tools.ed.integration.dto.VacationForm;

public interface ProjectWorkService {
	
	public List<ProjectForm> getMyTasks(Integer userId);
	public int addTasks(TaskForm taskForm);
	public List<ProjectForm> getMyTeamTasks(Integer supervisorId);
	public void deleteTasks(int taskId);
	public List<TaskForm> editTasks(TaskForm taskForm);
	public void saveTasks(TaskForm taskForm);
	public TaskForm retrieveTasks();
	public List<ReferenceData> getTasksByComponentId(Integer componentId,Integer employeeId);
	public TaskForm getTaskByTaskId(Integer taskId);
	public int addVacation(VacationForm vacationForm);
	public int addHoliday(VacationForm vacationForm);
	public List<VacationForm> getVacationDetailsBySupervisorId(Integer employeeId,Integer loginUserId);
	public List<VacationForm> getVacationDetailsByEmployeeId(Integer employeeId,Integer loginUserId);
	public List<VacationForm> getHolidays();
	public int approveVacation(VacationForm vacationForm);
	public void deleteVacation(int vacationId);
	public String editVacation(VacationForm vacationForm);
	public void addTaskLedger(TaskLedgerForm ledgerForm);
	public void assignTaskReviewer(Integer taskId,Integer reviewerId,String status);

}

