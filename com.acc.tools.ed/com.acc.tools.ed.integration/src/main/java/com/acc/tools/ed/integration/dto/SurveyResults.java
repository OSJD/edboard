package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class SurveyResults implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String empName;
	private int score;
	private int timeTaken;
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(int timeTaken) {
		this.timeTaken = timeTaken;
	}
	

}
