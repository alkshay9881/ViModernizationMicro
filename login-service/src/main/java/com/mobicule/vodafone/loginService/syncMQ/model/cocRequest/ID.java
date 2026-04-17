package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@XmlRootElement(name = "ID")
public class ID {

	private String schemeName;
	
	private String value;
	
	public String getSchemeName() {
		return schemeName;
	}
	
	@XmlAttribute(name = "schemeName")
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	
	public String getValue() {
		return value;
	}
	
	@XmlValue
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "ID [schemeName=" + schemeName + ", value=" + value + "]";
	}
	
}
