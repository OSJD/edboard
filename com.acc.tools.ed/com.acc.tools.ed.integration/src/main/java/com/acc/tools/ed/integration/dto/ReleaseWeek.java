package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

import org.joda.time.LocalDate;

public class ReleaseWeek implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalDate weekStart;
	private LocalDate weekEnd;
	private Long[] hours=new Long[7];
	public LocalDate getWeekStart() {
		return weekStart;
	}
	public void setWeekStart(LocalDate weekStart) {
		this.weekStart = weekStart;
	}
	public LocalDate getWeekEnd() {
		return weekEnd;
	}
	public void setWeekEnd(LocalDate weekEnd) {
		this.weekEnd = weekEnd;
	}
	public Long[] getHours() {
		return hours;
	}
	public void setHours(Long[] hours) {
		this.hours = hours;
	}


}
