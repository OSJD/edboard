package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReleasePlan implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResourceWorkPlan releasePlanHeader;
	private List<ResourceWorkPlan> resourceWorkPlan;
	private Map<String,Integer> weakPlannedWorkHours=new LinkedHashMap<String, Integer>();
	public ResourceWorkPlan getReleasePlanHeader() {
		return releasePlanHeader;
	}
	public void setReleasePlanHeader(ResourceWorkPlan releasePlanHeader) {
		this.releasePlanHeader = releasePlanHeader;
	}
	public List<ResourceWorkPlan> getResourceWorkPlan() {
		return resourceWorkPlan;
	}
	public void setResourceWorkPlan(List<ResourceWorkPlan> resourceWorkPlan) {
		this.resourceWorkPlan = resourceWorkPlan;
	}
	public Map<String, Integer> getWeakPlannedWorkHours() {
		return weakPlannedWorkHours;
	}
	public void setWeakPlannedWorkHours(Map<String, Integer> weakPlannedWorkHours) {
		this.weakPlannedWorkHours = weakPlannedWorkHours;
	}
		
	
	
}
