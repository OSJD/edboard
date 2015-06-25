package com.acc.tools.ed.integration.dto;

import java.io.Serializable;

public class Capability implements Serializable {

	private static final long serialVersionUID = 1L;
	private String capabilityName;
	private String capabilitySpecialty;

	public String getCapabilityName() {
		return capabilityName;
	}

	public void setCapabilityName(String capabilityName) {
		this.capabilityName = capabilityName;
	}

	public String getCapabilitySpecialty() {
		return capabilitySpecialty;
	}

	public void setCapabilitySpecialty(String capabilitySpecialty) {
		this.capabilitySpecialty = capabilitySpecialty;
	}

}
