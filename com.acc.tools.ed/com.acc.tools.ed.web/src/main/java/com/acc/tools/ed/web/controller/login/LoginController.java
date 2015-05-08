package com.acc.tools.ed.web.controller.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.dto.ProjectForm;
import com.acc.tools.ed.integration.dto.TaskForm;
import com.acc.tools.ed.integration.service.ILoginService;
import com.acc.tools.ed.integration.service.ProjectWorkService;
import com.acc.tools.ed.web.controller.common.AbstractEdbBaseController;
/**
 * 
 * @author nikhil.jagtiani
 *
 */

@Controller
@SessionAttributes({ "edbUser"})
public class LoginController extends AbstractEdbBaseController{
	
	private static final Logger LOG=LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ILoginService iLoginService;
	
	@Autowired
	private ProjectWorkService projectWorkService;
	
	@Value("${build}")
	private String environment;
	
	


	@RequestMapping(value="/start.do", method=RequestMethod.GET)
	public String pageLogin(Model model,HttpServletRequest request){
		
		if(environment.equalsIgnoreCase("DEV")){
			model.addAttribute("edbUser", new EDBUser());
			model.addAttribute("addProjectForm",new ProjectForm());
			model.addAttribute("addTaskForm",new TaskForm());
			model.addAttribute("editProjectForm", new ProjectForm());
		return "/login/index";
		} else {
			return "redirect:/login.do";
		}
			
	}
	
	@RequestMapping(value="/login.do")
	public String handleLogins(
			Model model,
			@RequestParam(required = false) String enterpriseId,
			final RedirectAttributes redirectAttributes
			) throws Exception{
		
		EDBUser user=null;
		String userName=System.getProperty("user.name");
		boolean isAdmin=isAdmin();
		if(environment.equalsIgnoreCase("DEV") && enterpriseId!=null){
			userName=enterpriseId;
			isAdmin=true;
		}
		
		if(isAdmin){
			user=iLoginService.searchUser(userName);
			if(user!=null && user.getEmployeeId()!=null){
				//session attributes
				model.addAttribute("edbUser", user);
				model.addAttribute("addProjectForm",new ProjectForm());
				model.addAttribute("addTaskForm",new TaskForm());
				model.addAttribute("editProjectForm", new ProjectForm());
				List<ProjectForm> projData=projectWorkService.getMyTasks(user.getEmployeeId());
				model.addAttribute("projData",projData);
				LOG.debug("Login - Adding user to session - User Id:[{}] Role:[{}]",user.getEmployeeId(),user.getRole());
				return "/projectmanagement/index";
			}else {
				redirectAttributes.addFlashAttribute("status", "User Not Registered!Please reach out to your supervisor!");
				//model.addAttribute("status", "User Not Registered!Please reach out to your supervisor!");
				return "redirect:/loginError.do";
			}
		} else {
			redirectAttributes.addFlashAttribute("status", "Please use EDB application with Windows Admin access!");
			//model.addAttribute("status", "Please use EDB application with Windows Admin access!");
			return "redirect:/loginError.do";
		}
	}
	
	@RequestMapping(value="/loginError.do")
	public String loginError(@ModelAttribute("status") final String status,Model model){
		model.addAttribute("status", status);
		return "/login/index";
	}
	
	@SuppressWarnings("restriction")
	public static boolean isAdmin() {
	    String groups[] = (new com.sun.security.auth.module.NTSystem()).getGroupIDs();
	    for (String group : groups) {
	        if (group.equals("S-1-5-32-544"))
	            return true;
	    }
	    return false;
	}

}
