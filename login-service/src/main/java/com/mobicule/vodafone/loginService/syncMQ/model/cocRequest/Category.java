package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

public class Category {

	private String listHierarchyID;
	
	private String value;

	public String getListHierarchyID() {
		return listHierarchyID;
	}

	@XmlAttribute
	public void setListHierarchyID(String listHierarchyID) {
		this.listHierarchyID = listHierarchyID;
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
		return "Category [listHierarchyID=" + listHierarchyID + ", value=" + value + "]";
	}

}