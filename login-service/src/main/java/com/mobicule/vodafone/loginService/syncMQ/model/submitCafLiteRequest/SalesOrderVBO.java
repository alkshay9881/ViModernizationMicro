package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

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

	@XmlElement(name = "cmn:IDs")
	public void setiDs(IDs iDs) {
		this.iDs = iDs;
	}

	public Categories getCategories() {
		return categories;
	}

	@XmlElement(name = "cmn:Categories")
	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	public RelatedSalesOrders getRelatedSalesOrders() {
		return relatedSalesOrders;
	}

	@XmlElement(name = "v2:RelatedSalesOrders")
	public void setRelatedSalesOrders(RelatedSalesOrders relatedSalesOrders) {
		this.relatedSalesOrders = relatedSalesOrders;
	}

	@Override
	public String toString() {
		return "SalesOrderVBO [iDs=" + iDs + ", categories=" + categories + ", relatedSalesOrders=" + relatedSalesOrders
				+ "]";
	}

}
