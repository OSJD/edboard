package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class Skill implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String skillCategory;
	private String skillName;
	
	public String getSkillCategory() {
		return skillCategory;
	}
	public void setSkillCategory(String skillCategory) {
		this.skillCategory = skillCategory;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	
	
}
