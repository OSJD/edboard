package com.acc.tools.ed.integration.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.ProjectReportDao;
import com.acc.tools.ed.report.MSWordReportTemplate;
import com.acc.tools.ed.report.dto.WeeklyStatusReportData;

@Service("projectReportDao")
public class ProjectReportDaoImp extends AbstractEdbDao implements ProjectReportDao{

	Logger LOG=LoggerFactory.getLogger(ProjectReportDaoImp.class);

	@Autowired
	private MSWordReportTemplate msWordReportTemplate;

	public void getAccomplishments(Integer releaseId, Integer projectId ,Integer programId,HttpServletResponse response,String startDate,String endDate,String status) {


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
				reportData.setStartDate("01/01/2015");
				reportData.setEndDate("02/27/2015");
				reportData.setRevisedEndDate("");
				reportData.setStatus("NotStarted");
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



			ByteArrayOutputStream output=(ByteArrayOutputStream)msWordReportTemplate.generateWordWeeklyStatusReport(reportData);
			byte[] outbytes=output.toByteArray();
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			//response.setHeader("Content-Disposition","attachment; filename=\"WeeklyStatusReport.docx\"");
			response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\" ; size=\"%d\"", "WeeklyStatusReport.docx",outbytes.length));
			outStream.write(outbytes);
			output.close();
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

	public void generateReport(String projectId, String releaseId,HttpServletResponse response,String startDate,String endDate,String status) {

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
							getAccomplishments(relId, projId, programId,response,startDate,endDate,status);
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
