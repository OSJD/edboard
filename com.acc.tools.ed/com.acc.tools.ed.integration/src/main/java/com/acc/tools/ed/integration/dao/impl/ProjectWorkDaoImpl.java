package com.acc.tools.ed.integration.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.acc.tools.ed.integration.dto.TaskLedgerForm;
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
			//pstm.setString(2, vacationForm.getResourceName());
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
	
public int approveVacation(VacationForm vacationForm){
		
		int status =0;
		try {
			
			final String addTaskQuery = "Update EDB_VACTN_CALNDR SET SUP_COMNTS =? , STATUS =? WHERE ID=?";
			PreparedStatement pstm = getConnection().prepareStatement(addTaskQuery);
			pstm.setString(1, vacationForm.getApproverComments());
			log.debug("Status being set:{}",vacationForm.getStatus());
			pstm.setString(2, vacationForm.getStatus());
			pstm.setInt(3, vacationForm.getVacationId());
			status = pstm.executeUpdate();
			pstm.close();

			
		} catch (Exception e) {
			log.error("Error Inserting into Vacation Table:{}",e);
		}
		return status;
	}

	private Map<Integer,List<TaskForm>> getMyReviewTasks(Integer userId){
		
		final Map<Integer,List<TaskForm>> reviewTaskMap=new HashMap<Integer,List<TaskForm>>();
		
		try{
			
			final StringBuffer taskTable =new StringBuffer();
			taskTable.append("SELECT T.COMPNT_ID,T.TASK_ID,T.EMP_ID,T.EMP_NM,T.TASK_NAME,T.TASK_DESC FROM (EDB_TASK_MASTER T LEFT JOIN EDB_TASK_REVW R ON T.TASK_ID=R.TASK_ID) LEFT JOIN EDB_TASK_REVW_HISTORY H ON T.TASK_ID=H.TASK_ID ");
			taskTable.append(" WHERE R.TASK_REVIWER_ID="+userId);
			log.debug("Review Task Query :{}",taskTable.toString());
			
			Statement stmt=getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(taskTable.toString());
			
			while(rs.next()){
				final TaskForm reviewTask=new TaskForm();
				final int componentId=rs.getInt("COMPNT_ID");
				reviewTask.setComponentId(componentId);
				reviewTask.setTaskId(rs.getInt("TASK_ID"));
				reviewTask.setEmployeeId(rs.getInt("EMP_ID"));
				reviewTask.setEmployeeName(rs.getString("EMP_NM"));
				reviewTask.setTaskName(rs.getString("TASK_NAME"));
				reviewTask.setTaskDesc(rs.getString("TASK_DESC"));
				if(!reviewTaskMap.isEmpty() && reviewTaskMap.containsKey(componentId)){
					reviewTaskMap.get(componentId).add(reviewTask);
				} else {
					final List<TaskForm> reviewTasks=new ArrayList<TaskForm>();
					reviewTasks.add(reviewTask);
					reviewTaskMap.put(componentId, reviewTasks);
				}
			}
			
		}catch(Exception e){
			log.error("Error fetching my review tasks",e);
		}
		
		return reviewTaskMap;
	}
	
	public List<ProjectForm> getMyTasks(Integer userId) {
		
		final List<ProjectForm> projectTasks=new ArrayList<ProjectForm>();
		final Map<Integer,ProjectForm> projMap = new HashMap<Integer, ProjectForm>();
		final Map<Integer,ReleaseForm> relMap = new HashMap<Integer, ReleaseForm>();
		final Map<Integer,ComponentForm> compMap=new HashMap<Integer, ComponentForm>();
		final Map<Integer,TaskForm> taskMap=new HashMap<Integer, TaskForm>();

		try{
	        final StringBuffer componentTable =new StringBuffer();
	       
	        componentTable.append("SELECT C.COMPNT_ID as COMPONENT_ID,C.COMPNT_NAME,C.COMPNT_FUNC_DESC,C.COMPNT_PHASE,C.COMPNT_ST_DT,C.COMPNT_END_DT, ");
	        componentTable.append(" M.*, T.TASK_ID AS TSK_ID,T.TASK_NAME,T.TASK_DESC,T.TASK_STATUS,T.TASK_TYPE,T.TASK_CT_DT,H.*, P.PROJ_NAME, CE.EMP_ID, CE.WORK_DESC,ED.EMP_RESOURCE_NAME, TL.TASK_HRS, TL.TASK_ACTIVITY,TL.TASK_ACTIVITI_DT "); 
	        componentTable.append("	FROM ((((((EDB_PROJECT AS P LEFT JOIN EDB_MILESTONE AS M ON P.PROJ_ID = M.PROJ_ID)  LEFT JOIN EDB_PROJ_COMPNT AS C ON M.MLSTN_ID = C.MLSTN_ID) "); 
	        componentTable.append(" LEFT JOIN EDB_COMPNT_EMP AS CE ON CE.COMPNT_ID=C.COMPNT_ID) LEFT JOIN EDB_TASK_MASTER AS T ON CE.COMPNT_ID=T.COMPNT_ID) "); 
	        componentTable.append(" LEFT JOIN EDB_TASK_LEDGER AS TL ON T.TASK_ID=TL.TASK_ID) LEFT JOIN EDB_TASK_REVW_HISTORY AS H ON T.TASK_ID=H.TASK_ID) ");
	        componentTable.append(" LEFT JOIN EDB_MSTR_EMP_DTLS AS ED ON  CE.EMP_ID= ED.EMP_ID WHERE CE.EMP_ID="+userId+" AND T.EMP_ID="+userId);

			log.debug("My Tasks Query:{}",componentTable.toString());
			Statement stmt=getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(componentTable.toString());
			while(rs.next()){
				final int projectId=rs.getInt("PROJ_ID");
				final int releaseId = rs.getInt("MLSTN_ID");
				final int componentId=rs.getInt("COMPONENT_ID");
				final int taskId=rs.getInt("TSK_ID");

				
				if(!projMap.isEmpty() && projMap.containsKey(projectId)){
					//second record occurrence
					ProjectForm project=projMap.get(projectId);
					if(!relMap.isEmpty() && relMap.containsKey(releaseId)){
						ReleaseForm release=relMap.get(releaseId);
						if(componentId!=0){
							if(!compMap.isEmpty() && compMap.containsKey(componentId)){
								final ComponentForm component=compMap.get(componentId);
								if(taskId!=0){
									if(!taskMap.isEmpty() && taskMap.containsKey(taskId)){
										final TaskForm task=taskMap.get(taskId);
										List<TaskLedgerForm> taskLedger=task.getTaskLedger();
										if(taskLedger==null){
											taskLedger=new ArrayList<TaskLedgerForm>();
										} 
										mapTaskLedgerData(rs,task,taskLedger);
										
									} else{
										final TaskForm task=new TaskForm();
										mapTaskData(rs, task,component.getComponentId(),taskId);
										if(component.getTaskFormList()==null){
											component.setTaskFormList(new ArrayList<TaskForm>());
										}
										component.getTaskFormList().add(task);
										List<TaskLedgerForm> taskLedger=task.getTaskLedger();
										if(taskLedger==null){
											taskLedger=new ArrayList<TaskLedgerForm>();
										} 
										mapTaskLedgerData(rs,task,taskLedger);
										taskMap.put(taskId, task);
									}
								}
							} else {
								final ComponentForm component=new ComponentForm(); 
								component.setComponentId(componentId);
								mapComponentData(rs, release,component);
								if(taskId!=0){
									if(!taskMap.isEmpty() && taskMap.containsKey(taskId)){
										final TaskForm task=taskMap.get(taskId);
										List<TaskLedgerForm> taskLedger=task.getTaskLedger();
										if(taskLedger==null){
											taskLedger=new ArrayList<TaskLedgerForm>();
										} 
										mapTaskLedgerData(rs,task,taskLedger);
										
									} else{
										final TaskForm task=new TaskForm();
										mapTaskData(rs, task,component.getComponentId(),taskId);
										if(component.getTaskFormList()==null){
											component.setTaskFormList(new ArrayList<TaskForm>());
										}
										component.getTaskFormList().add(task);
										List<TaskLedgerForm> taskLedger=task.getTaskLedger();
										if(taskLedger==null){
											taskLedger=new ArrayList<TaskLedgerForm>();
										} 
										mapTaskLedgerData(rs,task,taskLedger);
										taskMap.put(taskId, task);
									}
								}
								compMap.put(componentId, component);
							}
						}

					} else {
						final ReleaseForm release=new ReleaseForm();
						mapReleaseData(rs, project, release, releaseId);
						if(componentId!=0){
							if(!compMap.isEmpty() && compMap.containsKey(componentId)){
								final ComponentForm component=compMap.get(componentId);
								if(taskId!=0){
									if(!taskMap.isEmpty() && taskMap.containsKey(taskId)){
										final TaskForm task=taskMap.get(taskId);
										List<TaskLedgerForm> taskLedger=task.getTaskLedger();
										if(taskLedger==null){
											taskLedger=new ArrayList<TaskLedgerForm>();
										} 
										mapTaskLedgerData(rs,task,taskLedger);
										
									} else{
										final TaskForm task=new TaskForm();
										mapTaskData(rs, task,component.getComponentId(),taskId);
										if(component.getTaskFormList()==null){
											component.setTaskFormList(new ArrayList<TaskForm>());
										}
										component.getTaskFormList().add(task);
										List<TaskLedgerForm> taskLedger=task.getTaskLedger();
										if(taskLedger==null){
											taskLedger=new ArrayList<TaskLedgerForm>();
										} 
										mapTaskLedgerData(rs,task,taskLedger);
										taskMap.put(taskId, task);
									}
								}
							} else {
								final ComponentForm component=new ComponentForm(); 
								component.setComponentId(componentId);
								mapComponentData(rs, release,component);
								if(taskId!=0){
									if(!taskMap.isEmpty() && taskMap.containsKey(taskId)){
										final TaskForm task=taskMap.get(taskId);
										List<TaskLedgerForm> taskLedger=task.getTaskLedger();
										if(taskLedger==null){
											taskLedger=new ArrayList<TaskLedgerForm>();
										} 
										mapTaskLedgerData(rs,task,taskLedger);
										
									} else{
										final TaskForm task=new TaskForm();
										mapTaskData(rs, task,component.getComponentId(),taskId);
										if(component.getTaskFormList()==null){
											component.setTaskFormList(new ArrayList<TaskForm>());
										}
										component.getTaskFormList().add(task);
										List<TaskLedgerForm> taskLedger=task.getTaskLedger();
										if(taskLedger==null){
											taskLedger=new ArrayList<TaskLedgerForm>();
										} 
										mapTaskLedgerData(rs,task,taskLedger);
										taskMap.put(taskId, task);
									}
								}
								compMap.put(componentId, component);
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
						if(!compMap.isEmpty() && compMap.containsKey(componentId)){
							final ComponentForm component=compMap.get(componentId);
							if(taskId!=0){
								if(!taskMap.isEmpty() && taskMap.containsKey(taskId)){
									final TaskForm task=taskMap.get(taskId);
									List<TaskLedgerForm> taskLedger=task.getTaskLedger();
									if(taskLedger==null){
										taskLedger=new ArrayList<TaskLedgerForm>();
									} 
									mapTaskLedgerData(rs,task,taskLedger);
									
								} else{
									final TaskForm task=new TaskForm();
									mapTaskData(rs, task,component.getComponentId(),taskId);
									if(component.getTaskFormList()==null){
										component.setTaskFormList(new ArrayList<TaskForm>());
									}
									component.getTaskFormList().add(task);
									List<TaskLedgerForm> taskLedger=task.getTaskLedger();
									if(taskLedger==null){
										taskLedger=new ArrayList<TaskLedgerForm>();
									} 
									mapTaskLedgerData(rs,task,taskLedger);
									taskMap.put(taskId, task);
								}
							}
						} else {
							final ComponentForm component=new ComponentForm(); 
							component.setComponentId(componentId);
							mapComponentData(rs, release,component);
							if(taskId!=0){
								if(!taskMap.isEmpty() && taskMap.containsKey(taskId)){
									final TaskForm task=taskMap.get(taskId);
									List<TaskLedgerForm> taskLedger=task.getTaskLedger();
									if(taskLedger==null){
										taskLedger=new ArrayList<TaskLedgerForm>();
									} 
									mapTaskLedgerData(rs,task,taskLedger);
									
								} else{
									final TaskForm task=new TaskForm();
									mapTaskData(rs, task,component.getComponentId(),taskId);
									if(component.getTaskFormList()==null){
										component.setTaskFormList(new ArrayList<TaskForm>());
									}
									component.getTaskFormList().add(task);
									List<TaskLedgerForm> taskLedger=task.getTaskLedger();
									if(taskLedger==null){
										taskLedger=new ArrayList<TaskLedgerForm>();
									} 
									mapTaskLedgerData(rs,task,taskLedger);
									
									taskMap.put(taskId, task);
								}
							}
							compMap.put(componentId, component);
						}
						projMap.put(projectId, project);
						relMap.put(releaseId, release);
						projectTasks.add(project);
					
					}
				}
				
			}
			//Add review task to my task bucket
			final Map<Integer,List<TaskForm>> reviewTaskMap=getMyReviewTasks(userId);
			for(ProjectForm project :projectTasks){
				for(ReleaseForm release:project.getReleases()){
					for(ComponentForm component:release.getComponents()){
						final int componentId=component.getComponentId();
						log.debug("Component Id :{} Name:{} ",componentId,component.getComponentName());
						if(!reviewTaskMap.isEmpty() && reviewTaskMap.containsKey(componentId)){
							List<TaskForm> reviewTasks=reviewTaskMap.get(componentId);
							log.debug("\t Review Task size:{} ",reviewTasks.size());
							final List<TaskForm> myTasks=component.getTaskFormList();
							log.debug("\t Work Task size:{} ",myTasks.size());
							myTasks.addAll(reviewTasks);
						}
					}
				}
			}
			
			//Display logic

			for(ProjectForm project :projectTasks){
				for(ReleaseForm release:project.getReleases()){
					for(ComponentForm component:release.getComponents()){
						final int componentId=component.getComponentId();
						log.debug("Component Id :{} Name:{} ",componentId,component.getComponentName());
						for(TaskForm task:component.getTaskFormList()){
							log.debug("\t Task Name:{}",task.getTaskName());
							if(task.getTaskLedger()!=null){
								for(TaskLedgerForm ledger:task.getTaskLedger()){
									log.debug("\t\t Sub Task:{}",ledger.getTaskActivity());
								}
							}
						}
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


public List<ProjectForm> getMyTeamTasks(Integer supervisorId) {
	
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
	public List<ReferenceData> getMyTeamIds(Integer supervisorId) {
		
		final List<ReferenceData> userIdList = new ArrayList<ReferenceData>();
		try {

					
			String userIdQuery = " SELECT DISTINCT A.EMP_ID,A.EMP_RESOURCE_NAME FROM (EDB_MSTR_EMP_DTLS AS A" + 
								 " LEFT JOIN EDB_PROJ_EMP AS B ON A.EMP_ID = B.EMP_ID)" +
					             " LEFT JOIN EDB_PROJECT AS C ON B.PROJ_ID=C.PROJ_ID" +
					             " WHERE C.PROJ_LEAD="+supervisorId;
			
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
	
	@SuppressWarnings("resource")
	public List<VacationForm> getVacationDetails(Integer employeeId){
		
		{
		
			final List<VacationForm> vactionDetails=new ArrayList<VacationForm>();
			try {

				
				
				final String query="SELECT * FROM EDB_VACTN_CALNDR WHERE SUP_ID="+employeeId;
				log.debug("getTaskById Query :{}",query);
				Statement selectStatement = getConnection().createStatement();
				ResultSet rs = selectStatement.executeQuery(query);
				
				
				while (rs.next()) {
					VacationForm details = new VacationForm();
					
					details.setVacationId(rs.getInt("ID"));
					details.setApproverComments(rs.getString("SUP_COMNTS"));
					details.setComments(rs.getString("COMNTS"));
					details.setStartDate(rs.getString("VACTN_STRT_DT"));
					details.setEndDate(rs.getString("VACTN_END_DT"));
					details.setStatus(rs.getString("STATUS"));
					details.setVacationType(rs.getString("VACTN_TYP"));
					String empId= rs.getString("EMP_ID");

					System.out.println("the emps reporting are::" + empId);
					final String resourceQuery = "SELECT EMP_RESOURCE_NAME FROM EDB_MSTR_EMP_DTLS WHERE EMP_ID = "+empId;
					log.debug("get resource name Query :{}",resourceQuery);
					selectStatement = getConnection().createStatement();
					ResultSet rsEmpname = selectStatement.executeQuery(query);
					rsEmpname = selectStatement.executeQuery(resourceQuery);
								
					while(rsEmpname.next())
					{
						details.setResourceName(rsEmpname.getString("EMP_RESOURCE_NAME"));
					}
					vactionDetails.add(details);
				
				}
				String testquery = "SELECT COUNT(*) as COUNT FROM EDB_VACTN_CALNDR WHERE SUP_ID="+employeeId;
				selectStatement = getConnection().createStatement();
				rs = selectStatement.executeQuery(testquery);
				
					while (rs.next()) {
						System.out.println("number of rows returned is ::" + rs.getString("COUNT"));
				}
				
				return vactionDetails;
			} catch (Exception e) {
				log.error("Error in getTasksByComponentId:",e);
			}
			return vactionDetails;
		}
		
	}
	
	public List<VacationForm> getDeveloperVacationDetails(Integer employeeId){

		{

			final List<VacationForm> vactionDetails=new ArrayList<VacationForm>();
			try {


				final String supIdQuery = "SELECT EMP_SUP_EMP_ID  FROM EDB_MSTR_EMP_DTLS WHERE EMP_ID ="+employeeId;
				log.debug("getTaskById Query :{}",supIdQuery);
				Statement selectStatement = getConnection().createStatement();
				ResultSet supRs = selectStatement.executeQuery(supIdQuery);
				Integer sup_id =0;
				while(supRs.next())
				{
					sup_id = supRs.getInt("EMP_SUP_EMP_ID");

				}

				if(sup_id!= 0)
				{

					final String query="SELECT * FROM EDB_VACTN_CALNDR WHERE SUP_ID="+sup_id;
					log.debug("getTaskById Query :{}",query);
					selectStatement = getConnection().createStatement();
					ResultSet rs = selectStatement.executeQuery(query);


					while (rs.next()) {
						VacationForm details = new VacationForm();

						details.setVacationId(rs.getInt("ID"));
						details.setComments(rs.getString("COMNTS"));
						details.setStartDate(rs.getString("VACTN_STRT_DT"));
						details.setEndDate(rs.getString("VACTN_END_DT"));
						details.setStatus(rs.getString("STATUS"));
						details.setVacationType(rs.getString("VACTN_TYP"));
						details.setRole("DEVLP");
						int empId= rs.getInt("EMP_ID");
						System.out.println("employee id ::" + employeeId);
						if(empId== employeeId)
						{
							details.setViewFlag("TRUE");
							System.out.println("flag set true::");
						}
						System.out.println("the emps reporting are::" + empId);
						final String resourceQuery = "SELECT EMP_RESOURCE_NAME FROM EDB_MSTR_EMP_DTLS WHERE EMP_ID = "+empId;
						log.debug("get resource name Query :{}",resourceQuery);
						selectStatement = getConnection().createStatement();
						ResultSet rsEmpname = selectStatement.executeQuery(query);
						rsEmpname = selectStatement.executeQuery(resourceQuery);

						while(rsEmpname.next())
						{
							details.setResourceName(rsEmpname.getString("EMP_RESOURCE_NAME"));
						}
						vactionDetails.add(details);

					}


					return vactionDetails;
				}
			} catch (Exception e) {
				log.error("Error in getTasksByComponentId:",e);
			}
			return vactionDetails;
		}

	}

	public String editVacation(VacationForm vacationForm)
	{
		try {


			System.out.println("record to be updated::"+ vacationForm.getVacationId());
			
			final String updIdQuery = "UPDATE EDB_VACTN_CALNDR SET VACTN_STRT_DT= ?, VACTN_END_DT= ? ,COMNTS=? WHERE ID=?";
			PreparedStatement prpStatement = getConnection().prepareStatement(updIdQuery);
			prpStatement.setString(1, vacationForm.getStartDate());
			prpStatement.setString(2, vacationForm.getEndDate());
			prpStatement.setString(3, vacationForm.getComments());
			prpStatement.setInt(4, vacationForm.getVacationId());
			log.debug("delIdQuery Query :{}",updIdQuery);
			int rowcount =0;
			rowcount= prpStatement.executeUpdate();
			prpStatement.close();
		
			System.out.println("rows UPDATED");
			//rowcount= selectStatement.executeUpdate(delIdQuery);
			if(rowcount==0)
			{
				System.out.println("update failure");

			}
			else{

				System.out.println("update success");
				return "success";
			}
		} catch (Exception e) {
			log.error("Error in updating vacation records:",e);
		}



		return "success";
	}

	public void deleteVacation(int vacationId)
	{

		try {

			System.out.println("record to be deleted::"+ vacationId);
			final String delIdQuery = "DELETE FROM EDB_VACTN_CALNDR WHERE ID="+vacationId;
			log.debug("delIdQuery Query :{}",delIdQuery);
			Statement selectStatement = getConnection().createStatement();
			int rowcount =0;
			System.out.println("rows deleted");
			rowcount= selectStatement.executeUpdate(delIdQuery);
			if(rowcount==0)
			{
				System.out.println("update failure");

			}
			else{

				System.out.println("update success");
			}
		} catch (Exception e) {
			log.error("Error in deleteing vacation records:",e);
		}

	}

	public void mapLegendData(ResultSet rs, TaskLedgerForm taskLedgerForm,
			Integer componentId, Integer taskId) throws SQLException,
			ParseException {
		// TODO Auto-generated method stub
		
	}
}
