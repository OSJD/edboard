package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class TaskLedgerForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int taskLedgerId;
	private int taskId;
	private int taskHrs;
	private String taskDvlprComments;
	private String taskActivityDate;
	private String taskStatus;
	private int taskReviewUser;
	
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getTaskHrs() {
		return taskHrs;
	}
	public void setTaskHrs(int taskHrs) {
		this.taskHrs = taskHrs;
	}
	public String getTaskActivityDate() {
		return taskActivityDate;
	}
	public void setTaskActivityDate(String taskActivityDate) {
		this.taskActivityDate = taskActivityDate;
	}
	public int getTaskLedgerId() {
		return taskLedgerId;
	}
	public void setTaskLedgerId(int taskLedgerId) {
		this.taskLedgerId = taskLedgerId;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public int getTaskReviewUser() {
		return taskReviewUser;
	}
	public void setTaskReviewUser(int taskReviewUser) {
		this.taskReviewUser = taskReviewUser;
	}
	public String getTaskDvlprComments() {
		return taskDvlprComments;
	}
	public void setTaskDvlprComments(String taskDvlprComments) {
		this.taskDvlprComments = taskDvlprComments;
	}
	


}
