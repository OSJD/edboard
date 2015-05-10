package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * This class will hold user information
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
	private long lastLoginDB;
	
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
	public long getLastLoginDB() {
		return lastLoginDB;
	}
	public void setLastLoginDB(long lastLoginDB) {
		this.lastLoginDB = lastLoginDB;
	}


}
