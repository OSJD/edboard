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
	private String taskActivity;
	private String taskActivityDate;
	
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
	public String getTaskActivity() {
		return taskActivity;
	}
	public void setTaskActivity(String taskActivity) {
		this.taskActivity = taskActivity;
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
	


}
