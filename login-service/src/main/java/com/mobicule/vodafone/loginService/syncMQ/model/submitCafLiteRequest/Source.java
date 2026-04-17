package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import jakarta.xml.bind.annotation.XmlElement;

public class Source {

	private String Division;
	
	private String System;

	public String getDivision() {
		return Division;
	}

	@XmlElement(name = "hed:Division")
	public void setDivision(String division) {
		Division = division;
	}

	public String getSystem() {
		return System;
	}

	@XmlElement(name = "hed:System")
	public void setSystem(String system) {
		System = system;
	}

	@Override
	public String toString() {
		return "Source [Division=" + Division + ", System=" + System + "]";
	}
	
	
}
