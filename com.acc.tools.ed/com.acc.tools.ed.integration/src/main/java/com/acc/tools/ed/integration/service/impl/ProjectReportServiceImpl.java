package com.acc.tools.ed.integration.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.service.ProjectReportService;
import com.acc.tools.ed.integration.dao.ProjectReportDao;

@Service("ProjectReportService")
public class ProjectReportServiceImpl implements ProjectReportService{

	private static final Logger LOG = LoggerFactory.getLogger(ProjectReportServiceImpl.class);

	@Autowired
	private ProjectReportDao projectReportDao;


	public void generateReport(String projectName, String releaseName,HttpServletResponse response,String startDate,String endDate,String status)

	{
		try{
			System.out.println("passing the values from impl");
			projectReportDao.generateReport(projectName, releaseName, response, startDate, endDate,status);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
