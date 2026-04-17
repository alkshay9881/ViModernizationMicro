package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@XmlRootElement(name = "IDs")

public class IDs {
	

	private List<ID> idList;

	public List<ID> getIdList() {
		return idList;
	}
	//@XmlElement(name = "ID", namespace = "//group.vodafone.com/schema/common/v1")
	@XmlElement(name = "ns4:ID")
	public void setIdList(List<ID> idList) {
		this.idList = idList;
	}

	@Override
	public String toString() {
		return "IDs [idList=" + idList + "]";
	}
	
	
}
