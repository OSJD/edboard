package com.acc.tools.ed.integration.dao;

import javax.servlet.http.HttpServletResponse;

public interface ProjectReportDao {



	public void generateReport(String projectName, String releaseName,
			HttpServletResponse response, String startDate, String endDate,
			String status);



}
