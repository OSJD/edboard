package com.acc.tools.ed.integration.dao;

import java.util.List;

import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.TaskForm;
import com.acc.tools.ed.integration.dto.VacationForm;

public interface ProjectWorkDao {

	public List<ProjectForm> getMyTasks(Integer userId);
	public void addTasks(TaskForm taskForm);
	public List<ProjectForm> getMyTeamTasks(Integer supervisorId);
	public void deleteTasks(int taskId);
	public List<TaskForm> editTasks(int taskId);
	public void saveTasks(TaskForm taskForm);
	public TaskForm retrieveTasks();
	public List<ReferenceData> getTasksByComponentId(Integer componentId);
	public TaskForm getTaskByTaskId(Integer taskId);
	public int addVacation(VacationForm vacationForm);
	public List<VacationForm> getVacationDetails(Integer employeeId);
	public int approveVacation(VacationForm vacationForm);
	public List<VacationForm> getDeveloperVacationDetails(Integer employeeId);
	public void deleteVacation(int vacationId);
	public String editVacation(VacationForm vacationForm);
	public void addTaskComments(int taskId, String devloperComments);
	
	
}
