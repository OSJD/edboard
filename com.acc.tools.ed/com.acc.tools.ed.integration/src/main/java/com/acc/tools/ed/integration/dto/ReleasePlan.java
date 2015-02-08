package com.acc.tools.ed.integration.dto;

import java.io.Serializable;
import java.util.List;

public class ReleasePlan implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResourceWorkPlan releasePlanHeader;
	private List<ResourceWorkPlan> resourceWorkPlan;
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
		
	
	
}
