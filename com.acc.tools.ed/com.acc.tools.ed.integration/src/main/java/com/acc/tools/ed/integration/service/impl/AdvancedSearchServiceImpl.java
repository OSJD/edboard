package com.acc.tools.ed.integration.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acc.tools.ed.integration.dao.AdvancedSearchDao;
import com.acc.tools.ed.integration.dto.RrdResourceMappingDetails;
import com.acc.tools.ed.integration.service.AdvancedSearchService;


@Service("advancedSearchService")
public class AdvancedSearchServiceImpl implements AdvancedSearchService{
	
	@Autowired
	private AdvancedSearchDao advancedSearchDao;
	
	public List<RrdResourceMappingDetails> getAllSearchDetails(){
		return advancedSearchDao.getAllSearchDetails();
	}
}
