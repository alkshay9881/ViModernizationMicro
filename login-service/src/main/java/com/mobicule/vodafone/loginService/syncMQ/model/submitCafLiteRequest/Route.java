package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Route")
public class Route {

	private String Id;
	
	private Keys keys;

	public String getId() {
		return Id;
	}

	@XmlElement(name = "hed:ID")
	public void setId(String id) {
		Id = id;
	}

	public Keys getKeys() {
		return keys;
	}

	@XmlElement(name = "hed:Keys")
	public void setKeys(Keys keys) {
		this.keys = keys;
	}
	
	@Override
	public String toString() {
		return "Route [Id=" + Id + "]";
	}

}
