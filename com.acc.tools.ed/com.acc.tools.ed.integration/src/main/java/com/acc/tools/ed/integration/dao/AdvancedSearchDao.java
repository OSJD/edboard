package com.acc.tools.ed.integration.dao;
import java.util.List;

import com.acc.tools.ed.integration.dto.ResourceDetails;
import com.acc.tools.ed.integration.dto.RrdResourceMappingDetails;

public interface AdvancedSearchDao {
	public List<RrdResourceMappingDetails> getAllSearchDetails();
	public int addResourceMappingDetails(RrdResourceMappingDetails resDetail);
	public int getResourceDetails(ResourceDetails resourceDetails);
	
}