package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class Skill implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String capabilityName;
	private String skillName;
	private String existingSkill;

	public String getCapabilityName() {
		return capabilityName;
	}
	public void setCapabilityName(String capabilityName) {
		this.capabilityName = capabilityName;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public String getExistingSkill() {
		return existingSkill;
	}
	public void setExistingSkill(String existingSkill) {
		this.existingSkill = existingSkill;
	}
	
	
	
}
