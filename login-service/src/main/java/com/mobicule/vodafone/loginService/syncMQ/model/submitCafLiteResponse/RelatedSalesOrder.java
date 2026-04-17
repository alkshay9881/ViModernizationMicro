package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteResponse;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "name","categories"})
public class RelatedSalesOrder {
	
	
	private String name;
	private Categories categories;

	public String getName() {
		return name;
	}

	@XmlElement(name = "cmn:Name")
	public void setName(String name) {
		this.name = name;
	}

	public Categories getCategories() {
		return categories;
	}

	@XmlElement(name = "cmn:categories")
	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "RelatedSalesOrders [name=" + name + ", categories=" + categories + "]";
	}

}
