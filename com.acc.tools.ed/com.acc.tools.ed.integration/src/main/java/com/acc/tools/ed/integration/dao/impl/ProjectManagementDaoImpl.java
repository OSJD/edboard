package com.acc.tools.ed.integration.dao.impl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.ProjectManagementDao;
import com.acc.tools.ed.integration.dto.ComponentForm;
import com.acc.tools.ed.integration.dto.EditProjectForm;
import com.acc.tools.ed.integration.dto.MasterEmployeeDetails;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.dto.WeekDates;

/**
 * 
 * @author sarika.ashokkumar
 *
 */

@Service("projectManagementDao")
public class ProjectManagementDaoImpl extends AbstractEdbDao implements ProjectManagementDao{ 
	
	Logger log=LoggerFactory.getLogger(ProjectManagementDaoImpl.class);
	
	
	public Map<String,List<WeekDates>> getVacationDetailsByEmployeeIds(List<ReferenceData> employeeIds){
		final Map<String,List<WeekDates>> vacationDetailsMap=new LinkedHashMap<String, List<WeekDates>>();
		StringBuilder empIds=new StringBuilder();
		 for(ReferenceData data :employeeIds){
			 empIds.append(data.getId()).append(",");
		 }

		final String query="select EMP_ID,VACTN_STRT_DT,VACTN_END_DT from EDB_VACTN_CALNDR WHERE STATUS='Approved' AND EMP_ID IN ("+empIds.substring(0, empIds.lastIndexOf(",")).toString()+")";
		log.debug("getVacationDetailsByEmployeeIds Query:{}",query);
		try {
			Statement stmt=getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()){
				final WeekDates dates=new WeekDates();
				final Integer employeeId=rs.getInt("EMP_ID");
        		dates.setWeekStartDate(new DateTime(rs.getDate("VACTN_STRT_DT").getTime()));
        		dates.setWeekEndDate(new DateTime(rs.getDate("VACTN_END_DT").getTime()));
        		if(!vacationDetailsMap.isEmpty() && vacationDetailsMap.containsKey(""+employeeId)){
        			vacationDetailsMap.get(employeeId).add(dates);
        		} else {
        			final List<WeekDates> dateList=new ArrayList<WeekDates>();
        			dateList.add(dates);
        			vacationDetailsMap.put(""+employeeId,dateList);
        		}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vacationDetailsMap;
	}

	public List<ReferenceData> getAllProjectIds(){
		
		List<ReferenceData> projectList=new ArrayList<ReferenceData>();
		final String query="select PROJ_NAME, PROJ_ID from EDB_PROJECT";
		try {
			Statement stmt=getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()){
				ReferenceData refData=new ReferenceData();
				final String projId=rs.getString("PROJ_ID");
				final String projName=rs.getString("PROJ_NAME");
				refData.setId(projId);
				refData.setLabel(projName);
				projectList.add(refData);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectList;
	}
	
	public List<ReferenceData> getProjectReleaseIds(String projectId){
		List<ReferenceData> projectReleaseList=new ArrayList<ReferenceData>();
		final String query="select MLSTN_ID,MLSTN_NAME from EDB_MILESTONE where PROJ_ID="+projectId+"";
		log.debug("RELEASE QUERY :[{}]",query);
		try {
			Statement stmt=getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()){
				ReferenceData refData=new ReferenceData();
				refData.setId(""+rs.getInt("MLSTN_ID"));
				refData.setLabel(rs.getString("MLSTN_NAME"));
				projectReleaseList.add(refData);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectReleaseList;
	}
		
	public ReferenceData addProject(ProjectForm project) {
		
		final ReferenceData refData=new ReferenceData();

		try{
				//Insert Program
				log.debug("New Program Name:{} Existing Program id:{}",project.getNewProgramName(),project.getExistingProgram());
				if(project.getNewProgramName() != null && !project.getNewProgramName().isEmpty() && project.getExistingProgram()==-1) {
					log.debug("New Program Name:[{}] | New Program Id:[{}]",project.getNewProgramName(),project.getNewProgramId());
					String prgInsQuery = "insert into EDB_PROGRAM(PRGM_ID,PRGM_NAME) values (?,?)";
					PreparedStatement prgmPrepStmt = getConnection().prepareStatement(prgInsQuery);
					prgmPrepStmt.setInt(1, project.getNewProgramId());
					prgmPrepStmt.setString(2, project.getNewProgramName());
					prgmPrepStmt.executeUpdate();
					prgmPrepStmt.close();
					project.setExistingProgram(project.getNewProgramId());
					
				}
				
				//Insert Project
				final String employeeTable="insert into EDB_PROJECT(PROJ_ID,PROJ_NAME, PROJ_DESC, PROJ_LEAD, PROJ_ST_DT, PROJ_ET_DT, PROJ_PHSE, PRGM_ID) values (?,?,?,?,?,?,?,?)";
				PreparedStatement  preparedStatement = getConnection().prepareStatement(employeeTable);
				preparedStatement.setInt(1, project.getProjectId());
				preparedStatement.setString(2, project.getProjectName());
				preparedStatement.setString(3, project.getProjectDescription());
				preparedStatement.setString(4, project.getProjectLead());
				preparedStatement.setString(5, project.getStartDate().toString("yyyy-MM-dd"));
				preparedStatement.setString(6, project.getEndDate().toString("yyyy-MM-dd"));
				preparedStatement.setString(7, project.getPhases().toString());
				preparedStatement.setInt(8, project.getExistingProgram());
				preparedStatement.executeUpdate();
				refData.setId(Integer.toString(project.getProjectId()));
				refData.setLabel(project.getProjectName());
				refData.setSelected(true);
				preparedStatement.close();
				
				//Set Project to Employee relationship
				final String projEmpTable="insert into EDB_PROJ_EMP(PROJ_ID,EMP_ID) values (?,?)";
				PreparedStatement  projEmpStmt = getConnection().prepareStatement(projEmpTable);
				for(ReferenceData employeeRefData:project.getResources()){
					projEmpStmt.setInt(1, project.getProjectId());
					projEmpStmt.setString(2, employeeRefData.getId());
					projEmpStmt.addBatch();
				}
				projEmpStmt.executeBatch();
				projEmpStmt.close();
				
			}catch(Exception e)	{
				log.error("Error inserting employee table :",e);
				refData.setId("-1");
				refData.setLabel(e.getMessage());
				return refData;
			}
		return refData;
	}
	
	
	public ReferenceData addRelease(ReleaseForm release){
		
		final ReferenceData refData=new ReferenceData();

		try{
				log.debug("Project Id:{} | Release Name:{} | Start Date:{} | End Date:{} | Artifacts:{}",new Object[]{release.getProjectId(),release.getReleaseName(),
						release.getReleaseStartDate(),release.getReleaseEndDate(),release.getReleaseArtifacts().toString()});	
				final String employeeTable="insert into EDB_MILESTONE(PROJ_ID,MLSTN_NAME,MLSTN_ST_DT,MLSTN_END_DT,MLSTN_ARTIFACTS) values (?,?,?,?,?)";
				PreparedStatement  preparedStatement = getConnection().prepareStatement(employeeTable);
				preparedStatement.setInt(1, release.getProjectId());
				preparedStatement.setString(2, release.getReleaseName());
				preparedStatement.setString(3, release.getReleaseStartDate());
				preparedStatement.setString(4, release.getReleaseEndDate());
				preparedStatement.setString(5, release.getReleaseArtifacts().toString());
				preparedStatement.executeUpdate();
				preparedStatement.close();
				
				int mlstnId = 0;
				Statement  statement = getConnection().createStatement();
				ResultSet rs = statement.executeQuery("SELECT MAX(MLSTN_ID) FROM EDB_MILESTONE");
				while(rs.next()){
					mlstnId = rs.getInt(1);
				}
				statement.close();
				
				refData.setId(String.valueOf(mlstnId));
				refData.setLabel(release.getReleaseName());
			}catch(Exception e)	{
				log.error("Error inserting release table :",e);
				return null;
			}
		return refData;
	}
	
	public int addEmployee(MasterEmployeeDetails empDetail) {
		
		int status=0;

		try{
				
				//Employee table
				final String employeeTable="insert into EDB_MSTR_EMP_DTLS(EMP_RESOURCE_NAME,EMP_ENTERPRISE_ID,EMP_EMPLOYEE_ID,EMP_RRD_NO,EMP_LEVEL,EMP_ROLE)  values (?,?,?,?,?,?)";
						//+ "EMP_BILL_CODE,EMP_PROJECT,EMP_WORK_LOCATION,EMP_DESK_NO,EMP_ASSET_TAG_NO,EMP_SERVICE_TAG_NO,EMP_780_MACHINE,"
						//+ "EMP_4GB_RAM,EMP_HARDLOCK_DATE,EMP_IJP_ROLLOFF_DATE,EMP_MOBILE_NO,EMP_DOJ_ACCENTURE,)
				PreparedStatement  preparedStatement = getConnection().prepareStatement(employeeTable);
				preparedStatement.setString(1, empDetail.getEmployeeName());
				preparedStatement.setString(2, empDetail.getEmployeeEnterpId());
				preparedStatement.setString(3, empDetail.getEmployeeSAPId());
				preparedStatement.setString(4, empDetail.getEmployeeRRDNo());
				preparedStatement.setString(5, empDetail.getEmployeeLevel());
				preparedStatement.setString(6, empDetail.getEmployeeRole());
				status=preparedStatement.executeUpdate();
				preparedStatement.close();
			}catch(Exception e)	{
				log.error("Error inserting employee table :",e);
				return -1;
			}
		return status;
	}
	
	public ProjectForm getProjectPlanDetails(Integer releaseId, Integer projectId) {
        
        ProjectForm projectPlanData = new ProjectForm();
        final StringBuffer projPlanQuery =new StringBuffer();
        final Map<Integer,ReleaseForm> releaseMap=new HashMap<Integer,ReleaseForm>();
        projPlanQuery.append("SELECT P.*, M.*, C.*,CE.*, E.EMP_ENTERPRISE_ID,E.EMP_RESOURCE_NAME FROM (((EDB_PROJECT AS P LEFT JOIN EDB_MILESTONE AS M ON P.PROJ_ID = M.PROJ_ID) ");
        projPlanQuery.append("LEFT JOIN EDB_PROJ_COMPNT AS C ON M.MLSTN_ID = C.MLSTN_ID) "); 
        projPlanQuery.append("LEFT JOIN EDB_COMPNT_EMP AS CE ON CE.COMPNT_ID=C.COMPNT_ID ) ");
        projPlanQuery.append("LEFT JOIN EDB_MSTR_EMP_DTLS E ON E.EMP_ID=CE.EMP_ID WHERE M.MLSTN_ID="+releaseId+" AND P.PROJ_ID="+projectId);
        
        log.debug("RELEASE QUERY :[{}]",projPlanQuery);
        
        try {
                     PreparedStatement  preparedStatement = getConnection().prepareStatement(projPlanQuery.toString());
                     ResultSet rs = preparedStatement.executeQuery();
                     
                     while(rs.next()){
                    	 final int projId=rs.getInt("PROJ_ID");
                    	 	projectPlanData.setProjectId(projId);
                            projectPlanData.setProjectName(rs.getString("PROJ_NAME"));
                            projectPlanData.setProjectDescription(rs.getString("PROJ_DESC"));
                            
                            String phases = rs.getString("PROJ_PHSE");
                            projectPlanData.setPhases(Arrays.asList(phases.replace("[", "").replace("]", "").trim().split(",")));
                            
                            projectPlanData.setStartDate(new DateTime(rs.getTimestamp("PROJ_ST_DT").getTime()));
                            projectPlanData.setEndDate(new DateTime(rs.getTimestamp("PROJ_ET_DT").getTime()));
                            
                            String leadId=rs.getString("PROJ_LEAD");
                            //Get Lead name
                            final String projLeadQuery="SELECT EMP_RESOURCE_NAME FROM EDB_MSTR_EMP_DTLS WHERE EMP_ID="+leadId;
                            PreparedStatement  leadPreStmt = getConnection().prepareStatement(projLeadQuery);
                            ResultSet leadRs = leadPreStmt.executeQuery();
                            while(leadRs.next()){ 
                           	 projectPlanData.setProjectLead(leadRs.getString("EMP_RESOURCE_NAME")); 
                            }
                            
                            final int rReleaseId=rs.getInt("MLSTN_ID");
                            
                            if(!releaseMap.isEmpty() && releaseMap.containsKey(rReleaseId)){
                            	final ReleaseForm release=releaseMap.get(rReleaseId);
                            	final ComponentForm component = new ComponentForm();
                            	component.setComponentId(rs.getInt("COMPNT_ID"));
            			        component.setResourceName(rs.getString("EMP_ENTERPRISE_ID"));
            			        log.debug("Component Id:{}",component.getComponentId());
            			        if(component.getComponentId()!=0)
            			        	mapComponentData(rs,release,component);
                            } else {
                            	ReleaseForm release=new ReleaseForm(); 
                                release.setProjectId(projId);
                                release.setReleaseId(rReleaseId);
                                release.setReleaseName(rs.getString("MLSTN_NAME"));
                                release.setReleaseArtifacts(rs.getString("MLSTN_ARTIFACTS"));
                                
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                Date stDate =  sdf.parse(rs.getString("MLSTN_ST_DT"));


                                sdf.applyPattern("MM/dd/yyyy");
                                release.setReleaseStartDate(sdf.format(stDate));
                                
                                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                Date etDate =  sdf1.parse(rs.getString("MLSTN_END_DT"));
                                sdf1.applyPattern("MM/dd/yyyy");                
                                release.setReleaseEndDate(sdf1.format(etDate));
                                
                                if(projectPlanData.getReleases()==null){
                                	projectPlanData.setReleases(new ArrayList<ReleaseForm>());
                                }
                                projectPlanData.getReleases().add(release);
                                final ComponentForm component = new ComponentForm();
                                mapComponentData(rs,release,component);
                                releaseMap.put(rReleaseId, release);
                                
                            }
                     }   
                     
                     
                     List<ReferenceData> projectResourceList = getProjectResourceDetails(projectId);
                     projectPlanData.setResources(projectResourceList);
                     
        } catch (Exception e) {
               e.printStackTrace();
        }
        
        return projectPlanData;
 }

	/**
	 * List of developers tagged to the project
	 * @param projectId
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<ReferenceData> getProjectResourceDetails(Integer projectId)
			 {
		List<ReferenceData> projectResourceList = new ArrayList<ReferenceData>();
		try {
		final String projResourceQuery="SELECT E.EMP_ID,E.EMP_RESOURCE_NAME FROM EDB_MSTR_EMP_DTLS E, EDB_PROJ_EMP PE 	WHERE E.EMP_ID=PE.EMP_ID AND PE.PROJ_ID="+projectId;
		 PreparedStatement  resourcePreStmt = getConnection().prepareStatement(projResourceQuery);
		 ResultSet resRs = resourcePreStmt.executeQuery();
		                          
		 while(resRs.next()){ 
			 ReferenceData refData=new ReferenceData();
			 refData.setId(resRs.getString("EMP_ID"));
			 refData.setLabel(resRs.getString("EMP_RESOURCE_NAME"));
			 projectResourceList.add(refData);                    	 
		 }
		}catch(Exception e){
			e.printStackTrace();
		} 
		return projectResourceList;
	}
	

	
	public List<ReferenceData> editProject(String projectId,String editPrjDesc,String editPrjStartDate,String editPrjEndDate){
		
		List<ReferenceData> response = new ArrayList<ReferenceData>();

		try{
				//Project table
				final String employeeTable="UPDATE EDB_PROJECT SET PROJ_DESC = ?, PROJ_ST_DT =?, PROJ_ET_DT=? WHERE PROJ_ID =?";
				PreparedStatement  preparedStatement = getConnection().prepareStatement(employeeTable);
				preparedStatement.setString(1, editPrjDesc);
				preparedStatement.setString(2, editPrjStartDate);
				preparedStatement.setString(3, editPrjEndDate);
				preparedStatement.setString(4, projectId);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				
				final String projectTable="SELECT * FROM EDB_PROJECT WHERE PROJ_ID ="+projectId+"";
				Statement selectStatement = getConnection().createStatement();
				
				ResultSet rs=selectStatement.executeQuery(projectTable);
				while(rs.next()){
					ReferenceData refDataResp=new ReferenceData();
					//refDataResp.setEditPrjDescResp(rs.getString("PROJ_DESC"));
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date stDate =  sdf.parse(rs.getString("PROJ_ST_DT"));
					Date etDate =  sdf.parse(rs.getString("PROJ_ET_DT"));
					sdf.applyPattern("MM/dd/yyyy");
					//refDataResp.setEditPrjStartDateResp(sdf.format(stDate));
					//refDataResp.setEditPrjEndDateResp(sdf.format(etDate));
					response.add(refDataResp);
				}
				
			}catch(Exception e)	{
				e.printStackTrace();
			}
		return response;
		
	}
	
public List<ReferenceData> editRelease(String releaseId,String editRelArti,String editRelStartDate,String editRelEndDate){
		
		List<ReferenceData> response = new ArrayList<ReferenceData>();

		try{
				//Project table
				final String employeeTable="UPDATE EDB_MILESTONE SET MLSTN_ARTIFACTS = ?, MLSTN_ST_DT =?, MLSTN_END_DT=? WHERE MLSTN_ID =?";
				PreparedStatement  preparedStatement = getConnection().prepareStatement(employeeTable);
				preparedStatement.setString(1, editRelArti);
				preparedStatement.setString(2, editRelStartDate);
				preparedStatement.setString(3, editRelEndDate);
				preparedStatement.setString(4, releaseId);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				
				String releaseTable="SELECT * FROM EDB_MILESTONE WHERE MLSTN_ID ="+releaseId+"";
				Statement selectStatement = getConnection().createStatement();
				
				ResultSet rs=selectStatement.executeQuery(releaseTable);
				while(rs.next()){
					ReferenceData refDataResp=new ReferenceData();
					//refDataResp.setEditRelArtiResp(rs.getString("MLSTN_ARTIFACTS"));
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date stDate =  sdf.parse(rs.getString("MLSTN_ST_DT"));
					Date etDate =  sdf.parse(rs.getString("MLSTN_END_DT"));
					sdf.applyPattern("MM/dd/yyyy");
					//refDataResp.setEditRelStartDateResp(sdf.format(stDate));
					//refDataResp.setEditRelEndDateResp(sdf.format(etDate));
					response.add(refDataResp);
				}
				
			}catch(Exception e)	{
				e.printStackTrace();
			}
		return response;
		
	}

	public String deleteProject(String projectId) {
		
		try {
			
			final String prjTable="DELETE FROM EDB_PROJECT WHERE PROJ_ID = ?";
			PreparedStatement  preparedStatement = getConnection().prepareStatement(prjTable);
			preparedStatement.setString(1, projectId);
			preparedStatement.executeUpdate();
			
			final String relTable="DELETE FROM EDB_MILESTONE WHERE PROJ_ID = ?";
			PreparedStatement  relStatement = getConnection().prepareStatement(relTable);
			relStatement.setString(1, projectId);
			relStatement.executeUpdate();
			
			preparedStatement.close();
			relStatement.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "";
	}
	


	public String deleteRelease(String releaseId) {
		
		try {

			final String relTable="DELETE FROM EDB_MILESTONE WHERE MLSTN_ID = ?";
			PreparedStatement  relStatement = getConnection().prepareStatement(relTable);
			relStatement.setString(1, releaseId);
			relStatement.executeUpdate();
			relStatement.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "";
	}
	
	public List<ReferenceData> getProgramList() {
		
		List<ReferenceData> response = new ArrayList<ReferenceData>();
		
		try {
			
			String programTable="SELECT * FROM EDB_PROGRAM";
			Statement selectStatement = getConnection().createStatement();
			ResultSet rs=selectStatement.executeQuery(programTable);
			
			while(rs.next()){
				ReferenceData refDataResp=new ReferenceData();
				refDataResp.setId(rs.getString("PRGM_ID"));
				refDataResp.setLabel(rs.getString("PRGM_NAME"));
				response.add(refDataResp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public List<ReferenceData> getResourceList() {
		
		List<ReferenceData> resourceList = new ArrayList<ReferenceData>();
		
		try {
			String resourceQuery = "SELECT EMP_ID,EMP_RESOURCE_NAME FROM EDB_MSTR_EMP_DTLS";
			Statement selectStatement = getConnection().createStatement();
			ResultSet rs = selectStatement.executeQuery(resourceQuery);
			
			while (rs.next()) {
				ReferenceData referenceData = new ReferenceData();
				referenceData.setId(rs.getString("EMP_ID"));
				referenceData.setLabel(rs.getString("EMP_RESOURCE_NAME"));
				resourceList.add(referenceData);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return resourceList;
		
	}
	
	public List<ReferenceData> getPrjLeadList() {
		
		List<ReferenceData> prjLeadList = new ArrayList<ReferenceData>();
		
		try {
			String resourceQuery = "SELECT * FROM EDB_MSTR_EMP_DTLS WHERE EMP_LEVEL IN ('AM','TL')";
			Statement selectStatement = getConnection().createStatement();
			ResultSet rs = selectStatement.executeQuery(resourceQuery);
			
			while (rs.next()) {
				ReferenceData referenceData = new ReferenceData();
				referenceData.setId(rs.getString("EMP_ID"));
				referenceData.setLabel(rs.getString("EMP_RESOURCE_NAME"));
				prjLeadList.add(referenceData);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}		
		return prjLeadList;
	}
	
	public Map<String,String> getProjectDate(String projectId) {
		
		Map<String,String> projectDates = new HashMap<String,String>();
		
		try {
			
			final String prjTable="SELECT PROJ_ST_DT,PROJ_ET_DT FROM EDB_PROJECT WHERE PROJ_ID = "+projectId;
			Statement selectStatement = getConnection().createStatement();
			ResultSet rs = selectStatement.executeQuery(prjTable);
			
			while (rs.next()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				DateTime stDate =  new DateTime(sdf.parse(rs.getString("PROJ_ST_DT")));
				DateTime etDate =  new DateTime(sdf.parse(rs.getString("PROJ_ET_DT")));
				projectDates.put("projectStartDate", stDate.toString("MM/dd/yyyy"));
				projectDates.put("projectEndDate", etDate.toString("MM/dd/yyyy"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return projectDates;
	}
	
	public ComponentForm addComponent(Integer projectId,Integer phaseId,String componentName,String functionalDesc,
			String compStartDate,String compEndDate,String compResource, Integer releaseId, String workDesc) {
		
		ComponentForm compDet=null;
		try{

					compDet = getComponentDetails(phaseId, componentName,releaseId);
					
					if(compDet==null){
						final String employeeTable="insert into EDB_PROJ_COMPNT(COMPNT_PHASE,COMPNT_NAME,COMPNT_FUNC_DESC,COMPNT_ST_DT,COMPNT_END_DT,MLSTN_ID) values (?,?,?,?,?,?)";
						PreparedStatement  preparedStatement = getConnection().prepareStatement(employeeTable);
						preparedStatement.setInt(1, phaseId);
						preparedStatement.setString(2, componentName);
						preparedStatement.setString(3, functionalDesc);
						preparedStatement.setString(4, compStartDate);
						preparedStatement.setString(5, compEndDate);
						preparedStatement.setInt(6, releaseId);
						preparedStatement.executeUpdate();
						preparedStatement.close();
						
						compDet = getComponentDetails(phaseId, componentName,releaseId);
						
						if(compDet!=null && compDet.getComponentId()!=0 && !isComponentAssignedToEmployee(compDet.getComponentId(), Integer.parseInt(compResource))){
							compDet.setResourceId(Integer.parseInt(compResource));
							insertCompEmp(compDet.getComponentId(), phaseId, componentName,compResource,releaseId,workDesc);
						} else {
							throw new RuntimeException("Component '"+componentName+"' with "+phaseId+" phase is already assigned to resource "+compResource);
						}
					} else {
						if(compDet.getComponentId()!=0){
							compDet.setResourceId(Integer.parseInt(compResource));
							insertCompEmp(compDet.getComponentId(), phaseId, componentName,compResource,releaseId,workDesc);
						}
					}
				
			}catch(Exception e)	{
				log.error("Error inserting EDB_PROJ_COMPNT table :",e);
				return null;
			}
		return compDet;
	}
	
	public ComponentForm getComponentDetails(Integer phaseId, String componentName,
			Integer releaseId) {
		
		ComponentForm componentDet = null;
		try{
			//Employee table
			final StringBuffer compEmpTable=new StringBuffer();
			compEmpTable.append("SELECT COMPNT_ID,COMPNT_ST_DT,COMPNT_END_DT,COMPNT_FUNC_DESC FROM EDB_PROJ_COMPNT WHERE COMPNT_NAME='");
			compEmpTable.append(componentName);
			compEmpTable.append("' AND COMPNT_PHASE=");
			compEmpTable.append(phaseId);
			compEmpTable.append(" AND MLSTN_ID=");
			compEmpTable.append(releaseId);
			PreparedStatement  preparedStatement = getConnection().prepareStatement(compEmpTable.toString());
			log.debug("Component Query:{}",compEmpTable.toString());
			ResultSet r1 = preparedStatement.executeQuery();
			while (r1.next()){
				componentDet = new ComponentForm();
				componentDet.setComponentId(r1.getInt("COMPNT_ID"));
				componentDet.setComponentName(componentName);
				componentDet.setStartDate(r1.getString("COMPNT_ST_DT"));
				componentDet.setEndDate(r1.getString("COMPNT_END_DT"));
				componentDet.setFunctionalDesc(r1.getString("COMPNT_FUNC_DESC"));
				componentDet.setPhaseId(phaseId);
			}

			preparedStatement.close();
		}catch(Exception e)	{
			log.error("Error while retrieving data from  EDB_PROJ_COMPNT:",e);
		}
		return componentDet;
	}
	
	public boolean isComponentAssignedToEmployee(Integer componentId,Integer empId){
		
		boolean componentAssigned=false;
		try{
			final StringBuffer compEmpTable=new StringBuffer();
			compEmpTable.append("SELECT count(*) as COMPNT_IDS FROM EDB_COMPNT_EMP WHERE COMPNT_ID=");
			compEmpTable.append(componentId);
			compEmpTable.append(" AND EMP_ID=");
			compEmpTable.append(empId);
			PreparedStatement  preparedStatement = getConnection().prepareStatement(compEmpTable.toString());
			log.debug("isComponentAssignedToEmployee -> Query : {}",compEmpTable.toString());
			ResultSet r1 = preparedStatement.executeQuery();
			while(r1.next()){
				if(r1.getInt("COMPNT_IDS")!=0){
					componentAssigned=true;
				}
			}

			preparedStatement.close();
		}catch(Exception e)	{
			log.error("Error while retrieving data from  EDB_PROJ_COMPNT:",e);
		}
		return componentAssigned;
	}

	private void insertCompEmp(Integer componentId, Integer phaseId, String componentName, String compResource, Integer releaseId, String workDesc) {
		
		try{

			final String insertCompEmp="insert into EDB_COMPNT_EMP(COMPNT_ID,COMPNT_PHASE,EMP_ID,WORK_DESC) values (?,?,?,?)";
			PreparedStatement  preparedStatement1 = getConnection().prepareStatement(insertCompEmp);
			preparedStatement1.setInt(1, componentId);
			preparedStatement1.setInt(2, phaseId);
			preparedStatement1.setInt(3, Integer.parseInt(compResource));
			preparedStatement1.setString(4, workDesc);
			preparedStatement1.executeUpdate();
			preparedStatement1.close();
			
		}catch(Exception e)	{
			log.error("Error Inserting  EDB_COMPNT_EMP:",e);
		}
	}


	public List<MasterEmployeeDetails> getAllEmployees(){
		List<MasterEmployeeDetails> empList = new ArrayList<MasterEmployeeDetails>();
		try{
			//Employee table
			final StringBuffer employeeTable=new StringBuffer();
			employeeTable.append("SELECT EMP_RESOURCE_NAME,EMP_ENTERPRISE_ID,EMP_EMPLOYEE_ID,");
			employeeTable.append("EMP_LEVEL,EMP_ROLE,EMP_MOBILE_NO,EMP_DOJ_ACCENTURE FROM EDB_MSTR_EMP_DTLS");
			PreparedStatement  preparedStatement = getConnection().prepareStatement(employeeTable.toString());
			ResultSet r1 = preparedStatement.executeQuery();
			while(r1.next()){
				MasterEmployeeDetails emp = new MasterEmployeeDetails();
				emp.setEmployeeName(r1.getString("EMP_RESOURCE_NAME"));
				emp.setEmployeeEnterpId( r1.getString("EMP_ENTERPRISE_ID"));
				emp.setEmployeeSAPId(r1.getString("EMP_EMPLOYEE_ID"));
				emp.setEmployeeLevel(r1.getString("EMP_LEVEL"));
				emp.setEmployeeRole(r1.getString("EMP_ROLE"));
				emp.setEmployeePrimaryContactNo(r1.getString("EMP_MOBILE_NO"));
				emp.setAccentureDOJ(r1.getString("EMP_DOJ_ACCENTURE"));
				empList.add(emp);
			}
			preparedStatement.close();
		}catch(Exception e)	{
			log.error("Error retreiving employee table :",e);
			return null;
		}
		return empList;
	}
    
	/**
     * Method to save release details.
     *
     */
	public void addReleasePlan(int releaseId, String empId,
			LocalDate weekDateStart, LocalDate weekDateEnd,
			List<Long> weekHourList, Long weeklyPlannedHr, boolean isLastWeek) {
		
		    int index=1;
		    final int initalPreparedStmtIndexVal = 5;
		    final int daysPerWeek = 7;
		    List<Long> hrs = new ArrayList<Long>(daysPerWeek);
		try{
            // Day1 in the query is Monday like wise Day2 Tuesday... and Day7 Sunday. A Week is from Monday to Sunday
			final String insertReleasePlan="insert into EDB_RELEASE_PLAN(MLSTN_ID,EMP_ID,WEEK_ST_DT,WEEK_ED_DT,DAY1,DAY2,DAY3,DAY4,DAY5,DAY6,DAY7,PLND_HRS) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement  preparedStatement = getConnection().prepareStatement(insertReleasePlan);
			
			preparedStatement.setInt(index++, releaseId);
			preparedStatement.setInt(index++,Integer.valueOf(empId).intValue());
			preparedStatement.setString(index++, weekDateStart.toString("yyyy-MM-dd"));
			preparedStatement.setString(index++, weekDateEnd.toString("yyyy-MM-dd"));
			//Below condition will be true if the release start date is in Mid of the week
			if(weekHourList.size()<daysPerWeek && !isLastWeek){				
				for (; index < initalPreparedStmtIndexVal+(daysPerWeek-weekHourList.size()); index++) {					
					     preparedStatement.setInt(index,0);
				}
				for (int j=0; j < weekHourList.size(); j++) {
					if(weekHourList.get(j)!=null)
					  preparedStatement.setInt(index++,weekHourList.get(j).intValue());
					else
					  preparedStatement.setInt(index++,0);	
				}
			}
			//Below condition will be true if the release end date is in the Mid of the week and also if both release start and End date fall in the same week
			else if(weekHourList.size()<daysPerWeek && isLastWeek){		
				
				for (int i = 0,j=0; i < daysPerWeek; i++) {
					if(i>= (weekDateStart.getDayOfWeek()-1) && i<= (weekDateEnd.getDayOfWeek()-1))
						hrs.add(i,weekHourList.get(j++) );
					else 
						hrs.add(i, 0L);
				}
				for (Long hr : hrs) {
					if(hr!=null)
					  preparedStatement.setInt(index++,hr.intValue());
					else
					  preparedStatement.setInt(index++,0);
				}
			}
			// This condition is for all 7 days in a week to be saved in DB. That is if the date range is from Mon-Sun.
			else{
			for (int i = initalPreparedStmtIndexVal; i < (initalPreparedStmtIndexVal+weekHourList.size()); i++) {
				if(weekHourList.get(i-initalPreparedStmtIndexVal)!=null)
				  preparedStatement.setInt(index++,weekHourList.get(i-initalPreparedStmtIndexVal).intValue());
				else
			      preparedStatement.setInt(index++,0);	
			  }
			}
			preparedStatement.setInt(index++,weeklyPlannedHr.intValue());				
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
		}catch(Exception e)	{
			log.error("Error Inserting  EDB_RELEASE_PLAN:",e);
		}
		
	}
	
	public List<EditProjectForm> editProject(int projectId) {
		List<EditProjectForm> editProjList = new ArrayList<EditProjectForm>();
		List<String> resources = new ArrayList<String>();
		try {
			String resourceQuery  ="SELECT "+
					"PRGM_NAME, "+
					"PROJ_NAME, "+
					"(SELECT EMP_RESOURCE_NAME  FROM EDB_MSTR_EMP_DTLS WHERE EMP_ID = ( SELECT  PROJ_LEAD FROM EDB_PROJECT  WHERE PROJ_ID  = "+projectId+" )) AS PROJ_LEAD, "+ 
					"PROJ_ST_DT, "+
					"PROJ_ET_DT, "+
					"PROJ_DESC ,"+
					"PROJ_PHSE "+
					"FROM EDB_PROJECT PROJ, EDB_PROGRAM PROG WHERE "+  
					"PROJ.PRGM_ID = PROG.PRGM_ID AND "+
					"PROJ.PROJ_ID  = "+projectId+" ";
					
			final String subQuery = "SELECT EMP_RESOURCE_NAME FROM EDB_MSTR_EMP_DTLS  D ,EDB_PROJ_EMP O WHERE D.EMP_ID IN (SELECT E.EMP_ID FROM "+
					"EDB_PROJ_EMP E, EDB_PROJECT P WHERE P.PROJ_ID = E.PROJ_ID AND P.PROJ_ID = "+projectId+") AND D.EMP_ID = O.EMP_ID ";
			System.out.println(subQuery);
			Statement selectStatement = getConnection().createStatement();
			ResultSet rs = selectStatement.executeQuery(resourceQuery);
			PreparedStatement  resourceStmt = getConnection().prepareStatement(subQuery);
            ResultSet resourceRs = resourceStmt.executeQuery();
            while(resourceRs.next()){ 
            	resources.add(resourceRs.getString("EMP_RESOURCE_NAME"));
            }
            resourceStmt.close();
			while (rs.next()) {
				EditProjectForm editProjectData = new EditProjectForm();
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//				DateTime stDate =  new DateTime(sdf.parse(rs.getString("PROJ_ST_DT")));
//				DateTime etDate =  new DateTime(sdf.parse(rs.getString("PROJ_ET_DT")));
				editProjectData.setNewProgramNameEdit(rs.getString("PRGM_NAME"));
				editProjectData.setProjectNameEdit(rs.getString("PROJ_NAME"));
				editProjectData.setProjectLeadEdit(rs.getString("PROJ_LEAD"));
				editProjectData.setStartDateEdit(rs.getString("PROJ_ST_DT"));
				editProjectData.setEndDateEdit(rs.getString("PROJ_ET_DT"));
				editProjectData.setProjectDescriptionEdit(rs.getString("PROJ_DESC"));
				List<String> phaseList = new ArrayList<String>();
				String arr[] =  rs.getString("PROJ_PHSE").split("[[,]]+");
				for(int i=0;i<arr.length;i++){
					phaseList.add(arr[i]);
				}
				editProjectData.setPhasesEdit(phaseList);
				editProjectData.setSelectedResourcesEdit(resources);
				editProjList.add(editProjectData);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}	
		return editProjList;
	}

	public int checkProjName(String projectName, int progId) {
		int flag = 0;
		try {
				String resourceQuery = "SELECT PROJ_ID FROM EDB_PROJECT WHERE PROJ_NAME = '"+projectName+"' AND PRGM_ID ="+progId+" ";
				Statement selectStatement = getConnection().createStatement();
				ResultSet rs = selectStatement.executeQuery(resourceQuery);
				if (!rs.next()){
					flag = 1;
					System.out.println("Project Not Exist");
				}else{
					flag = 0;
					System.out.println("Project already Exist");
				}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public Map<Integer,List<WeekDates>> getReleasePlan(Integer releaseId) {
		
        final Map<Integer,List<WeekDates>> resourceHoursMap=new HashMap<Integer, List<WeekDates>>();


		final String query = "SELECT * FROM EDB_RELEASE_PLAN WHERE MLSTN_ID="+releaseId;
		log.debug(" RELEASE PLAN HOURS  :{}", query);
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				WeekDates dates = new WeekDates();
				final int employeeId=rs.getInt("EMP_ID");
				dates.setWeekStartDate(new DateTime(rs.getDate("WEEK_ST_DT").getTime()));
				dates.setWeekEndDate(new DateTime(rs.getDate("WEEK_ED_DT").getTime()));
				dates.setDay1(rs.getInt("DAY1"));
				dates.setDay2(rs.getInt("DAY2"));
				dates.setDay3(rs.getInt("DAY3"));
				dates.setDay4(rs.getInt("DAY4"));
				dates.setDay5(rs.getInt("DAY5"));
				dates.setDay6(rs.getInt("DAY6"));
				dates.setDay7(rs.getInt("DAY7"));
				if(!resourceHoursMap.isEmpty() && resourceHoursMap.containsKey(employeeId)){
					List<WeekDates> weekdates=resourceHoursMap.get(employeeId);
					if(weekdates==null){
						weekdates=new LinkedList<WeekDates>();
					}
					weekdates.add(dates);
				} else {
					final List<WeekDates> weekdates=new LinkedList<WeekDates>();
					weekdates.add(dates);
					resourceHoursMap.put(employeeId, weekdates);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resourceHoursMap;

	}	
}

