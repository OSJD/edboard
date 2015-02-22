package com.acc.tools.ed.integration.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.ProjectWorkDao;
import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.TaskForm;
import com.acc.tools.ed.integration.dto.VacationForm;


@Service("projectWorkDao")
public class ProjectWorkDaoImpl extends AbstractEdbDao implements ProjectWorkDao {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectWorkDaoImpl.class);
	
	
	public int addVacation(VacationForm vacationForm){
		
		int status =0;
		try {
			
			String addTaskQuery = "insert into EDB_VACTN_CALNDR(EMP_ID,VACTN_TYP,VACTN_STRT_DT,VACTN_END_DT,COMNTS,STATUS,SUP_ID,SUP_COMNTS) values (?,?,?,?,?,?,?,?)";
			PreparedStatement pstm = getConnection().prepareStatement(addTaskQuery);
			pstm.setInt(1, vacationForm.getEmployeeId());
			pstm.setString(2, vacationForm.getVacationType());
			pstm.setString(3, vacationForm.getStartDate());
			pstm.setString(4, vacationForm.getEndDate());
			pstm.setString(5, vacationForm.getComments());
			pstm.setString(6, vacationForm.getStatus());
			pstm.setInt(7, vacationForm.getSupervisorId());
			pstm.setString(8, vacationForm.getApproverComments());
			pstm.executeUpdate();
			pstm.close();

			
		} catch (Exception e) {
			log.error("Error Inserting into Vacation Table:{}",e);
		}
		return status;
	}
	
	public List<ProjectForm> getMyTasks(String userId) {
		
		final List<ProjectForm> projectTasks=new ArrayList<ProjectForm>();
		final Map<Integer,ProjectForm> projMap = new HashMap<Integer, ProjectForm>();
		final Map<Integer,ReleaseForm> relMap = new HashMap<Integer, ReleaseForm>();

		try{
	        final StringBuffer componentTable =new StringBuffer();
	       
	        componentTable.append(" SELECT C.COMPNT_ID as COMPONENT_ID,C.COMPNT_NAME,C.COMPNT_FUNC_DESC,C.COMPNT_PHASE,C.COMPNT_ST_DT,C.COMPNT_END_DT, M.*, T.*,H.*, P.PROJ_NAME, CE.EMP_ID, CE.WORK_DESC,ED.EMP_RESOURCE_NAME FROM (((EDB_PROJECT AS P LEFT JOIN EDB_MILESTONE AS M ON P.PROJ_ID = M.PROJ_ID) ");
	        componentTable.append(" LEFT JOIN (EDB_PROJ_COMPNT AS C LEFT JOIN EDB_TASK_MASTER AS T ON C.COMPNT_ID = T.COMPNT_ID) ON M.MLSTN_ID = C.MLSTN_ID) ");
	        componentTable.append(" LEFT JOIN (EDB_COMPNT_EMP AS CE LEFT JOIN EDB_MSTR_EMP_DTLS AS ED ON CE.EMP_ID = ED.EMP_ID) ON CE.COMPNT_ID=C.COMPNT_ID) LEFT JOIN EDB_TASK_REVW_HISTORY AS H ON T.TASK_ID=H.TASK_ID  WHERE CE.EMP_ID="+userId);

			log.debug("My Tasks Query:{}",componentTable.toString());
			Statement stmt=getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(componentTable.toString());
			while(rs.next()){
				final int projectId=rs.getInt("PROJ_ID");
				final int releaseId = rs.getInt("MLSTN_ID");
				final int componentId=rs.getInt("COMPONENT_ID");
				final int taskId=rs.getInt("TASK_ID");

				
				if(!projMap.isEmpty() && projMap.containsKey(projectId)){
					//second record occurrence
					ProjectForm project=projMap.get(projectId);
					if(!relMap.isEmpty() && relMap.containsKey(releaseId)){
						ReleaseForm release=relMap.get(releaseId);
						if(componentId!=0){
							final ComponentForm component=new ComponentForm(); 
							component.setComponentId(componentId);
							mapComponentData(rs, release,component);
							if(taskId!=0){
								final TaskForm task=new TaskForm();
								mapTaskData(rs, task,component.getComponentId(),taskId);
								if(component.getTaskFormList()==null){
									component.setTaskFormList(new ArrayList<TaskForm>());
								}
								component.getTaskFormList().add(task);
							}
						}

					} else {
						final ReleaseForm release=new ReleaseForm();
						mapReleaseData(rs, project, release, releaseId);
						if(componentId!=0){
							final ComponentForm component=new ComponentForm(); 
							component.setComponentId(componentId);
							mapComponentData(rs, release,component);
							if(taskId!=0){
								final TaskForm task=new TaskForm();
								mapTaskData(rs, task,component.getComponentId(),taskId);
								if(component.getTaskFormList()==null){
									component.setTaskFormList(new ArrayList<TaskForm>());
								}
								component.getTaskFormList().add(task);
							}
							relMap.put(releaseId, release);
						}
					}
				} else {
					//First record occurrence
					ProjectForm project=new ProjectForm();
					project.setProjectId(projectId);
					project.setProjectName(rs.getString("PROJ_NAME"));
					final ReleaseForm release=new ReleaseForm();
					mapReleaseData(rs, project, release, releaseId);
					if(componentId!=0){
						final ComponentForm component=new ComponentForm(); 
						component.setComponentId(componentId);
						mapComponentData(rs, release,component);
						if(taskId!=0){
							final TaskForm task=new TaskForm();
							mapTaskData(rs, task,component.getComponentId(),taskId);
							if(component.getTaskFormList()==null){
								component.setTaskFormList(new ArrayList<TaskForm>());
							}
							component.getTaskFormList().add(task);
						}
						projMap.put(projectId, project);
						relMap.put(releaseId, release);
						projectTasks.add(project);
					
					}
				}
				
			}
			
			
			stmt.close();
		}catch(Exception e)	{
			log.error("Error retreiving employee table :",e);
			return null;
		}
		
		return projectTasks;
	}


public List<ProjectForm> getMyTeamTasks(String supervisorId) {
	
	final List<ProjectForm> projectTasks=new ArrayList<ProjectForm>();
	final Map<Integer,ProjectForm> projMap = new HashMap<Integer, ProjectForm>();
	final Map<Integer,ReleaseForm> relMap = new HashMap<Integer, ReleaseForm>();

	try{
        final List<ReferenceData> userIdList =getMyTeamIds(supervisorId);
        for(int i=0;i<userIdList.size();i++){
        	final StringBuffer componentTable =new StringBuffer();
        	ReferenceData userData=userIdList.get(i);
        
        	componentTable.append(" SELECT C.COMPNT_ID as COMPONENT_ID,C.COMPNT_NAME,C.COMPNT_FUNC_DESC,C.COMPNT_PHASE,C.COMPNT_ST_DT,C.COMPNT_END_DT, M.*, T.*,H.*, P.PROJ_NAME, CE.EMP_ID, CE.WORK_DESC, ED.EMP_RESOURCE_NAME FROM (((EDB_PROJECT AS P LEFT JOIN EDB_MILESTONE AS M ON P.PROJ_ID = M.PROJ_ID) ");
	        componentTable.append(" LEFT JOIN (EDB_PROJ_COMPNT AS C LEFT JOIN EDB_TASK_MASTER AS T ON C.COMPNT_ID = T.COMPNT_ID) ON M.MLSTN_ID = C.MLSTN_ID) ");
	        componentTable.append(" LEFT JOIN (EDB_COMPNT_EMP AS CE LEFT JOIN EDB_MSTR_EMP_DTLS AS ED ON CE.EMP_ID = ED.EMP_ID) ON CE.COMPNT_ID=C.COMPNT_ID) LEFT JOIN EDB_TASK_REVW_HISTORY AS H ON T.TASK_ID=H.TASK_ID  WHERE CE.EMP_ID="+userData.getId());

			log.debug("My Team Tasks Query:{}",componentTable.toString());
			final Statement stmt=getConnection().createStatement();
			final ResultSet rs=stmt.executeQuery(componentTable.toString());
			while(rs.next()){
				final int projectId=rs.getInt("PROJ_ID");
				final int releaseId = rs.getInt("MLSTN_ID");
				final int componentId=rs.getInt("COMPONENT_ID");
				final int taskId=rs.getInt("TASK_ID");
	
				
				if(!projMap.isEmpty() && projMap.containsKey(projectId)){
					//second record occurrence
					ProjectForm project=projMap.get(projectId);
					if(!relMap.isEmpty() && relMap.containsKey(releaseId)){
						ReleaseForm release=relMap.get(releaseId);
						if(componentId!=0){
							final ComponentForm component=new ComponentForm(); 
							component.setComponentId(componentId);
							mapComponentData(rs, release,component);
							if(taskId!=0){
								final TaskForm task=new TaskForm();
								mapTaskData(rs, task,component.getComponentId(),taskId);
								if(component.getTaskFormList()==null){
									component.setTaskFormList(new ArrayList<TaskForm>());
								}
								component.getTaskFormList().add(task);
							}

						}
						
					} else {
						final ReleaseForm release=new ReleaseForm();
						mapReleaseData(rs, project, release, releaseId);
						if(componentId!=0){
							final ComponentForm component=new ComponentForm(); 
							component.setComponentId(componentId);
							mapComponentData(rs, release,component);
							if(taskId!=0){
								final TaskForm task=new TaskForm();
								mapTaskData(rs, task,component.getComponentId(),taskId);
								if(component.getTaskFormList()==null){
									component.setTaskFormList(new ArrayList<TaskForm>());
								}
								component.getTaskFormList().add(task);
							}
							relMap.put(releaseId, release);
						}
					}
				} else {
					//First record occurrence
					ProjectForm project=new ProjectForm();
					project.setProjectId(projectId);
					project.setProjectName(rs.getString("PROJ_NAME"));
					final ReleaseForm release=new ReleaseForm();
					mapReleaseData(rs, project, release, releaseId);
					mapUserData(rs, release,userData);
					if(componentId!=0){
						final ComponentForm component=new ComponentForm(); 
						component.setComponentId(componentId);
						mapComponentData(rs, release,component);
						if(taskId!=0){
							final TaskForm task=new TaskForm();
							mapTaskData(rs, task,component.getComponentId(),taskId);
							if(component.getTaskFormList()==null){
								component.setTaskFormList(new ArrayList<TaskForm>());
							}
							component.getTaskFormList().add(task);
						}
						projMap.put(projectId, project);
						relMap.put(releaseId, release);
						projectTasks.add(project);
					}
				}
				
			}
		
		
		stmt.close();
        }
	}catch(Exception e)	{
		log.error("Error retreiving employee table :",e);
		return null;
	}
	
	return projectTasks;
}
	public List<ReferenceData> getMyTeamIds(String supervisorId) {
		
		final List<ReferenceData> userIdList = new ArrayList<ReferenceData>();
		try {

					
			String userIdQuery = " SELECT DISTINCT A.EMP_ID,A.EMP_RESOURCE_NAME FROM (EDB_MSTR_EMP_DTLS AS A" + 
								 " LEFT JOIN EDB_PROJ_EMP AS B ON A.EMP_ID = B.EMP_ID)" +
					             " LEFT JOIN EDB_PROJECT AS C ON B.PROJ_ID=C.PROJ_ID" +
					             " WHERE C.PROJ_LEAD='"+supervisorId+"'";
			
			log.debug("My Team resources Query:{}",userIdQuery);
			Statement selectStatement = getConnection().createStatement();
			ResultSet rs = selectStatement.executeQuery(userIdQuery);
			
			while (rs.next()) {
				ReferenceData userData =new ReferenceData();
				userData.setId(rs.getString("EMP_ID"));
				userData.setLabel(rs.getString("EMP_RESOURCE_NAME"));
				userIdList.add(userData);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return userIdList;
		
	}
	public void addTasks(TaskForm taskForm) {
		
		
		try {
			
			String addTaskQuery = "insert into EDB_TASK_MASTER(COMPNT_ID,TASK_NAME,TASK_DESC,TASK_HRS,TASK_STATUS,TASK_TYPE,TASK_CT_DT,TASK_ST_DT,TASK_ET_DT,TASK_COMMENTS) values (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstm = getConnection().prepareStatement(addTaskQuery);
			pstm.setInt(1, taskForm.getComponentId());
			pstm.setString(2, taskForm.getTaskName());
			pstm.setString(3, taskForm.getTaskDesc());
			pstm.setInt(4, Integer.parseInt(String.valueOf(taskForm.getTaskHrs())));
			pstm.setString(5, taskForm.getTaskStatus());
			pstm.setString(6, taskForm.getTaskType());
			DateTime currentDate=new DateTime(System.currentTimeMillis());
			pstm.setString(7, currentDate.toString("yyyy-MM-dd"));
			pstm.setString(8, taskForm.getTaskStartDate());
			pstm.setString(9, taskForm.getTaskEndDate());
			pstm.setString(10, taskForm.getTaskComments());
			pstm.executeUpdate();
			pstm.close();
			
			
			String addTaskHistoryQuery = "insert into EDB_TASK_REVW_HISTORY(TASK_ACTIONS,TASK_REVIEW_USER,TASK_REVIEW_COMMENTS) values (?,?,?)";
			PreparedStatement pstmHistory = getConnection().prepareStatement(addTaskHistoryQuery);
			pstmHistory.setString(1, taskForm.getTaskAction());
			pstmHistory.setString(2, taskForm.getTaskReviewUser());
			pstmHistory.setString(3, taskForm.getRejComment());
			pstmHistory.executeUpdate();
			pstmHistory.close();
			
			//componentList = getMyTasks(userId).getReleases().get(0).getComponents();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void deleteTasks(int taskId) {
		
		try {

			final String relTable="DELETE FROM EDB_TASK_MASTER WHERE TASK_ID = ?";
			PreparedStatement  relStatement = getConnection().prepareStatement(relTable);
			relStatement.setInt(1, taskId);
			relStatement.executeUpdate();
			relStatement.close();
			
			final String relTableHistory="DELETE FROM EDB_TASK_REVW_HISTORY WHERE TASK_ID = ?";
			PreparedStatement  relStatementHistory = getConnection().prepareStatement(relTableHistory);
			relStatementHistory.setInt(1, taskId);
			relStatementHistory.executeUpdate();
			relStatementHistory.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<TaskForm> editTasks(int taskId) {
		List<TaskForm>taskFormList= new ArrayList<TaskForm>();
		
		try {

			final String relTable="SELECT * FROM EDB_TASK_MASTER AS A LEFT JOIN EDB_TASK_REVW_HISTORY AS B ON A.TASK_ID=B.TASK_ID WHERE A.TASK_ID ="+taskId;
			Statement selectStatement = getConnection().createStatement();
			ResultSet rs = selectStatement.executeQuery(relTable);
			while (rs.next()) {
				TaskForm taskForm = new TaskForm();
				taskForm.setTaskId(rs.getInt("TASK_ID"));
				taskForm.setComponentId(rs.getInt("COMPNT_ID"));
				taskForm.setTaskName(rs.getString("TASK_NAME"));
				taskForm.setTaskDesc(rs.getString("TASK_DESC"));
				taskForm.setTaskHrs(rs.getInt("TASK_HRS"));
				taskForm.setTaskType(rs.getString("TASK_TYPE"));
				taskForm.setTaskCreateDate(rs.getString("TASK_CT_DT"));
				taskForm.setTaskStartDate(rs.getString("TASK_ST_DT"));
				taskForm.setTaskEndDate(rs.getString("TASK_ET_DT"));
				taskForm.setTaskStatus(rs.getString("TASK_STATUS"));
				taskForm.setTaskAction(rs.getString("TASK_ACTIONS"));
				taskForm.setTaskReviewUser(rs.getString("TASK_REVIEW_USER"));
				taskForm.setRejComment(rs.getString("TASK_REVIEW_COMMENTS"));
				taskForm.setTaskComments(rs.getString("TASK_COMMENTS"));
				taskFormList.add(taskForm);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return taskFormList;
	}

	public void saveTasks(TaskForm taskForm) {
		
		
		try {
			
			String addTaskQuery = "UPDATE EDB_TASK_MASTER SET COMPNT_ID = ?, TASK_NAME =?, TASK_DESC=?,TASK_HRS=?,TASK_STATUS=?,TASK_TYPE=?,TASK_CT_DT=?,TASK_ST_DT=?,TASK_ET_DT=?,TASK_COMMENTS=? WHERE TASK_ID =?";
			PreparedStatement pstm = getConnection().prepareStatement(addTaskQuery);
			pstm.setInt(1, taskForm.getComponentId());
			pstm.setString(2, taskForm.getTaskName());
			pstm.setString(3, taskForm.getTaskDesc());
			pstm.setInt(4, Integer.parseInt(String.valueOf(taskForm.getTaskHrs())));
			pstm.setString(5, taskForm.getTaskStatus());
			pstm.setString(6, taskForm.getTaskType());
			pstm.setString(7, taskForm.getTaskCreateDate());
			pstm.setString(8, taskForm.getTaskStartDate());
			pstm.setString(9, taskForm.getTaskEndDate());
			pstm.setInt(10, taskForm.getTaskId());
			pstm.setString(11, taskForm.getTaskComments());
			pstm.executeUpdate();
			pstm.close();
			
			
			String addTaskHistoryQuery = "UPDATE EDB_TASK_REVW_HISTORY SET TASK_ACTIONS=?,TASK_REVIEW_USER=?,TASK_REVIEW_COMMENTS=? WHERE TASK_ID=?";
			PreparedStatement pstmHistory = getConnection().prepareStatement(addTaskHistoryQuery);
			pstmHistory.setString(1, taskForm.getTaskAction());
			pstmHistory.setString(2, taskForm.getTaskReviewUser());
			pstmHistory.setString(3, taskForm.getRejComment());
			pstmHistory.setInt(4, taskForm.getTaskId());
			pstmHistory.executeUpdate();
			pstmHistory.close();
			
			//componentList = getMyTasks(userId).getReleases().get(0).getComponents();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public TaskForm retrieveTasks() {
		TaskForm taskForm= new TaskForm();
		
		try {

			final String relTable="SELECT TOP 1 * FROM EDB_TASK_MASTER ORDER BY TASK_ID DESC";
			Statement selectStatement = getConnection().createStatement();
			ResultSet rs = selectStatement.executeQuery(relTable);
			while (rs.next()) {
				taskForm.setTaskId(rs.getInt("TASK_ID"));
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return taskForm;
	}
	
	public List<ReferenceData> getTasksByComponentId(Integer componentId){
		
		final List<ReferenceData> tasks=new LinkedList<ReferenceData>();
		
		try {

			final String relTable="SELECT TASK_ID,TASK_NAME FROM EDB_TASK_MASTER WHERE COMPNT_ID="+componentId;
			Statement selectStatement = getConnection().createStatement();
			ResultSet rs = selectStatement.executeQuery(relTable);
			while (rs.next()) {
				ReferenceData task=new ReferenceData();
				task.setId(""+rs.getInt("TASK_ID"));
				task.setLabel(rs.getString("TASK_NAME"));
				tasks.add(task);
			}
				
		} catch (Exception e) {
			log.error("Error in getTasksByComponentId:",e);
		}
		return tasks;
	}
	
	public TaskForm getTaskByTaskId(Integer taskId){
		
		TaskForm taskform=null;
		try {

			final String relTable="SELECT TASK_ID,TASK_NAME,TASK_DESC FROM EDB_TASK_MASTER WHERE TASK_ID="+taskId;
			log.debug("getTaskById Query :{}",relTable);
			Statement selectStatement = getConnection().createStatement();
			ResultSet rs = selectStatement.executeQuery(relTable);
			while (rs.next()) {
				taskform=new TaskForm();
				taskform.setTaskId(rs.getInt("TASK_ID"));
				taskform.setTaskName(rs.getString("TASK_NAME"));
				taskform.setTaskDesc(rs.getString("TASK_DESC"));
				
			}
				
		} catch (Exception e) {
			log.error("Error in getTasksByComponentId:",e);
		}
		return taskform;
	}
}
