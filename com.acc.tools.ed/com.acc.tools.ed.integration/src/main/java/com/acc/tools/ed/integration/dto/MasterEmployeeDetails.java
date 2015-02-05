package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

import org.joda.time.DateTime;

public class MasterEmployeeDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private String employeeName;
	 private String employeeEnterpId;
	 private String employeeSAPId;
	 private String employeeRRDNo;
	 private boolean isEmployeeActive;
	 private String employeeLevel;
	 private String employeeRole;
	 private String employeeBillCode;
	 private String employeeProjectId;
	 private String employeeProjectName;
	 private String employeeWorkLocation;
	 private String employeeDeskNo;
	 private String employeeAssetTagNo;
	 private String employeeServiceTagNo;
	 private String employeeMachineCode;//EMP_780_MACHINE
	 private String employeeMachineRAMSize;//EMP_4GB_RAM  ,
	 private String employeeHardLockDate;
	 private String employeeRollOffDate;
	 private String employeePrimaryContactNo;
	 private String employeeSecondryContactNo;
	 private String accentureDOJ;
	 private String clientLANId;
	 private String employeeRSATokenNo;
	 private String employeeRSAExpiryDate;
	 private String employeeSEZId;
	 private String employeeSEZIdExpiryDate;
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeEnterpId() {
		return employeeEnterpId;
	}
	public void setEmployeeEnterpId(String employeeEnterpId) {
		this.employeeEnterpId = employeeEnterpId;
	}
	public String getEmployeeSAPId() {
		return employeeSAPId;
	}
	public void setEmployeeSAPId(String employeeSAPId) {
		this.employeeSAPId = employeeSAPId;
	}
	public String getEmployeeRRDNo() {
		return employeeRRDNo;
	}
	public void setEmployeeRRDNo(String employeeRRDNo) {
		this.employeeRRDNo = employeeRRDNo;
	}
	public boolean isEmployeeActive() {
		return isEmployeeActive;
	}
	public void setEmployeeActive(boolean isEmployeeActive) {
		this.isEmployeeActive = isEmployeeActive;
	}
	public String getEmployeeLevel() {
		return employeeLevel;
	}
	public void setEmployeeLevel(String employeeLevel) {
		this.employeeLevel = employeeLevel;
	}
	public String getEmployeeRole() {
		return employeeRole;
	}
	public void setEmployeeRole(String employeeRole) {
		this.employeeRole = employeeRole;
	}
	public String getEmployeeBillCode() {
		return employeeBillCode;
	}
	public void setEmployeeBillCode(String employeeBillCode) {
		this.employeeBillCode = employeeBillCode;
	}
	public String getEmployeeProjectId() {
		return employeeProjectId;
	}
	public void setEmployeeProjectId(String employeeProjectId) {
		this.employeeProjectId = employeeProjectId;
	}
	public String getEmployeeWorkLocation() {
		return employeeWorkLocation;
	}
	public void setEmployeeWorkLocation(String employeeWorkLocation) {
		this.employeeWorkLocation = employeeWorkLocation;
	}
	public String getEmployeeDeskNo() {
		return employeeDeskNo;
	}
	public void setEmployeeDeskNo(String employeeDeskNo) {
		this.employeeDeskNo = employeeDeskNo;
	}
	public String getEmployeeAssetTagNo() {
		return employeeAssetTagNo;
	}
	public void setEmployeeAssetTagNo(String employeeAssetTagNo) {
		this.employeeAssetTagNo = employeeAssetTagNo;
	}
	public String getEmployeeServiceTagNo() {
		return employeeServiceTagNo;
	}
	public void setEmployeeServiceTagNo(String employeeServiceTagNo) {
		this.employeeServiceTagNo = employeeServiceTagNo;
	}
	public String getEmployeeMachineCode() {
		return employeeMachineCode;
	}
	public void setEmployeeMachineCode(String employeeMachineCode) {
		this.employeeMachineCode = employeeMachineCode;
	}
	public String getEmployeeMachineRAMSize() {
		return employeeMachineRAMSize;
	}
	public void setEmployeeMachineRAMSize(String employeeMachineRAMSize) {
		this.employeeMachineRAMSize = employeeMachineRAMSize;
	}
	
	public String getEmployeeRSATokenNo() {
		return employeeRSATokenNo;
	}
	public void setEmployeeRSATokenNo(String employeeRSATokenNo) {
		this.employeeRSATokenNo = employeeRSATokenNo;
	}
	
	public void setEmployeeProjectName(String employeeProjectName) {
		this.employeeProjectName = employeeProjectName;
	}
	public String getClientLANId() {
		return clientLANId;
	}
	public void setClientLANId(String clientLANId) {
		this.clientLANId = clientLANId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEmployeeHardLockDate() {
		return employeeHardLockDate;
	}
	public void setEmployeeHardLockDate(String employeeHardLockDate) {
		this.employeeHardLockDate = employeeHardLockDate;
	}
	public String getEmployeeRollOffDate() {
		return employeeRollOffDate;
	}
	public void setEmployeeRollOffDate(String employeeRollOffDate) {
		this.employeeRollOffDate = employeeRollOffDate;
	}
	public String getEmployeePrimaryContactNo() {
		return employeePrimaryContactNo;
	}
	public void setEmployeePrimaryContactNo(String employeePrimaryContactNo) {
		this.employeePrimaryContactNo = employeePrimaryContactNo;
	}
	public String getEmployeeSecondryContactNo() {
		return employeeSecondryContactNo;
	}
	public void setEmployeeSecondryContactNo(String employeeSecondryContactNo) {
		this.employeeSecondryContactNo = employeeSecondryContactNo;
	}
	public String getAccentureDOJ() {
		return accentureDOJ;
	}
	public void setAccentureDOJ(String accentureDOJ) {
		this.accentureDOJ = accentureDOJ;
	}
	public String getEmployeeRSAExpiryDate() {
		return employeeRSAExpiryDate;
	}
	public void setEmployeeRSAExpiryDate(String employeeRSAExpiryDate) {
		this.employeeRSAExpiryDate = employeeRSAExpiryDate;
	}
	public String getEmployeeSEZId() {
		return employeeSEZId;
	}
	public void setEmployeeSEZId(String employeeSEZId) {
		this.employeeSEZId = employeeSEZId;
	}
	public String getEmployeeSEZIdExpiryDate() {
		return employeeSEZIdExpiryDate;
	}
	public void setEmployeeSEZIdExpiryDate(String employeeSEZIdExpiryDate) {
		this.employeeSEZIdExpiryDate = employeeSEZIdExpiryDate;
	}
	public String getEmployeeProjectName() {
		return employeeProjectName;
	}

}
