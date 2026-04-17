package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import jakarta.xml.bind.annotation.XmlElement;



/*@XmlType(propOrder = { "id", "keys" })*/
public class Route {

	private String id;

	private Keys keys;

	public String getId() {
		return id;
	}
	//@XmlElement(name = "ID",namespace = "http://group.vodafone.com/contract/vho/header/v1")
	@XmlElement(name = "ns2:ID")
	public void setId(String id) {
		this.id = id;
	}

	public Keys getKeys() {
		return keys;
	}
	//@XmlElement(name = "Keys",namespace = "http://group.vodafone.com/contract/vho/header/v1")
	@XmlElement(name = "ns2:Keys")
	public void setKeys(Keys keys) {
		this.keys = keys;
	}

	@Override
	public String toString() {
		return "Route [id=" + id + ", keys=" + keys + "]";
	}


}
