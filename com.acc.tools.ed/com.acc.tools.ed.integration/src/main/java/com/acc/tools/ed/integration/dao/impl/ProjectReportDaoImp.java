package com.acc.tools.ed.integration.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.ProjectReportDao;
import com.acc.tools.ed.integration.util.CalendarEnum;
import com.acc.tools.ed.report.MSWordReportTemplate;
import com.acc.tools.ed.report.dto.DMSReportData;
import com.acc.tools.ed.report.dto.ReportMasterData;
import com.acc.tools.ed.report.dto.WeeklyStatusReportData;

@Service("projectReportDao")
public class ProjectReportDaoImp extends AbstractEdbDao implements ProjectReportDao{

	Logger LOG=LoggerFactory.getLogger(ProjectReportDaoImp.class);

	@Autowired
	private MSWordReportTemplate msWordReportTemplate;

	public void getAccomplishments(Integer releaseId, Integer projectId ,Integer programId,HttpServletResponse response,String startDate,String endDate,String status,String reportFormat) {


		WeeklyStatusReportData reportData=new WeeklyStatusReportData();
		final StringBuffer projPlanQuery =new StringBuffer();


		projPlanQuery.append("SELECT P.*, M.*, C.*,CE.*, PRG.*,E.EMP_ENTERPRISE_ID,E.EMP_RESOURCE_NAME FROM ((((EDB_PROJECT AS P LEFT JOIN EDB_MILESTONE AS M ON P.PROJ_ID = M.PROJ_ID) ");
		projPlanQuery.append("LEFT JOIN EDB_PROJ_COMPNT AS C ON M.MLSTN_ID = C.MLSTN_ID) "); 
		projPlanQuery.append("LEFT JOIN EDB_COMPNT_EMP AS CE ON CE.COMPNT_ID=C.COMPNT_ID ) ");
		projPlanQuery.append("LEFT JOIN EDB_PROGRAM AS PRG ON PRG.PRGM_ID =P.PRGM_ID ) ");
		projPlanQuery.append("LEFT JOIN EDB_MSTR_EMP_DTLS E ON E.EMP_ID=CE.EMP_ID WHERE M.MLSTN_ID="+releaseId+" AND P.PROJ_ID="+projectId +" AND PRG.PRGM_ID ="+programId);


		LOG.debug("RELEASE QUERY :[{}]",projPlanQuery);



		try {

			final ServletOutputStream outStream = response.getOutputStream();

			PreparedStatement  preparedStatement = getConnection().prepareStatement(projPlanQuery.toString());
			ResultSet rs = preparedStatement.executeQuery();



			while(rs.next()){

				reportData.setProgramName(rs.getString("PRGM_NAME"));
				reportData.setProjectName(rs.getString("PROJ_NAME"));
				reportData.setReleaseName(rs.getString("MLSTN_NAME"));
				reportData.setStartDate(startDate);
				reportData.setEndDate(endDate);
				reportData.setRevisedEndDate("");
				reportData.setStatus(status);
				String milestone = rs.getString("MLSTN_DESC");
				reportData.setMilestoneMitigation(milestone); 
				System.out.println("final results::"+ milestone );

				List<String> currentTasks = new ArrayList<String>();
				currentTasks.add("Task Number 1 (Completed)");
				currentTasks.add("Task number 2 (In Progress)");
				reportData.setCurrentTasks(currentTasks);

				List<String> upcomingTasks = new ArrayList<String>();
				String upcomingTask;
				upcomingTasks.add("TaskTodo Number 1 ");
				upcomingTasks.add("TaskTodo Number 2 ");
				upcomingTasks.add("TaskTodo Number 3 ");
				reportData.setUpcomingTasks(upcomingTasks);

				reportData.setReportingPeriod("02/02/2015 - 02/07/2015");

			}   



			ByteArrayOutputStream output = null;
			byte[] outbytes = null;
			if(reportFormat.equalsIgnoreCase("Word")){
				output=(ByteArrayOutputStream)msWordReportTemplate.generateWordWeeklyStatusReport(reportData);
				outbytes=output.toByteArray();
				response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
				//response.setHeader("Content-Disposition","attachment; filename=\"WeeklyStatusReport.docx\"");
				response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\" ; size=\"%d\"", "WeeklyStatusReport.docx",outbytes.length));
			}else{
				output=(ByteArrayOutputStream)msWordReportTemplate.generateWordWeeklyStatusReportPDF(reportData);
				outbytes=output.toByteArray();
				response.setContentType("application/pdf");
				//response.setHeader("Content-Disposition","attachment; filename=\"WeeklyStatusReport.docx\"");
				//response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\" ; size=\"%d\"", "WeeklyStatusReport.pdf",outbytes.length));
				response.setHeader("Content-Disposition","attachment;filename=\"WeeklyStatusReport.pdf\"");
			}
			outStream.write(outbytes);
			output.flush();
			//outStream.flush();
			//outStream.close();
			response.flushBuffer();



			//


		} 
		catch (IOException e) {
			LOG.error("Report Error:",e);
		}  catch (URISyntaxException e) {
			LOG.error("Report Error:",e);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// return projectPlanData;
	}

	public void generateReport(String projectId, String releaseId,HttpServletResponse response,String startDate,String endDate,String status,String reportFormat) {

		System.out.println("reached dao impl::"+ projectId+" " +releaseId+" "+startDate+" "+ endDate);
		String query ="SELECT PROJ_NAME,PRGM_ID FROM EDB_PROJECT WHERE PROJ_ID  = ?";
		Integer projId = Integer.valueOf(projectId);
		Integer relId = Integer.valueOf(releaseId);
		try {
			if(getConnection()==null)
			{
				System.out.println("connection null");
			}
			else
			{
				PreparedStatement  preparedStatement = getConnection().prepareStatement(query);
				preparedStatement.setString(1, projectId);
				ResultSet rs = preparedStatement.executeQuery();

				Integer programId =0 ;
				String projectName=null;

				while(rs.next()){
					programId = rs.getInt("PRGM_ID");

					projectName = rs.getString("PROJ_NAME");
				}   
				if(programId !=0 && relId !=0)
				{
					System.out.println("program id ::"+ programId);
					String releaseIdQuery = "SELECT MLSTN_NAME FROM EDB_MILESTONE WHERE PROJ_ID= ? AND  MLSTN_ID = ?";

					preparedStatement = getConnection().prepareStatement(releaseIdQuery);
					preparedStatement.setInt(1, Integer.valueOf(projectId));
					preparedStatement.setInt(2, relId);

					rs = preparedStatement.executeQuery();
					while(rs.next())
					{
						String mlstnName=rs.getString("MLSTN_NAME");
						System.out.println("milestone id is::"+mlstnName);

						if(mlstnName!= null)
						{
							getAccomplishments(relId, projId, programId,response,startDate,endDate,status,reportFormat);
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	public void generateMasterReport(HttpServletResponse response,String startDate,String endDate,String reportFormat,String reportName) {

		System.out.println("reached dao impl::");
	
		
		if(reportName.equals("RelMstr")){
		List<WeeklyStatusReportData> releaseData = new ArrayList<WeeklyStatusReportData>(); 
		final StringBuffer projQuery =new StringBuffer();
		projQuery.append("SELECT P.PROJ_NAME,M.MLSTN_NAME,M.MLSTN_DESC,M.MLSTN_ST_DT,M.MLSTN_END_DT,M.MLSTN_WRK_STATUS FROM ");
		projQuery.append("EDB_PROJECT AS P LEFT JOIN EDB_MILESTONE AS M ON P.PROJ_ID = M.PROJ_ID ");
		projQuery.append("WHERE M.MLSTN_ST_DT >= #"+startDate+"# AND M.MLSTN_END_DT <= #"+endDate+"#");
		
		System.out.println(projQuery.toString());
		
		try {
			if(getConnection()==null)
			{
				System.out.println("connection null");
			}
			else
			{
				
				
		Statement statement = getConnection().createStatement();
		
		ResultSet rs = statement.executeQuery(projQuery.toString());
		
		releaseData = getReleaseList(rs);
		
		generateReleaseMasterReport(releaseData,response);
		}
		
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else if(reportName.equals("EmpMstr")){
			
			List<ReportMasterData> resourceDetails = new ArrayList<ReportMasterData>();
			String projQuery = "SELECT EMP_RESOURCE_NAME,EMP_ENTERPRISE_ID,EMP_EMPLOYEE_ID,EMP_LEVEL,EMP_ROLE FROM EDB_MSTR_EMP_DTLS";
			System.out.println(projQuery.toString());
			
			try {
				if(getConnection()==null)
				{
					System.out.println("connection null");
				}
				else
				{
			Statement statement = getConnection().createStatement();
			
			ResultSet rs = statement.executeQuery(projQuery.toString());
			
			resourceDetails = getResourceList(rs);
			
			generateEmployeeMasterReport(resourceDetails,response);
			}
			
		
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
}
		
		
		private List<WeeklyStatusReportData> getReleaseList(ResultSet rs){
			List<WeeklyStatusReportData> releaseResult = new ArrayList<WeeklyStatusReportData>();
			try {
				while(rs.next()){
					WeeklyStatusReportData reportData = new WeeklyStatusReportData();
					reportData.setReleaseName(rs.getString("MLSTN_NAME"));
					reportData.setReleaseDesc(rs.getString("MLSTN_DESC"));
					reportData.setProjectName(rs.getString("PROJ_NAME"));
					reportData.setStartDate(rs.getString("MLSTN_ST_DT"));
					reportData.setEndDate(rs.getString("MLSTN_END_DT"));
					reportData.setStatus(rs.getString("MLSTN_WRK_STATUS"));
					releaseResult.add(reportData);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return releaseResult;
			
		}
		
		private List<ReportMasterData> getResourceList(ResultSet rs){
			List<ReportMasterData> resourceResult = new ArrayList<ReportMasterData>();
			try {
				while(rs.next()){
					ReportMasterData resourceData = new ReportMasterData();
					resourceData.setEmployeeName(rs.getString("EMP_RESOURCE_NAME"));
					resourceData.setEnterpriseId(rs.getString("EMP_ENTERPRISE_ID"));
					resourceData.setEmployeeNumber(rs.getString("EMP_EMPLOYEE_ID"));
					resourceData.setLevel(rs.getString("EMP_LEVEL"));
					resourceData.setRole(rs.getString("EMP_ROLE"));
					
					resourceResult.add(resourceData);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return resourceResult;
			
		}
		
		public void generateReleaseMasterReport(List<WeeklyStatusReportData> releaseData,HttpServletResponse response) throws Docx4JException, URISyntaxException{
			ServletOutputStream outStream;
			try {
				outStream = response.getOutputStream();
				ByteArrayOutputStream output = null;
				byte[] outbytes = null;
				output=(ByteArrayOutputStream)msWordReportTemplate.generateReleaseMasterReportPDF(releaseData);
				outbytes=output.toByteArray();
				response.setContentType("application/pdf");
				//response.setHeader("Content-Disposition","attachment; filename=\"WeeklyStatusReport.docx\"");
				//response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\" ; size=\"%d\"", "WeeklyStatusReport.pdf",outbytes.length));
				response.setHeader("Content-Disposition","attachment;filename=\"ReleaseMaster.pdf\"");
			
			outStream.write(outbytes);
			output.flush();
			//outStream.flush();
			//outStream.close();
			response.flushBuffer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
		
		public void generateEmployeeMasterReport(List<ReportMasterData> resourceDetails,HttpServletResponse response) throws Docx4JException, URISyntaxException{
			ServletOutputStream outStream;
			try {
				outStream = response.getOutputStream();
				ByteArrayOutputStream output = null;
				byte[] outbytes = null;
				output=(ByteArrayOutputStream)msWordReportTemplate.generateEmployeeMasterReportPDF(resourceDetails);
				outbytes=output.toByteArray();
				response.setContentType("application/pdf");
				//response.setHeader("Content-Disposition","attachment; filename=\"WeeklyStatusReport.docx\"");
				//response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\" ; size=\"%d\"", "WeeklyStatusReport.pdf",outbytes.length));
				response.setHeader("Content-Disposition","attachment;filename=\"EmployeeMaster.pdf\"");
			
			outStream.write(outbytes);
			output.flush();
			//outStream.flush();
			//outStream.close();
			response.flushBuffer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
		
		public void generateDMSReport(HttpServletResponse response,String programName,String projectName,String releaseName,String weekStartDate,String weekEndDate) {
			System.out.println("Reached DAO Impl::");
			
				List<DMSReportData> currentWeekData = new ArrayList<DMSReportData>();
				List<DMSReportData> futureWeekData = new ArrayList<DMSReportData>();
				
				
				try {
					currentWeekData = getCurrentWeekData(programName,projectName,releaseName,weekStartDate,weekEndDate);
					futureWeekData = getFutureWeekData(programName,projectName,releaseName,weekStartDate,weekEndDate);
					generateDMSReport(currentWeekData,futureWeekData,response,programName,weekEndDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				
		}
	
		private void generateDMSReport(List<DMSReportData> currentWeekData,
				List<DMSReportData> futureWeekData, HttpServletResponse response,String programName,String weekEndDate) throws ParseException {
			ServletOutputStream outStream;
			try {
				String weekStartDate = CalendarEnum.getWeekStartDate(weekEndDate);
				String startDate = CalendarEnum.getNextWeekStartDate(weekEndDate);
				String endDate = CalendarEnum.getNextWeekEndDate(startDate);
				outStream = response.getOutputStream();
				ByteArrayOutputStream output = null;
				byte[] outbytes = null;
				output=(ByteArrayOutputStream)msWordReportTemplate.generateDMSPDFReport(currentWeekData,futureWeekData,programName,weekEndDate,weekStartDate,startDate,endDate);
				outbytes=output.toByteArray();
				response.setContentType("application/pdf");
				//response.setHeader("Content-Disposition","attachment; filename=\"WeeklyStatusReport.docx\"");
				//response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\" ; size=\"%d\"", "WeeklyStatusReport.pdf",outbytes.length));
				response.setHeader("Content-Disposition","attachment;filename=\"DMSReport.pdf\"");
			
			outStream.write(outbytes);
			output.flush();
			//outStream.flush();
			//outStream.close();
			response.flushBuffer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		private List<DMSReportData> getFutureWeekData(String programName,
				String projectName, String releaseName, String weekStartDate,
				String weekEndDate) throws ParseException {
			
			List<DMSReportData> futureWeekData = new ArrayList<DMSReportData>();
			
			String startDate = CalendarEnum.getNextWeekStartDate(weekEndDate);
			String endDate = CalendarEnum.getNextWeekEndDate(startDate);
			
			
			
			final StringBuffer projQuery =new StringBuffer();
			projQuery.append(" SELECT C.COMPNT_NAME, C.COMPNT_FUNC_DESC, C.COMPNT_PHASE, P.PROJ_NAME, M.MLSTN_NAME,EP.PRGM_NAME ");
			projQuery.append(" FROM (((EDB_PROJ_COMPNT AS C ");
			projQuery.append(" LEFT JOIN EDB_MILESTONE AS M ON M.MLSTN_ID = C.MLSTN_ID) ");
			projQuery.append(" LEFT JOIN EDB_PROJECT AS P ON P.PROJ_ID = M.PROJ_ID) ");
			projQuery.append(" LEFT JOIN EDB_PROGRAM AS EP ON EP.PRGM_ID = P.PRGM_ID) ");
			projQuery.append(" WHERE EP.PRGM_ID ="+programName+"  AND");
			projQuery.append((null!=projectName && !projectName.equals("0"))?" P.PROJ_ID ="+projectName+"  AND ":" ");
			projQuery.append((null!=releaseName && !releaseName.equals("0"))?" M.MLSTN_ID ="+releaseName+" AND ":" ");
			projQuery.append(" C.COMPNT_END_DT >=  #"+startDate+"# AND C.COMPNT_END_DT <= #"+endDate+"# ");
			
			
			System.out.println(projQuery.toString());
			
			try {
				if(getConnection()==null)
				{
					System.out.println("connection null");
				}
				else
				{
			Statement statement = getConnection().createStatement();
			ResultSet rs = statement.executeQuery(projQuery.toString());
			futureWeekData = getDMSDataList(rs);
			}
			
		
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return futureWeekData;
		}

		private List<DMSReportData> getCurrentWeekData(String programName,
				String projectName, String releaseName, String weekStartDate,
				String weekEndDate) {
			List<DMSReportData> currentWeekData = new ArrayList<DMSReportData>();
			
			final StringBuffer projQuery =new StringBuffer();
			projQuery.append(" SELECT C.COMPNT_NAME, C.COMPNT_FUNC_DESC, C.COMPNT_PHASE, P.PROJ_NAME, M.MLSTN_NAME,EP.PRGM_NAME ");
			projQuery.append(" FROM (((EDB_PROJ_COMPNT AS C ");
			projQuery.append(" LEFT JOIN EDB_MILESTONE AS M ON M.MLSTN_ID = C.MLSTN_ID) ");
			projQuery.append(" LEFT JOIN EDB_PROJECT AS P ON P.PROJ_ID = M.PROJ_ID) ");
			projQuery.append(" LEFT JOIN EDB_PROGRAM AS EP ON EP.PRGM_ID = P.PRGM_ID) ");
			projQuery.append(" WHERE EP.PRGM_ID ="+programName+"  AND");
			projQuery.append((null!=projectName && !projectName.equals("0"))?" P.PROJ_ID ="+projectName+"  AND ":" ");
			projQuery.append((null!=releaseName && !releaseName.equals("0"))?" M.MLSTN_ID ="+releaseName+" AND ":" ");
			projQuery.append(" C.COMPNT_END_DT >=  #"+weekStartDate+"# AND C.COMPNT_END_DT <= #"+weekEndDate+"# ");
			
			
			System.out.println(projQuery.toString());
			
			try {
				if(getConnection()==null)
				{
					System.out.println("connection null");
				}
				else
				{
			Statement statement = getConnection().createStatement();
			ResultSet rs = statement.executeQuery(projQuery.toString());
			currentWeekData = getDMSDataList(rs);
			}
			
		
			} catch (Exception e) {
				e.printStackTrace();
			}
			return currentWeekData;
		}
		
		private List<DMSReportData> getDMSDataList(ResultSet rs) {
			
			List<DMSReportData> compData = new ArrayList<DMSReportData>();
			try {
				while(rs.next()){
					DMSReportData dmsReportData = new DMSReportData();
					
					dmsReportData.setProgramName(rs.getString("PRGM_NAME"));
					dmsReportData.setProjectName(rs.getString("PROJ_NAME"));
					dmsReportData.setReleaseName(rs.getString("MLSTN_NAME"));
					dmsReportData.setComponentName(rs.getString("COMPNT_NAME"));
					dmsReportData.setFunctionalDesc(rs.getString("COMPNT_FUNC_DESC"));
					dmsReportData.setPhaseId(rs.getInt("COMPNT_PHASE"));
					compData.add(dmsReportData);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return compData;
		}

	}


