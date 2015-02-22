package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.Map;

import org.joda.time.DateTime;

public class WeekDates implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DateTime weekStartDate;
	private DateTime weekEndDate;
	private Map<String,DayEvent> dayEventMap;
	
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
	public Map<String, DayEvent> getDayEventMap() {
		return dayEventMap;
	}
	public void setDayEventMap(Map<String, DayEvent> dayEventMap) {
		this.dayEventMap = dayEventMap;
	}
}
