package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@XmlRootElement(name = "Destination")

public class Destination {

	private String system;

	public String getSystem() {
		return system;
	}

	//@XmlElement(name = "System",namespace = "http://group.vodafone.com/contract/vho/header/v1")
	@XmlElement(name = "ns2:System")
	public void setSystem(String system) {
		this.system = system;
	}

	@Override
	public String toString() {
		return "Destination [system=" + system + "]";
	}
	
}