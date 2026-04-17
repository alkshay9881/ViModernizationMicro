package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;

public class Categories {

	private List<Category> Category;

	public List<Category> getCategory() {
		return Category;
	}

	@XmlElement(name = "cmn:Category")
	public void setCategory(List<Category> category) {
		Category = category;
	}

	@Override
	public String toString() {
		return "Categories [Category=" + Category + "]";
	}

	/*
	 * public Category getCategory() { return Category; }
	 * 
	 * @XmlElement(name = "cmn:Category") public void setCategory(Category category)
	 * { Category = category; }
	 * 
	 * 
	 * @Override public String toString() { return "Categories [Category=" +
	 * Category + "]"; }
	 */

}
