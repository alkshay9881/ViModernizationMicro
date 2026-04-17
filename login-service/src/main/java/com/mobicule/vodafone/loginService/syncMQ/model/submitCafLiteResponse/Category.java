package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteResponse;

import jakarta.xml.bind.annotation.XmlElement;

public class Category {

	private String key;
	
	private String value;

	public String getKey() {
		return key;
	}

	@XmlElement(name = "key")
	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	@XmlElement(name = "value")
	public void setValue(String value) {
		this.value = value;
	}


	@Override
	public String toString() {
		return "Category [key=" + key + ", value=" + value + "]";
	}


	
}
