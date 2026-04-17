package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

public class Category {
	private String listName;
	private String value;

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
		return "Category [listName=" + listName + "]";
	}

}
