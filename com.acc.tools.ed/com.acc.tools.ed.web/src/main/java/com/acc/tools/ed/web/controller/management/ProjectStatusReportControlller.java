package com.acc.tools.ed.web.controller.management;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.dto.ResourceDetails;
import com.acc.tools.ed.integration.dto.SurveyQuestionnaire;
import com.acc.tools.ed.integration.dto.SurveySystem;
import com.acc.tools.ed.integration.service.ProjectReportService;
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
	
	@Autowired
	private ProjectReportService projectReportService;

	@RequestMapping(value = "/projectStatus.do")

	public String projectStatus(@ModelAttribute("statusForm") WeeklyStatusReportData statusForm,Model model) throws IOException {

		return "/projectmanagement/projectStatus";
	}


	@RequestMapping(value = "/downloadWsr.do")
	public void downloadWeeklyStatusReport(
			@ModelAttribute("statusForm") WeeklyStatusReportData statusForm,
			HttpServletResponse response,
			Model model) throws IOException {

		try
		{
			
		if(statusForm!= null)
		{
		String projectName = statusForm.getProjectName();
		String releaseName =statusForm.getReleaseName();
		String startDate =statusForm.getStartDate();
		String endDate=	statusForm.getEndDate();
		String status = statusForm.getStatus();
		String reportFormat = statusForm.getReportFormat();

		System.out.println("project name::"+projectName +"releasename::"+releaseName +"start date::"+startDate +"enddate::"+ endDate +"Status::"+ status);
		projectReportService.generateReport(projectName, releaseName, response,startDate,endDate,status,reportFormat);
		}
		else
		{
			throw new Exception("status form empty");
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	@RequestMapping(value="/reports.do")
	  public String getReport(Model model) {
		  
		model.addAttribute("statusForm",new WeeklyStatusReportData());
	    return "/reports/reports";
	  }
	
	@RequestMapping(value="/dmsreports.do")
	  public String getDMSReport(Model model) {
		
	
		model.addAttribute("programList", getProgramList());
		model.addAttribute("statusForm",new WeeklyStatusReportData());
	   
		return "/reports/dmsReport";
	  }
	
	
	@RequestMapping(value = "/downloadStatusReport.do")
	public void downloadMasterReport(
			@ModelAttribute("statusForm") WeeklyStatusReportData statusForm,
			HttpServletResponse response,
			Model model) throws IOException {

		try
		{
			
		if(statusForm!= null)
		{
		String reportName = statusForm.getReportName();
		String startDate =statusForm.getStartDate();
		String endDate=	statusForm.getEndDate();
		String reportFormat = statusForm.getReportFormat();

		System.out.println("Report name::"+reportName +"Report Format::"+reportFormat +"start date::"+startDate +"enddate::"+ endDate);
		projectReportService.generateMasterReport(response,startDate,endDate,reportFormat,reportName);
		}
		else
		{
			throw new Exception("status form empty");
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
