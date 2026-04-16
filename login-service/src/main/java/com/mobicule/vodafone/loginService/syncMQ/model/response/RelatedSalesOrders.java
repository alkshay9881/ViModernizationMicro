package com.mobicule.vodafone.loginService.syncMQ.model.response;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;

public class RelatedSalesOrders {

	private List<RelatedSalesOrder> relatedSalesOrder;

	public List<RelatedSalesOrder> getRelatedSalesOrder() {
		return relatedSalesOrder;
	}

	@XmlElement(name = "v2:relatedSalesOrder")
	public void setRelatedSalesOrder(List<RelatedSalesOrder> relatedSalesOrder) {
		this.relatedSalesOrder = relatedSalesOrder;
	}

	@Override
	public String toString() {
		return "RelatedSalesOrders [relatedSalesOrder=" + relatedSalesOrder + "]";
	}


}
