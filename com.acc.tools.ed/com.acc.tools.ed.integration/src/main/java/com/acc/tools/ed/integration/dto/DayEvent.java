package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class DayEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int day;
	private String date;
	private String eventDescription;
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	

}
