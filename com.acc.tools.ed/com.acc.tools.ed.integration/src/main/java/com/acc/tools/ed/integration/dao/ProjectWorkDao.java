package com.acc.tools.ed.integration.dao;

import java.util.List;

import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.TaskForm;
import com.acc.tools.ed.integration.dto.TaskLedgerForm;
import com.acc.tools.ed.integration.dto.VacationForm;

public interface ProjectWorkDao {

	public List<ProjectForm> getMyTasks(Integer userId);
	public Integer addTasks(TaskForm taskForm);
	public List<ProjectForm> getMyTeamTasks(Integer supervisorId);
	public void deleteTasks(int taskId);
	public int[] addTaskReviewComments(TaskForm taskform);
	public int[] addTaskReviewDeveloperComments(TaskForm taskForm);
	public TaskForm retrieveTasks();
	public List<ReferenceData> getTasksByComponentId(Integer componentId,Integer employeeId);
	public TaskForm getTaskByTaskId(Integer taskId);
	public int addVacation(VacationForm vacationForm);
	public int addHoliday(VacationForm vacationForm);
	public List<VacationForm> getVacationDetailsBySupervisorId(Integer employeeId);
	public List<VacationForm> getVacationDetailsByEmployeeId(Integer employeeId);
	public List<VacationForm> getHolidays();
	public int approveVacation(VacationForm vacationForm);
	public void deleteVacation(int vacationId);
	public String editVacation(VacationForm vacationForm);
	public void addTaskLedger(TaskLedgerForm ledgerForm);
	public void assignTaskReviewer(Integer taskId,Integer reviewerId,String status);
	public String getEmpNameByEmpId(Integer employeeId);
	
}
