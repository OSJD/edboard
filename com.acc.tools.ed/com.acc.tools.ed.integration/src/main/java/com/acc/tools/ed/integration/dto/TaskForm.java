package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.List;

public class TaskForm implements Serializable {

	private static final long serialVersionUID = 9197621972979099410L;

	private int taskId;
	private int componentId;
	private String taskName;
	private String taskDesc;
	private int taskHrs;
	private String taskType;
	private String taskStatus;
	private String taskAction;
	private String rejComment;
	private int taskReviewUser;
	private String taskCreateDate;
	private String taskStartDate;
	private String taskEndDate;
	private String existingTask;
	private String newTaskName;
	private String newTaskId;
	private String taskComments;
	private int employeeId;
	private int reviewerId;
	private String employeeName;
	private List<TaskLedgerForm> taskLedger;
	private List<TaskReviewHistory> taskReviewHistory;
	private List<String> reviewerList;
	private String workType;
	private String taskReviewUserName;
private int userId;
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getComponentId() {
		return componentId;
	}

	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public int getTaskHrs() {
		return taskHrs;
	}

	public void setTaskHrs(int taskHrs) {
		this.taskHrs = taskHrs;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskAction() {
		return taskAction;
	}

	public void setTaskAction(String taskAction) {
		this.taskAction = taskAction;
	}

	public String getRejComment() {
		return rejComment;
	}

	public void setRejComment(String rejComment) {
		this.rejComment = rejComment;
	}

	public int getTaskReviewUser() {
		return taskReviewUser;
	}

	public void setTaskReviewUser(int taskReviewUser) {
		this.taskReviewUser = taskReviewUser;
	}

	public String getTaskCreateDate() {
		return taskCreateDate;
	}

	public void setTaskCreateDate(String taskCreateDate) {
		this.taskCreateDate = taskCreateDate;
	}

	public String getTaskStartDate() {
		return taskStartDate;
	}

	public void setTaskStartDate(String taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	public String getTaskEndDate() {
		return taskEndDate;
	}

	public void setTaskEndDate(String taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	public String getExistingTask() {
		return existingTask;
	}

	public void setExistingTask(String existingTask) {
		this.existingTask = existingTask;
	}

	public String getNewTaskName() {
		return newTaskName;
	}

	public void setNewTaskName(String newTaskName) {
		this.newTaskName = newTaskName;
	}

	public String getNewTaskId() {
		return newTaskId;
	}

	public void setNewTaskId(String newTaskId) {
		this.newTaskId = newTaskId;
	}

	public String getTaskComments() {
		return taskComments;
	}

	public void setTaskComments(String taskComments) {
		this.taskComments = taskComments;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public List<TaskLedgerForm> getTaskLedger() {
		return taskLedger;
	}

	public void setTaskLedger(List<TaskLedgerForm> taskLedger) {
		this.taskLedger = taskLedger;
	}

	public List<TaskReviewHistory> getTaskReviewHistory() {
		return taskReviewHistory;
	}

	public void setTaskReviewHistory(List<TaskReviewHistory> taskReviewHistory) {
		this.taskReviewHistory = taskReviewHistory;
	}

	public List<String> getReviewerList() {
		return reviewerList;
	}

	public void setReviewerList(List<String> reviewerList) {
		this.reviewerList = reviewerList;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getTaskReviewUserName() {
		return taskReviewUserName;
	}

	public void setTaskReviewUserName(String taskReviewUserName) {
		this.taskReviewUserName = taskReviewUserName;
	}

	public int getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(int reviewerId) {
		this.reviewerId = reviewerId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
