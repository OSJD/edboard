package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class LowLevelTask implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String stDate;
	private String endDate;
	private int llid;
	private String desc;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStDate() {
		return stDate;
	}
	public void setStDate(String stDate) {
		this.stDate = stDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getLlid() {
		return llid;
	}
	public void setLlid(int llid) {
		this.llid = llid;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	

}
