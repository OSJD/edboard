package com.acc.tools.ed.integration.service;

import javax.servlet.http.HttpServletResponse;

public interface ProjectReportService {

	
	
	public void generateReport(String projectName, String releaseName,HttpServletResponse response,String startDate,String endDate,String status,String reportFormat);
}
