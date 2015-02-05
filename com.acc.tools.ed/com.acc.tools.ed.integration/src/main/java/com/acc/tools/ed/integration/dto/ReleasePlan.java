package com.acc.tools.ed.integration.dto;

import java.util.List;
import java.util.Map;

public class ReleasePlan {
		
	//property which holds resource and their hours
	private Map<ReferenceData,List<Long>> resourcesAndHours; 
	
	//property which holds month and its count of Days in the month
	private Map<String,Long> monthsNoOfDays;

	//property which holds Week No and its days Ex:(Week-1,List<Fri-1,Sat-2>)
	private Map<String,List<String>> weeksAndDays;
	
	private List<String> weeklyTotalHours;

	public List<String> getWeeklyTotalHours() {
		return weeklyTotalHours;
	}

	public void setWeeklyTotalHours(List<String> weeklyTotalHours) {
		this.weeklyTotalHours = weeklyTotalHours;
	}

	public Map<ReferenceData, List<Long>> getResourcesAndHours() {
		return resourcesAndHours;
	}

	public void setResourcesAndHours(Map<ReferenceData, List<Long>> resourcesAndHours) {
		this.resourcesAndHours = resourcesAndHours;
	}

	public Map<String, List<String>> getWeeksAndDays() {
		return weeksAndDays;
	}

	public void setWeeksAndDays(Map<String, List<String>> weeksAndDays) {
		this.weeksAndDays = weeksAndDays;
	}
	public Map<String, Long> getMonthsNoOfDays() {
		return monthsNoOfDays;
	}

	public void setMonthsNoOfDays(Map<String, Long> monthsNoOfDays) {
		this.monthsNoOfDays = monthsNoOfDays;
	}
	
}
