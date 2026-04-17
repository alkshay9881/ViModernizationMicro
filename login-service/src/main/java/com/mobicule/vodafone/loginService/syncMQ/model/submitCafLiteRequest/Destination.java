package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import jakarta.xml.bind.annotation.XmlElement;

public class Destination {

	private String System;

	public String getSystem() {
		return System;
	}

	@XmlElement(name = "hed:System")
	public void setSystem(String system) {
		System = system;
	}
	

	@Override
	public String toString() {
		return "Destination [System=" + System + "]";
	}


	
}
