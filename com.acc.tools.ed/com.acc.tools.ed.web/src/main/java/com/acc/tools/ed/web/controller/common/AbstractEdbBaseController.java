package com.acc.tools.ed.web.controller.common;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.acc.tools.ed.integration.dto.EditProjectForm;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.service.ProjectManagementService;
import com.acc.tools.ed.integration.service.ProjectWorkService;
import com.acc.tools.ed.web.editor.DateTimeEditor;

public abstract class AbstractEdbBaseController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(DateTime.class, new DateTimeEditor("MM/dd/yyyy", true));
	}
	
	@Autowired
	private ProjectManagementService projectManagementService;
	
	@Autowired
	private ProjectWorkService projectWorkService;
	
	/**
	 * This method will be used to display the programs in the JSP
	 * @return
	 */

	public List<ReferenceData> getProgramList(){
		final List<ReferenceData> programId= projectManagementService.getProgramList();
		return programId;
	}	
	
	/**
	 * This method will be used to display the role's in the JSP
	 * @return
	 */

	@ModelAttribute("projectList")
	public List<ReferenceData> getProjectList()
	{
		final List<ReferenceData> projectIds=projectManagementService.getAllProjectIds();
		return projectIds;
	}

	public List<ReferenceData> getProjectLeadList()
	{
		return projectManagementService.getPrjLeadList();
	}
	
	/**
	 * This method will be used to display the role's in the JSP
	 * @return
	 */

	@ModelAttribute("roleList")
	public List<ReferenceData> getRoleList()
	{
		List<ReferenceData> roleList=new ArrayList<ReferenceData>();
		ReferenceData developer=new ReferenceData();
		developer.setId("dev");
		developer.setLabel("Developer");
		roleList.add(developer);
		
		ReferenceData supervisor=new ReferenceData();
		supervisor.setId("sup");
		supervisor.setLabel("Supervisor");
		roleList.add(supervisor);
		
		return roleList;
	}
	
	
	/**
	 * This method will be used to display the designation's in the JSP
	 * @return
	 */

	@ModelAttribute("designationList")
	public List<ReferenceData> getDesignationList()
	{
		List<ReferenceData> roleList=new ArrayList<ReferenceData>();
		ReferenceData ase=new ReferenceData();
		ase.setId("ase");
		ase.setLabel("ASE");
		roleList.add(ase);
		
		ReferenceData se=new ReferenceData();
		se.setId("se");
		se.setLabel("SE");
		roleList.add(se);
		
		ReferenceData sse=new ReferenceData();
		sse.setId("sse");
		sse.setLabel("SSE");
		roleList.add(sse);
		
		ReferenceData tl=new ReferenceData();
		tl.setId("tl");
		tl.setLabel("TL");
		roleList.add(tl);
		
		ReferenceData am=new ReferenceData();
		am.setId("am");
		am.setLabel("AM");
		roleList.add(am);
		
		ReferenceData m=new ReferenceData();
		m.setId("m");
		m.setLabel("M");
		roleList.add(m);
		
		return roleList;
	}
	
	/**
	 * This method will be used to display the phases in the JSP
	 * @return
	 */

	@ModelAttribute("phaseList")
	public List<ReferenceData> getPhaseList()
	{
		List<ReferenceData> phaseList=new ArrayList<ReferenceData>();
		
		ReferenceData analysis=new ReferenceData();
		analysis.setId("1");
		analysis.setLabel("Analysis");
		phaseList.add(analysis);
		ReferenceData design=new ReferenceData();
		design.setId("2");
		design.setLabel("Design");
		phaseList.add(design);
		ReferenceData build=new ReferenceData();
		build.setId("3");
		build.setLabel("Build");
		phaseList.add(build);
		ReferenceData test=new ReferenceData();
		test.setId("4");
		test.setLabel("Test");
		phaseList.add(test);
		ReferenceData support=new ReferenceData();
		support.setId("5");
		support.setLabel("Support");
		phaseList.add(support);
		
		return phaseList;
	}
	
	/**
	 * This method will be used to display the resources in the JSP
	 * @return
	 */

	public List<ReferenceData> getResourceList()
	{
		return projectManagementService.getResourceList();
	}

	public ProjectManagementService getProjectManagementService() {
		return this.projectManagementService;
	}
	
	public ProjectWorkService getProjectWorkService() {
		return this.projectWorkService;
	}
	
	public List<EditProjectForm> editProject(int ProjectId)
	{
		return projectManagementService.editProject(ProjectId);
	}
	
	public int checkProjName(String projectName ,int progId)
	{
		return projectManagementService.checkProjName(projectName, progId);
	}
	
}
