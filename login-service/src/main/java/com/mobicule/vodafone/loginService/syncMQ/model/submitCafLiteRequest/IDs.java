package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;

public class IDs {
    List<ID> ID;

	public List<ID> getID() {
		return ID;
	}
	@XmlElement(name = "cmn:ID")
	public void setID(List<ID> iDs) {
		this.ID = iDs;
	}
	@Override
	public String toString() {
		return "IDs [IDs=" + ID + "]";
	} 
		
}
