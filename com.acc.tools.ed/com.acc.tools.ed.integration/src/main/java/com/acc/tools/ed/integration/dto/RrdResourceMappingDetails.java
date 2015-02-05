package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.List;

public class RrdResourceMappingDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	
	 private String  rrdNumber;
	 private String employeeNumber;
	 private String employeeLockDate;
	 private String employeeReleaseDate;
	 private boolean isHardLocked;
	 private boolean isDeleted;
	 private String auditProcTimeStamp;
	 private String auditProcUser;
	 private List<ResourceDetails> resourceDetails;
	private List<RrdDetails> rrdDetails;
	
	public List<ResourceDetails> getResourceDetails() {
		return resourceDetails;
	}
	public void setResourceDetails(List<ResourceDetails> resourceDetails) {
		this.resourceDetails = resourceDetails;
	}
	public List<RrdDetails> getRrdDetails() {
		return rrdDetails;
	}
	public void setRrdDetails(List<RrdDetails> rrdDetails) {
		this.rrdDetails = rrdDetails;
	}
	public String getRrdNumber() {
		return rrdNumber;
	}
	public void setRrdNumber(String rrdNumber) {
		this.rrdNumber = rrdNumber;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	
	public String getEmployeeLockDate() {
		return employeeLockDate;
	}
	public void setEmployeeLockDate(String employeeLockDate) {
		this.employeeLockDate = employeeLockDate;
	}
	public String getEmployeeReleaseDate() {
		return employeeReleaseDate;
	}
	public void setEmployeeReleaseDate(String employeeReleaseDate) {
		this.employeeReleaseDate = employeeReleaseDate;
	}
	public boolean getIsHardLocked() {
		return isHardLocked;
	}
	public void setHardLocked(boolean isHardLocked) {
		this.isHardLocked = isHardLocked;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
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
	 
}
