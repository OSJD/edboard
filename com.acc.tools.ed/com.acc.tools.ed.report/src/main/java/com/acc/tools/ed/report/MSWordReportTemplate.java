package com.acc.tools.ed.report;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import org.docx4j.openpackaging.exceptions.Docx4JException;

import com.acc.tools.ed.report.dto.WeeklyStatusReportData;

public interface MSWordReportTemplate {
	
	public  OutputStream generateWordWeeklyStatusReport(WeeklyStatusReportData reportData) throws IOException, Docx4JException, URISyntaxException;

}
