package com.acc.tools.ed.report;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;

import org.docx4j.openpackaging.exceptions.Docx4JException;

import com.acc.tools.ed.report.dto.DMSReportData;
import com.acc.tools.ed.report.dto.ReportMasterData;
import com.acc.tools.ed.report.dto.WeeklyStatusReportData;

public interface MSWordReportTemplate {
	
	public  OutputStream generateWordWeeklyStatusReport(WeeklyStatusReportData reportData) throws IOException, Docx4JException, URISyntaxException;
	public  OutputStream generateWordWeeklyStatusReportPDF(WeeklyStatusReportData reportData) throws IOException, Docx4JException, URISyntaxException;
	public  OutputStream generateReleaseMasterReportPDF(List<WeeklyStatusReportData> releaseData) throws IOException, Docx4JException, URISyntaxException; 
	public  OutputStream generateEmployeeMasterReportPDF(List<ReportMasterData> resourceDetails) throws IOException, Docx4JException, URISyntaxException;
	public  OutputStream generateDMSPDFReport(List<DMSReportData> currentWeekData,List<DMSReportData> futureWeekData,String programName,String weekEndDate,String weekStartDate,String endDate,String startDate); 
}
