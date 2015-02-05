package com.acc.tools.ed.integration.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.AdvancedSearchDao;
import com.acc.tools.ed.integration.dto.ResourceDetails;
import com.acc.tools.ed.integration.dto.RrdDetails;
import com.acc.tools.ed.integration.dto.RrdResourceMappingDetails;

/**
 * 
 * @author kiruba.rajaguru
 *
 */

@Service("advancedSearchDao")
public class AdvancedSearchDaoImpl extends AbstractEdbDao implements
		AdvancedSearchDao {

	Logger log = LoggerFactory.getLogger(AdvancedSearchDaoImpl.class);

	public int addResourceMappingDetails(RrdResourceMappingDetails resDetail) {
		int status = 0;
		try {
			// Employee table
			final String resourcemappingTable = "insert into EDB_ROT_RRD_RESOURCE_MAPPING(RRD_NO,EMP_NO,EMP_LOCK_DATE,EMP_REL_DATE,IS_HARD_LOCKED)  values (?,?,?,?,?)";
			// +
			// "EMP_BILL_CODE,EMP_PROJECT,EMP_WORK_LOCATION,EMP_DESK_NO,EMP_ASSET_TAG_NO,EMP_SERVICE_TAG_NO,EMP_780_MACHINE,"
			// +
			// "EMP_4GB_RAM,EMP_HARDLOCK_DATE,EMP_IJP_ROLLOFF_DATE,EMP_MOBILE_NO,EMP_DOJ_ACCENTURE,)
			PreparedStatement preparedStatement = getConnection()
					.prepareStatement(resourcemappingTable);
			preparedStatement.setString(1, resDetail.getRrdNumber());
			preparedStatement.setString(2, resDetail.getEmployeeNumber());
			preparedStatement.setString(3, resDetail.getEmployeeLockDate());
			preparedStatement.setString(4, resDetail.getEmployeeReleaseDate());
			preparedStatement.setBoolean(5, resDetail.getIsHardLocked());

			status = preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			log.error("Error inserting employee table :", e);
			return -1;
		}
		return status;
	}

	public int getResourceDetails(ResourceDetails resourceDetails) {
		int status = 0;
		try {
			final String resourceTable = "insert into EDB_ROT_RESOURCE(SKILL,LEVEL)values(?,?)";
			PreparedStatement preparedStatement = getConnection()
					.prepareStatement(resourceTable);
			preparedStatement.setString(1, resourceDetails.getSkill());
			preparedStatement.setString(1, resourceDetails.getLevel());
			status = preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			log.error("Error inserting employee table :", e);
			return -1;
		}
		return status;
	}

	public int getEmployeeDetails(RrdDetails rrdDetails){
		int status  = 0;
		try{
			final String empTable = "insert into EDB_ROT_RRD_DTLS(PROJECT)values(?)";
			PreparedStatement preparedStatement = getConnection()
					.prepareStatement(empTable);
			preparedStatement.setString(1,rrdDetails.getProject());
			status = preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			log.error("Error inserting employee table :", e);
			return -1;
		}
		return status;
		}
	

	public List<RrdResourceMappingDetails> getAllSearchDetails() {
		List<RrdResourceMappingDetails> empList = new ArrayList<RrdResourceMappingDetails>();

		try {
			// Employee table
			final StringBuffer mappingTable = new StringBuffer();
			mappingTable
					.append("SELECT mapping.RRD_NO,mapping.EMP_NO,mapping.EMP_LOCK_DATE,mapping.EMP_REL_DATE,mapping.IS_HARD_LOCKED,resource.SKILL,resource.LEVEL,rrddtls.PROJECT from EDB_ROT_RRD_RESOURCE_MAPPING mapping,EDB_ROT_RESOURCE resource,EDB_ROT_RRD_DTLS rrddtls WHERE mapping.EMP_NO = resource.EMP_NO and mapping.RRD_NO = rrddtls.RRD_NO " );
			PreparedStatement preparedStatement = getConnection()
					.prepareStatement(mappingTable.toString());
			ResultSet r1 = preparedStatement.executeQuery();
			String isHLed = "";
			while (r1.next()) {
				RrdResourceMappingDetails resDetails = new RrdResourceMappingDetails();
				resDetails.setRrdNumber(r1.getString("RRD_NO"));
				resDetails.setEmployeeNumber(r1.getString("EMP_NO"));
				resDetails.setEmployeeLockDate(r1.getString("EMP_LOCK_DATE"));
				resDetails.setEmployeeReleaseDate(r1.getString("EMP_REL_DATE"));
				isHLed = (r1.getString("IS_HARD_LOCKED"));
				if (isHLed.equalsIgnoreCase("TRUE"))
					resDetails.setHardLocked(true);
				else if (isHLed.equalsIgnoreCase("FALSE"))
					resDetails.setHardLocked(false);
				
				ResourceDetails resourceDetails = new ResourceDetails();
				List<ResourceDetails> resList = new ArrayList<ResourceDetails>();
				resourceDetails.setSkill(r1.getString("SKILL"));
				resourceDetails.setLevel(r1.getString("LEVEL"));
				resList.add(resourceDetails);
				resDetails.setResourceDetails(resList);
				
				
				RrdDetails rrdDetails = new RrdDetails();
				List<RrdDetails> rrdDetailsList = new ArrayList<RrdDetails>();
				rrdDetails.setProject(r1.getString("PROJECT"));
				rrdDetailsList.add(rrdDetails);
				resDetails.setRrdDetails(rrdDetailsList);
			
				empList.add(resDetails);

			}
			preparedStatement.close();
		} catch (Exception e) {
			log.error("Error retreiving employee table :", e);
			return null;
		}
		return empList;
	}

}
