package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteResponse;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


@XmlType(propOrder = { "iDs", "categories","relatedSalesOrders" })
public class SalesOrderVBO {

	private IDs iDs;
	private Categories categories;
	private RelatedSalesOrders relatedSalesOrders;

	public IDs getiDs() {
		return iDs;
	}

	@XmlElement(name = "IDs",namespace = "http://group.vodafone.com/schema/common/v1")
	public void setiDs(IDs iDs) {
		this.iDs = iDs;
	}

	public Categories getCategories() {
		return categories;
	}

	@XmlElement(name = "Categories",namespace = "http://group.vodafone.com/schema/common/v1")
	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	public RelatedSalesOrders getRelatedSalesOrders() {
		return relatedSalesOrders;
	}

	@XmlElement(name = "vbo:RelatedSalesOrders")
	public void setRelatedSalesOrders(RelatedSalesOrders relatedSalesOrders) {
		this.relatedSalesOrders = relatedSalesOrders;
	}

	@Override
	public String toString() {
		return "SalesOrderVBO [iDs=" + iDs + ", categories=" + categories + ", relatedSalesOrders=" + relatedSalesOrders
				+ "]";
	}

}
