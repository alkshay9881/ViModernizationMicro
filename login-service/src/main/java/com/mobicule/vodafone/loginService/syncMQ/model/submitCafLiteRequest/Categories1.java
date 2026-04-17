package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;

public class Categories1 {
	
	private List<Category1> Category;

	public List<Category1> getCategory() {
		return Category;
	}

	@XmlElement(name = "cmn:Category")
	public void setCategory(List<Category1> category) {
		Category = category;
	}


	@Override
	public String toString() {
		return "Categories [Category=" + Category + "]";
	}
}
