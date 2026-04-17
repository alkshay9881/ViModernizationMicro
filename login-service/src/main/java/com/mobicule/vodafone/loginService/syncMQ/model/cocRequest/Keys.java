package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(name = "Keys")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Keys {
	private String key;

	public String getKey() {
		return key;
	}

	//@XmlElement(name = "Key", namespace = "http://group.vodafone.com/contract/vho/header/v1")
	@XmlElement(name = "ns2:Key")
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "Keys [key=" + key + "]";
	}
	
	

}
