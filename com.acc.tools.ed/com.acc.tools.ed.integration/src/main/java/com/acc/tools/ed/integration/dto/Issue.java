package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class Issue implements Serializable{

	private static final long serialVersionUID = 1L;
	private int issueId;
	private String issueDesc;
	private String mtgtnPlan;
	private String openDate;
	private String endDate;
	/**
	 * @return the issueId
	 */
	public int getIssueId() {
		return issueId;
	}
	/**
	 * @param issueId the issueId to set
	 */
	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}
	/**
	 * @return the issueDesc
	 */
	public String getIssueDesc() {
		return issueDesc;
	}
	/**
	 * @param issueDesc the issueDesc to set
	 */
	public void setIssueDesc(String issueDesc) {
		this.issueDesc = issueDesc;
	}
	/**
	 * @return the openedDate
	 */
	public String getOpenDate() {
		return openDate;
	}
	/**
	 * @param openedDate the openedDate to set
	 */
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the mtgtnPlan
	 */
	public String getMtgtnPlan() {
		return mtgtnPlan;
	}
	/**
	 * @param mtgtnPlan the mtgtnPlan to set
	 */
	public void setMtgtnPlan(String mtgtnPlan) {
		this.mtgtnPlan = mtgtnPlan;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Issue [issueId=" + issueId + ", issueDesc=" + issueDesc
				+ ", mtgtnPlan=" + mtgtnPlan + ", openDate=" + openDate
				+ ", endDate=" + endDate + "]";
	}
	
}
