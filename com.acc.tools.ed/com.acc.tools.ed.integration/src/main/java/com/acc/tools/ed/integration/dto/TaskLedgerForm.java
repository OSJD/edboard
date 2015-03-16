package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class TaskLedgerForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int taskId;
	private int taskHrs;
	private String taskComments;
	
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
	public String getTaskComments() {
		return taskComments;
	}
	public void setTaskComments(String taskComments) {
		this.taskComments = taskComments;
	}

}
