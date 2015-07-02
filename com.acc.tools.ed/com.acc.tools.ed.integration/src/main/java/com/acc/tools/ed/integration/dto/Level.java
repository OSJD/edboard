package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class Level implements Serializable{
	private static final long serialVersionUID = 1L;
	private String levelName;
	private String existingLevel;
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getExistingLevel() {
		return existingLevel;
	}
	public void setExistingLevel(String existingLevel) {
		this.existingLevel = existingLevel;
	}
	
	
}
