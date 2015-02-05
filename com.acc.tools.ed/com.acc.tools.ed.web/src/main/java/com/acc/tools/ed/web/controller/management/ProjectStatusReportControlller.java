package com.acc.tools.ed.web.controller.management;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.report.MSWordReportTemplate;
import com.acc.tools.ed.report.dto.WeeklyStatusReportData;
import com.acc.tools.ed.web.controller.common.AbstractEdbBaseController;

@Controller
@SessionAttributes({ "edbUser" }) 
public class ProjectStatusReportControlller extends AbstractEdbBaseController { 

	private static final Logger LOG = LoggerFactory
			.getLogger(ProjectStatusReportControlller.class);
	
	@Autowired
	private MSWordReportTemplate msWordReportTemplate;

	@RequestMapping(value = "/projectStatus.do")
	public String projectStatus(Model model) throws IOException {
		return "/projectmanagement/projectStatus";
	}
	

	@RequestMapping(value = "/downloadWsr.do")
	public void downloadWeeklyStatusReport(Model model,
			HttpServletResponse response) throws IOException {
		
        final ServletOutputStream outStream = response.getOutputStream();
        
        
		WeeklyStatusReportData reportData=new WeeklyStatusReportData();
		reportData.setProgramName("AO-BAS)");
		reportData.setProjectName("ACES - Premier Pricing");
		reportData.setReleaseName("Feb Release");
		reportData.setStartDate("01/01/2015");
		reportData.setEndDate("02/27/2015");
		reportData.setRevisedEndDate("");
		reportData.setStatus("Status");
		
		reportData.setMilestoneMitigation("Milestone Name  + Other Imp Details + (Status)");

		List<String> currentTasks = new ArrayList<String>();
		currentTasks.add("Task Number 1 (Completed)");
		currentTasks.add("Task number 2 (In Progress)");
		reportData.setCurrentTasks(currentTasks);

		List<String> upcomingTasks = new ArrayList<String>();
		upcomingTasks.add("TaskTodo Number 1 ");
		upcomingTasks.add("TaskTodo Number 2 ");
		reportData.setUpcomingTasks(upcomingTasks);
		
		reportData.setReportingPeriod("02/02/2015 - 02/07/2015");
		try {
			ByteArrayOutputStream output=(ByteArrayOutputStream)msWordReportTemplate.generateWordWeeklyStatusReport(reportData);
			byte[] outbytes=output.toByteArray();
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			//response.setHeader("Content-Disposition","attachment; filename=\"WeeklyStatusReport.docx\"");
			response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\" ; size=\"%d\"", "WeeklyStatusReport.docx",outbytes.length));
			outStream.write(outbytes);
			output.close();
			outStream.flush();
			outStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			LOG.error("Report Error:",e);
		} catch (Docx4JException e) {
			LOG.error("Report Error:",e);
		} catch (URISyntaxException e) {
			LOG.error("Report Error:",e);
		}
	}

	
	

}
