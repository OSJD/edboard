package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class RrdDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String rrdDetailsId;
	private String rrdNumber;
	private String level;
	private String  capability;
	private String skill;
	private boolean isReadyToHardlock;
	private boolean isDeleted;
	private boolean isActive;
	private String rrdStartDate;
	private String rrdEndDate;
	private String project;
	private String engagement;
	private String auditProcTimeStamp;
	private String auditProcUser;
	
	
	public String getAuditProcTimeStamp() {
		return auditProcTimeStamp;
	}
	public void setAuditProcTimeStamp(String auditProcTimeStamp) {
		this.auditProcTimeStamp = auditProcTimeStamp;
	}
	public String getAuditProcUser() {
		return auditProcUser;
	}
	public void setAuditProcUser(String auditProcUser) {
		this.auditProcUser = auditProcUser;
	}
	public String getRrdDetailsId() {
		return rrdDetailsId;
	}
	public void setRrdDetailsId(String rrdDetailsId) {
		this.rrdDetailsId = rrdDetailsId;
	}
	public String getRrdNumber() {
		return rrdNumber;
	}
	public void setRrdNumber(String rrdNumber) {
		this.rrdNumber = rrdNumber;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCapability() {
		return capability;
	}
	public void setCapability(String capability) {
		this.capability = capability;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public boolean isReadyToHardlock() {
		return isReadyToHardlock;
	}
	public void setReadyToHardlock(boolean isReadyToHardlock) {
		this.isReadyToHardlock = isReadyToHardlock;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getRrdStartDate() {
		return rrdStartDate;
	}
	public void setRrdStartDate(String rrdStartDate) {
		this.rrdStartDate = rrdStartDate;
	}
	public String getRrdEndDate() {
		return rrdEndDate;
	}
	public void setRrdEndDate(String rrdEndDate) {
		this.rrdEndDate = rrdEndDate;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getEngagement() {
		return engagement;
	}
	public void setEngagement(String engagement) {
		this.engagement = engagement;
	}
	
	 
}
