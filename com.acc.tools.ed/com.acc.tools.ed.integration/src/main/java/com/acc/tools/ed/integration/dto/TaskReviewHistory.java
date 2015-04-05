package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class TaskReviewHistory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int taskId;
	private int reviewHistoryId;
	private String reviewComment;
	private String devResponse;
	private String isReviewValid;
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getReviewComment() {
		return reviewComment;
	}
	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}
	public String getDevResponse() {
		return devResponse;
	}
	public void setDevResponse(String devResponse) {
		this.devResponse = devResponse;
	}

	public int getReviewHistoryId() {
		return reviewHistoryId;
	}
	public void setReviewHistoryId(int reviewHistoryId) {
		this.reviewHistoryId = reviewHistoryId;
	}
	public String getIsReviewValid() {
		return isReviewValid;
	}
	public void setIsReviewValid(String isReviewValid) {
		this.isReviewValid = isReviewValid;
	}

}
