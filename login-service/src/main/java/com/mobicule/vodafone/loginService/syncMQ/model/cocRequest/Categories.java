package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;

public class Categories {

	private List<Category> category;

	public List<Category> getCategory() {
		return category;
	}

	//@XmlElement(name = "Category",namespace = "http://group.vodafone.com/schema/common/v1")
	@XmlElement(name = "ns4:Category")
	public void setCategory(List<Category> category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Categories [category=" + category + "]";
	}
	
	
}