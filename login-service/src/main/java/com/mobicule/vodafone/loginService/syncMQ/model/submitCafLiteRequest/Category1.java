package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

public class Category1 {
	private String listHierarchyID;
	private String listName;
	private String value;

	public String getListHierarchyID() {
		return listHierarchyID;
	}

	@XmlAttribute(name = "listHierarchyID")
	public void setListHierarchyID(String listHierarchyID) {
		this.listHierarchyID = listHierarchyID;
	}

	public String getListName() {
		return listName;
	}
	
	@XmlAttribute(name = "listName")
	public void setListName(String listName) {
		this.listName = listName;
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
		return "Category1 [listHierarchyID=" + listHierarchyID + ", listName=" + listName + ", value=" + value + "]";
	}

	
	
	
}
