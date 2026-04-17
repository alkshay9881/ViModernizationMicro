package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;

public class RelatedSalesOrders {

	private List<RelatedSalesOrder> relatedSalesOrder;

	public List<RelatedSalesOrder> getRelatedSalesOrder() {
		return relatedSalesOrder;
	}

	@XmlElement(name = "v2:RelatedSalesOrder")
	public void setRelatedSalesOrder(List<RelatedSalesOrder> relatedSalesOrder) {
		this.relatedSalesOrder = relatedSalesOrder;
	}

	@Override
	public String toString() {
		return "RelatedSalesOrders [relatedSalesOrder=" + relatedSalesOrder + "]";
	}


}
