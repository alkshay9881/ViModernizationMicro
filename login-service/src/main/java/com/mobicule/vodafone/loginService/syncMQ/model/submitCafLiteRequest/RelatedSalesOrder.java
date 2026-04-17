package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "name","categories"})
public class RelatedSalesOrder {
	
	
	private String name;
	private Categories1 categories;

	public String getName() {
		return name;
	}

	@XmlElement(name = "cmn:Name")
	public void setName(String name) {
		this.name = name;
	}

	public Categories1 getCategories() {
		return categories;
	}

	@XmlElement(name = "cmn:Categories")
	public void setCategories(Categories1 categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "RelatedSalesOrders [name=" + name + ", categories=" + categories + "]";
	}

}
