package com.mobicule.vodafone.loginService.syncMQ.model.response;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Source")
public class Source {

	private String division;
	
	private String System;


	public String getDivision() {
		return division;
	}

	@XmlElement(name = "Division",namespace ="http://group.vodafone.com/contract/vho/header/v1")
	public void setDivision(String division) {
		this.division = division;
	}

	public String getSystem() {
		return System;
	}

	@XmlElement(name = "System",namespace ="http://group.vodafone.com/contract/vho/header/v1")
	public void setSystem(String system) {
		System = system;
	}
	
	@Override
	public String toString() {
		return "Source [division=" + division + ", System=" + System + "]";
	}

	
}
