package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import jakarta.xml.bind.annotation.XmlElement;

public class Keys {

	private String key;

	public String getKey() {
		return key;
	}

	@XmlElement(name = "hed:Key")
	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "Keys [key=" + key + "]";
	}

	
}
