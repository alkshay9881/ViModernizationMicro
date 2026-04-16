package com.mobicule.vodafone.loginService.syncMQ.model.response;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;


public class Categories {


	private List<Category> categoryList;

	public List<Category> getCategoryList() {
		return categoryList;
	}

	@XmlElement(name="Category",namespace = "http://group.vodafone.com/schema/common/v1")
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
	@Override
	public String toString() {
		return "Categories [categoryList=" + categoryList + "]";
	}


	
}
