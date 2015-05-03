package com.acc.tools.ed.web.controller.management;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.dto.ExcelData;
import com.acc.tools.ed.integration.dto.MasterEmployeeDetails;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.ReleaseForm;
import com.acc.tools.ed.integration.service.ILoginService;
import com.acc.tools.ed.integration.service.ProjectManagementService;
import com.acc.tools.ed.integration.util.EDBConstants;
import com.acc.tools.ed.integration.util.ExcelFileReadUtil;
import com.acc.tools.ed.web.controller.common.AbstractEdbBaseController;

@Controller
public class FileUploadController extends AbstractEdbBaseController{
	
	private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);
	
	@Autowired
	ProjectManagementService projectManagementService;
	
	@Autowired
	private ILoginService iLoginService;
     
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(MultipartHttpServletRequest request, 
			HttpServletResponse response,
			Model model) throws IOException, SQLException {                 
	 
	     Iterator<String> itr =  request.getFileNames();
	     MultipartFile mpf = request.getFile(itr.next());
	     ExcelData excelData=ExcelFileReadUtil.convertExcelDataToObject(mpf.getInputStream());
		 if(!validateExcel(excelData)){
			 LOG.debug("Invalid Excel sheet. Please verify");
		 } else {
			 final Collection<MasterEmployeeDetails> employeeDetails=createResourceData(excelData);
			 if(employeeDetails.size()>0){
				MasterEmployeeDetails empDetail=employeeDetails.iterator().next();
				EDBUser user= iLoginService.searchUser(empDetail.getEmployeeEnterpId());
				if(user==null)
					projectManagementService.addEmployees(employeeDetails);
			 }
			 
		 }

		List<MasterEmployeeDetails> empList= projectManagementService.getAllEmployees();

		model.addAttribute("empList", empList);
		model.addAttribute("addProjectForm", new ProjectForm());
		model.addAttribute("addReleaseForm", new ReleaseForm());		
		model.addAttribute("editProjectForm", new ProjectForm());
		model.addAttribute(EDBConstants.DEFAULT_TAB, EDBConstants.RESOURCE_MNG_TAB);
		
		return "/projectmanagement/index";
	}
	   
	private Collection<MasterEmployeeDetails>  createResourceData(ExcelData excelData){
		final Map<String,MasterEmployeeDetails> employeeDetailsMap=new HashMap<String,MasterEmployeeDetails>();
		for(String[] columns:excelData.getRowsData()){
			 final String empActive=columns[5];
			 if(empActive!=null && !empActive.isEmpty() && empActive.equalsIgnoreCase("Yes")){
				 final MasterEmployeeDetails employeeData=new MasterEmployeeDetails();
				 employeeData.setEmployeeActive(true);
				 employeeData.setEmployeeName(columns[0]);
				 employeeData.setEmployeeEnterpId(columns[2]);
				 employeeData.setEmployeeSAPId(columns[3]);
				 employeeData.setEmployeeRRDNo(columns[4]);
				 employeeData.setEmployeeLevel(columns[6]);
				 employeeData.setEmployeeRole(columns[7]);
				 employeeData.setEmployeeBillCode(columns[8]);
				 employeeData.setEmployeeProjectName(columns[10]);
				 employeeData.setEmployeeWorkLocation(columns[12]);
				 employeeData.setEmployeeDeskNo(columns[13]);
				 employeeData.setEmployeeAssetTagNo(columns[14]);
				 employeeData.setEmployeeServiceTagNo(columns[15]);
				 employeeData.setEmployeeMachineCode(columns[16]);
				 employeeData.setEmployeeMachineRAMSize(columns[17]);
				 employeeData.setEmployeeHardLockDate(columns[18]);
				 employeeData.setEmployeeRollOffDate(columns[20]);
				 employeeData.setEmployeePrimaryContactNo(columns[21]);
				 employeeData.setAccentureDOJ(columns[22]);
				 employeeData.setClientLANId(columns[23]);
		/*    		 employeeData.setEmployeeRSATokenNo(columns[25]);
		    		 employeeData.setEmployeeRSAExpiryDate(columns[26]);
		    		 employeeData.setEmployeeSEZId(columns[27]);
		    		 employeeData.setEmployeeSEZIdExpiryDate(columns[28]);*/
				 if(!employeeDetailsMap.containsKey(employeeData.getEmployeeSAPId())){
					 employeeDetailsMap.put(employeeData.getEmployeeSAPId(), employeeData);
				 }
			 } 
		}
		return employeeDetailsMap.values();
	}
	   
	private boolean validateExcel(ExcelData excelData){
		   String[] headers={"Resource Name","Win 7","Enterprise Id","Employee Id","RRD No.","Active Resource (Y\\N)","Level","Role","Bill Code",
			   "2013 \nProject","2014 \nProject","2014\nProject\nStart Date","Work Location","Desk No.","Asset Tag No.","Service Tag No.","780 Machines ( Y/N)","4 GB RAM(Y/N)",
			   		 "Hard Lock Date",	"IJP \nRoll-Off Date","New \nRoll Off","Mobile No.","DOJ Accenture","HCSC LAN Id","Old RSA Token No.",
			   		 "Old RSA Expiry Date"," RSA Token No."," RSA Expiry Date","SEZ ID Emp No","SEZ ID Card Expiry Date",
			   		"HUB Start Date","HUB End Date"};
			for(String header:excelData.getHeader()){
				if(!header.isEmpty() && !headers[excelData.getHeaderIndex(header)].equalsIgnoreCase(header)){
					LOG.debug("NOTMATCH --->Excel Header:[{}] | Code Header:[{}] ",header,headers[excelData.getHeaderIndex(header)]);
					return false;
				} 
			
			}
		  return true;
	}
}
