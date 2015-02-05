package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class SurveyQuestionnaireOptions implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int optionId;
	private int optionOrder;
	private int optionValue;
	private String optionDescription;
	public int getOptionId() {
		return optionId;
	}
	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}
	public String getOptionDescription() {
		return optionDescription;
	}
	public void setOptionDescription(String optionDescription) {
		this.optionDescription = optionDescription;
	}
	public int getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(int optionValue) {
		this.optionValue = optionValue;
	}
	public int getOptionOrder() {
		return optionOrder;
	}
	public void setOptionOrder(int optionOrder) {
		this.optionOrder = optionOrder;
	}

}
