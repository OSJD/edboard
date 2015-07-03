package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * This class will hold user information
 * 
 * @author murali.k.gavarasana
 *
 */

public class EDBUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer employeeId;
	private Integer sapId;
	private String enterpriseId;
	private String role;
	private String level;
	private Integer supervisorId;
	private Integer projectId;
	private String projectName;
	private Boolean logout;
	private Long lastLoginTime;
	private String password;
	private String passwordFlag;
	

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getSapId() {
		return sapId;
	}

	public void setSapId(Integer sapId) {
		this.sapId = sapId;
	}

	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Boolean getLogout() {
		return logout;
	}

	public void setLogout(Boolean logout) {
		this.logout = logout;
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordFlag() {
		return passwordFlag;
	}

	public void setPasswordFlag(String passwordFlag) {
		this.passwordFlag = passwordFlag;
	}

	
}
