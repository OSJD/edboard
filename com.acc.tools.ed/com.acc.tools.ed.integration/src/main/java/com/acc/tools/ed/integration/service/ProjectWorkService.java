package com.acc.tools.ed.integration.service;

import java.util.List;

import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.TaskForm;

public interface ProjectWorkService {
	
	public List<ProjectForm> getMyTasks(String userId);
	public void addTasks(TaskForm taskForm);
	public List<ProjectForm> getMyTeamTasks(String supervisorId);
	public void deleteTasks(int taskId);
	public List<TaskForm> editTasks(int taskId);
	public void saveTasks(TaskForm taskForm);
	public TaskForm retrieveTasks();

}

