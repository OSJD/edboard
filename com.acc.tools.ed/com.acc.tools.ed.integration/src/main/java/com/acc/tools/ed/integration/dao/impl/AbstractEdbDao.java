package com.acc.tools.ed.integration.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acc.tools.ed.database.MicroSoftAccessDatabase;
import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.TaskForm;
import com.acc.tools.ed.integration.dto.TaskLedgerForm;
import com.acc.tools.ed.integration.dto.TaskReviewHistory;


public class AbstractEdbDao {
	
	Logger log=LoggerFactory.getLogger(AbstractEdbDao.class);
	
	@Resource
	private MicroSoftAccessDatabase msAccessDatabase;
	
	
	public Connection getConnection() throws IOException, SQLException{
		final Connection connection=msAccessDatabase.getConnection();
		if(connection==null){
			throw new RuntimeException("Connection to Sharepoint failed.Please restart the EngagementDashboard Tool.");
		}
		return connection;
	}
	
	public void mapComponentData(ResultSet rs,ReleaseForm release,ComponentForm component) throws SQLException, ParseException{
		final String compName=rs.getString("COMPNT_NAME");
		final int empId=rs.getInt("EMP_ID");
		log.debug("Component Name:{} Resource Id:{}",compName,empId);
		if(compName!=null && !compName.isEmpty() && empId!=0){
	        component.setComponentName(compName);
	        component.setResourceId(empId);
	        component.setFunctionalDesc(rs.getString("COMPNT_FUNC_DESC"));
	        component.setResourceName(rs.getString("EMP_RESOURCE_NAME"));
	        component.setWorkDesc(rs.getString("WORK_DESC"));
	        component.setPhaseId(rs.getInt("COMPNT_PHASE"));
	        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        String cStDt = rs.getString("COMPNT_ST_DT");
	        if(cStDt != null) {
	               Date compStDate =  sdf2.parse(cStDt);
	               sdf2.applyPattern("MM/dd/yyyy");
	               component.setStartDate(sdf2.format(compStDate));                                   
	        } else {
	               component.setStartDate(null);
	        }
	        
	        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        String cEtDt = rs.getString("COMPNT_END_DT");
	        if(cEtDt != null) {
	               Date compEtDate =  sdf3.parse(cEtDt);
	               sdf3.applyPattern("MM/dd/yyyy");                
	               component.setEndDate(sdf3.format(compEtDate));
	        } else {
	               component.setEndDate(null);
	        }
	        if(release!=null){
		        if(release.getComponents()==null){
		        	release.setComponents(new ArrayList<ComponentForm>());
		        }
		        release.getComponents().add(component);
		        if(release.getTeamTasks()==null){
		        	release.setTeamTasks(new HashMap<String, List<ComponentForm>>());
		        }
		        if(release.getTeamTasks().containsKey(component.getResourceName())){
		        	release.getTeamTasks().get(component.getResourceName()).add(component);
		        } else {
		        	List<ComponentForm> empComponents=new ArrayList<ComponentForm>();
		        	empComponents.add(component);
		        	release.getTeamTasks().put(component.getResourceName(), empComponents);	
		        }
	        }
		}
	}
	
	public void mapTaskLedgerData(ResultSet rs,List<TaskLedgerForm> taskLedger,int taskLedgerId) throws SQLException{
		final TaskLedgerForm ledgerForm=new TaskLedgerForm();
		ledgerForm.setTaskLedgerId(taskLedgerId);
		ledgerForm.setTaskHrs(rs.getInt("TASK_HRS"));
		ledgerForm.setTaskActivity(rs.getString("TASK_ACTIVITY"));
		ledgerForm.setTaskStatus(rs.getString("TASK_STATUS"));
		ledgerForm.setTaskActivityDate(new DateTime(rs.getDate("TASK_ACTIVITI_DT").getTime()).toString("MM/dd/yyyy"));
		taskLedger.add(ledgerForm);
	}
	
	public void mapTaskReviewHistory(ResultSet rs,List<TaskReviewHistory> historys,int reviewCommentId) throws SQLException{
		final TaskReviewHistory history=new TaskReviewHistory();
		history.setReviewHistoryId(reviewCommentId);
		final String reviewComment=rs.getString("TASK_REVIEW_COMMENTS");
		final String devComment=rs.getString("TASK_DEV_COMMENTS");
		if(reviewComment!=null && reviewComment.length()>0){
			history.setReviewComment(reviewComment);
		}else {
			history.setReviewComment("");
		}
		if(devComment!=null && devComment.length()>0){
			history.setDevResponse(devComment);
		}else {
			history.setDevResponse("");
		}
		
		history.setIsReviewValid(rs.getString("TASK_REVIEW_VALID"));
		historys.add(history);
	}
	
	public void mapTaskData(ResultSet rs,TaskForm taskForm,Integer componentId,Integer taskId) throws SQLException{
		taskForm.setTaskId(taskId);
		taskForm.setTaskName(rs.getString("TASK_NAME"));
		taskForm.setComponentId(componentId);
		taskForm.setTaskDesc(rs.getString("TASK_DESC"));
		taskForm.setTaskStatus(rs.getString("TASK_STATUS"));
		taskForm.setTaskType(rs.getString("TASK_TYPE"));
		taskForm.setTaskCreateDate(rs.getString("TASK_CT_DT"));
	}
	
	public void mapReleaseData(ResultSet rs,ProjectForm project,ReleaseForm release,Integer releaseId) throws SQLException{
		if(project.getReleases()==null){
			project.setReleases(new ArrayList<ReleaseForm>());
		}
		release.setReleaseId(releaseId);
		release.setReleaseName(rs.getString("MLSTN_NAME"));
		release.setReleaseDesc(rs.getString("MLSTN_DESC"));
		release.setReleaseStartDate(rs.getString("MLSTN_ST_DT"));
		release.setReleaseEndDate(rs.getString("MLSTN_END_DT"));
		project.getReleases().add(release);
	}
	public void mapUserData(ResultSet rs,ReleaseForm release,ReferenceData userData) throws SQLException{
		userData.setId(userData.getId());
		userData.setLabel(userData.getLabel());
		if(release.getUserData()==null){
        	release.setUserData(new ArrayList<ReferenceData>());
        }
        release.getUserData().add(userData);
	}
}
