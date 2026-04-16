package com.mobicule.vodafone.loginService.syncMQ.model.response;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Destination")
public class Destination {

	private String System;

	public String getSystem() {
		return System;
	}

	@XmlElement(name = "System",namespace ="http://group.vodafone.com/contract/vho/header/v1")
	public void setSystem(String system) {
		System = system;
	}
	
	@Override
	public String toString() {
		return "Destination [System=" + System + "]";
	}
}
