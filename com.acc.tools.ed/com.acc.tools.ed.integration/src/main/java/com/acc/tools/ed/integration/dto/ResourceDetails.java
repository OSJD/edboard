package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.List;

public class ResourceDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	private String ResourceId;
	private String employeeNumber;
	private String employeeName;
	private String contactNumber;
	private String emailId;
	private String capability;
	private String skill;
	private String level;
	private String previousLocation;
	private String auditProcTimeStamp;
	private String auditProcUser;
	private List<String> skillList;
	private String enterpriseId;
	private String role;
	private String projectStartDate;
	private String projectEndDate;
	private String resourceFlag;
	private List<String> secSkills;
	private String exisCapability;
	private String exisLevel;
	private String exisSkill;
	private List<String> exisSecSkills;
	
	
	public String getResourceId() {
		return ResourceId;
	}
	public void setResourceId(String resourceId) {
		ResourceId = resourceId;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPreviousLocation() {
		return previousLocation;
	}
	public void setPreviousLocation(String previousLocation) {
		this.previousLocation = previousLocation;
	}
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
	
	/**
	 * @return the skillList
	 */
	public List<String> getSkillList() {
		return skillList;
	}
	/**
	 * @param skillList the skillList to set
	 */
	public void setSkillList(List<String> skillList) {
		this.skillList = skillList;
	}
	/**
	 * @return the enterpriseId
	 */
	public String getEnterpriseId() {
		return enterpriseId;
	}
	/**
	 * @param enterpriseId the enterpriseId to set
	 */
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the projectStartDate
	 */
	public String getProjectStartDate() {
		return projectStartDate;
	}
	/**
	 * @param projectStartDate the projectStartDate to set
	 */
	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}
	/**
	 * @return the projectEndDate
	 */
	public String getProjectEndDate() {
		return projectEndDate;
	}
	/**
	 * @param projectEndDate the projectEndDate to set
	 */
	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	/**
	 * @return the resourceFlag
	 */
	public String getResourceFlag() {
		return resourceFlag;
	}
	/**
	 * @param resourceFlag the resourceFlag to set
	 */
	public void setResourceFlag(String resourceFlag) {
		this.resourceFlag = resourceFlag;
	}
	public List<String> getSecSkills() {
		return secSkills;
	}
	public void setSecSkills(List<String> secSkills) {
		this.secSkills = secSkills;
	}
	public String getExisCapability() {
		return exisCapability;
	}
	public void setExisCapability(String exisCapability) {
		this.exisCapability = exisCapability;
	}
	public String getExisLevel() {
		return exisLevel;
	}
	public void setExisLevel(String exisLevel) {
		this.exisLevel = exisLevel;
	}
	public String getExisSkill() {
		return exisSkill;
	}
	public void setExisSkill(String exisSkill) {
		this.exisSkill = exisSkill;
	}
	public List<String> getExisSecSkills() {
		return exisSecSkills;
	}
	public void setExisSecSkills(List<String> exisSecSkills) {
		this.exisSecSkills = exisSecSkills;
	}
	
	

}
