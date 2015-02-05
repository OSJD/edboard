package com.acc.tools.ed.web.controller.resourceonboarding;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.integration.dto.ResourceDetails;
import com.acc.tools.ed.integration.dto.RrdDetails;
import com.acc.tools.ed.integration.dto.RrdResourceMappingDetails;
import com.acc.tools.ed.integration.service.AdvancedSearchService;
import com.acc.tools.ed.web.controller.common.AbstractEdbBaseController;

@Controller
@SessionAttributes({ "edbUser" })
public class AdvancedSearchController extends AbstractEdbBaseController {

	private static final Logger LOG = LoggerFactory
			.getLogger(AdvancedSearchController.class);
	
	@Autowired
	AdvancedSearchService advancedSearchService;

	@RequestMapping(value = "/advancedSearch.do")
	public String addEmpDetails(Model model) {
		List<RrdResourceMappingDetails> rrdList = advancedSearchService.getAllSearchDetails();
		
		List<ResourceDetails> temp = new ArrayList<ResourceDetails>();
		for(RrdResourceMappingDetails  rrd :  rrdList){
			temp = rrd.getResourceDetails();
			for (ResourceDetails resourceDetails : temp) {
				System.out.println("Skill   " + resourceDetails.getSkill());
			}
		}
		
		List<RrdDetails> tmp = new ArrayList<RrdDetails>();
		for(RrdResourceMappingDetails rrd : rrdList){
			tmp = rrd.getRrdDetails();
			for(RrdDetails rrdDetails : tmp) {
				System.out.println("Skill   " + rrdDetails.getProject());
			}
		}
		model.addAttribute("rrdList", rrdList);
		LOG.debug("*******************resourceonboarding");
		return "/resourcemanagement/advancedSearch";
	}
	
}
