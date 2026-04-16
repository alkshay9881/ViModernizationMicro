package com.mobicule.vodafone.loginService.syncMQ.model.response;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;

public class IDs {
    List<ID> ID;

	public List<ID> getID() {
		return ID;
	}
	@XmlElement(name = "ID",namespace = "http://group.vodafone.com/schema/common/v1")
	public void setID(List<ID> iDs) {
		this.ID = iDs;
	}
	@Override
	public String toString() {
		return "IDs [IDs=" + ID + "]";
	} 
		
}
