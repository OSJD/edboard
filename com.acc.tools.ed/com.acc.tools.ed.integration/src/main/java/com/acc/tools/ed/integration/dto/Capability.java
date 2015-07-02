package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class Capability implements Serializable {

	private static final long serialVersionUID = 1L;
	private String capabilityName;
	private String existingCapability;
	public String getCapabilityName() {
		return capabilityName;
	}

	public void setCapabilityName(String capabilityName) {
		this.capabilityName = capabilityName;
	}

	public String getExistingCapability() {
		return existingCapability;
	}

	public void setExistingCapability(String existingCapability) {
		this.existingCapability = existingCapability;
	}
	

}
