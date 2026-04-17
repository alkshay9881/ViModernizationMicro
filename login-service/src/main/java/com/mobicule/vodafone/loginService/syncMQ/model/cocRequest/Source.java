package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@XmlRootElement(name = "Source")
public class Source {

	private String division;
	
	private String system;
	
	public String getDivision() {
		return division;
	}
	
	//@XmlElement(name = "Division",namespace = "http://group.vodafone.com/contract/vho/header/v1")
	@XmlElement(name = "ns2:Division")
	public void setDivision(String division) {
		this.division = division;
	}
	
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
		return "Source [division=" + division + ", system=" + system + "]";
	}
	
}
