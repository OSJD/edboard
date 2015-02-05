package com.acc.tools.ed.integration.dto;


/**
 * @author nikhil.jagtiani
 *
 */
public class HrsResource{
	int hrs;
	String status;
	String comment;
	int actl_frn_id;
	String enterprise_id="";
	String week;
public String getEnterprise_id() {
		return enterprise_id;
	}
	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}
	String desc;
public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
public int getActl_frn_id() {
	return actl_frn_id;
}
public void setActl_frn_id(int actl_frn_id) {
	this.actl_frn_id = actl_frn_id;
}

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}
String date;
int Actl_Tsk_Id;
String taskName;
public String getTaskName() {
	return taskName;
}
public void setTaskName(String taskName) {
	this.taskName = taskName;
}
public int getHrs() {
	return hrs;
}
public void setHrs(int hrs) {
	this.hrs = hrs;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public int getActl_Tsk_Id() {
	return Actl_Tsk_Id;
}
public void setActl_Tsk_Id(int actl_Tsk_Id) {
	Actl_Tsk_Id = actl_Tsk_Id;
}
public String getWeek() {
	return week;
}
public void setWeek(String week) {
	this.week = week;
}




}
