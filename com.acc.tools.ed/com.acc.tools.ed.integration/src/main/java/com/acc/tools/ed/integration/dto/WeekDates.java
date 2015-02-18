package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

import org.joda.time.DateTime;

public class WeekDates implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DateTime weekStartDate;
	private DateTime weekEndDate;
	public DateTime getWeekStartDate() {
		return weekStartDate;
	}
	public void setWeekStartDate(DateTime weekStartDate) {
		this.weekStartDate = weekStartDate;
	}
	public DateTime getWeekEndDate() {
		return weekEndDate;
	}
	public void setWeekEndDate(DateTime weekEndDate) {
		this.weekEndDate = weekEndDate;
	}

}
