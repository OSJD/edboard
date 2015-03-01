package com.acc.tools.ed.integration.service;

import java.util.List;

import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.TaskForm;
import com.acc.tools.ed.integration.dto.VacationForm;

public interface ProjectWorkService {
	
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
}

