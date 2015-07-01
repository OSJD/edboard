package com.acc.tools.ed.report.dto;

import java.io.Serializable;
import java.util.List;

public class WeeklyStatusReportData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String programName;
	private String projectName;
	private String releaseName;
	private String startDate;
	private String endDate;
	private String revisedEndDate;
	private String status;
	private String milestoneMitigation;
	private List<String> currentTasks;
	private List<String> upcomingTasks;
	private String reportingPeriod;
	private String reportFormat;
	private String reportName;
	private String releaseDesc;
	private String weekEndDate;

	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	public String getRevisedEndDate() {
		return revisedEndDate;
	}
	public void setRevisedEndDate(String revisedEndDate) {
		this.revisedEndDate = revisedEndDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMilestoneMitigation() {
		return milestoneMitigation;
	}
	public void setMilestoneMitigation(String milestoneMitigation) {
		this.milestoneMitigation = milestoneMitigation;
	}
	public List<String> getCurrentTasks() {
		return currentTasks;
	}
	public void setCurrentTasks(List<String> currentTasks) {
		this.currentTasks = currentTasks;
	}
	public List<String> getUpcomingTasks() {
		return upcomingTasks;
	}
	public void setUpcomingTasks(List<String> upcomingTasks) {
		this.upcomingTasks = upcomingTasks;
	}
	public String getReportingPeriod() {
		return reportingPeriod;
	}
	public void setReportingPeriod(String reportingPeriod) {
		this.reportingPeriod = reportingPeriod;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getReportFormat() {
		return reportFormat;
	}
	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReleaseDesc() {
		return releaseDesc;
	}
	public void setReleaseDesc(String releaseDesc) {
		this.releaseDesc = releaseDesc;
	}
	public String getWeekEndDate() {
		return weekEndDate;
	}
	public void setWeekEndDate(String weekEndDate) {
		this.weekEndDate = weekEndDate;
	}
}
