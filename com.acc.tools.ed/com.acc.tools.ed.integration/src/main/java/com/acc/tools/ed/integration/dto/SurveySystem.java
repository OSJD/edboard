package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.List;

public class SurveySystem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int employeeId;
	private Integer announcementSubjectId;
	private String announcementSubject;
	private String announcementCreateDate;
	private String announcementHTMLData;
	private List<SurveyQuestionnaire> questionnaire;
	private int timeTaken;
	private boolean setTime=false;
	private boolean announcementPublished=false;
	private String quizStartDateTime;
	private int stopWatch;
	private boolean quizAttempted;
	
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public List<SurveyQuestionnaire> getQuestionnaire() {
		return questionnaire;
	}
	public void setQuestionnaire(List<SurveyQuestionnaire> questionnaire) {
		this.questionnaire = questionnaire;
	}
	public int getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(int timeTaken) {
		this.timeTaken = timeTaken;
	}
	public String getAnnouncementHTMLData() {
		return announcementHTMLData;
	}
	public void setAnnouncementHTMLData(String announcementHTMLData) {
		this.announcementHTMLData = announcementHTMLData;
	}
	public boolean isSetTime() {
		return setTime;
	}
	public void setSetTime(boolean setTime) {
		this.setTime = setTime;
	}
	public String getQuizStartDateTime() {
		return quizStartDateTime;
	}
	public void setQuizStartDateTime(String quizStartDateTime) {
		this.quizStartDateTime = quizStartDateTime;
	}
	public boolean isAnnouncementPublished() {
		return announcementPublished;
	}
	public void setAnnouncementPublished(boolean announcementPublished) {
		this.announcementPublished = announcementPublished;
	}
	public String getAnnouncementSubject() {
		return announcementSubject;
	}
	public void setAnnouncementSubject(String announcementSubject) {
		this.announcementSubject = announcementSubject;
	}
	public String getAnnouncementCreateDate() {
		return announcementCreateDate;
	}
	public void setAnnouncementCreateDate(String announcementCreateDate) {
		this.announcementCreateDate = announcementCreateDate;
	}
	public Integer getAnnouncementSubjectId() {
		return announcementSubjectId;
	}
	public void setAnnouncementSubjectId(Integer announcementSubjectId) {
		this.announcementSubjectId = announcementSubjectId;
	}
	public int getStopWatch() {
		return stopWatch;
	}
	public void setStopWatch(int stopWatch) {
		this.stopWatch = stopWatch;
	}
	public boolean isQuizAttempted() {
		return quizAttempted;
	}
	public void setQuizAttempted(boolean quizAttempted) {
		this.quizAttempted = quizAttempted;
	}

}
