package com.mobicule.vodafone.loginService.syncMQ.model.response;

import jakarta.xml.bind.annotation.XmlElement;

public class CreateSalesOrderVBMResp {
	private SalesOrderVBO salesOrderVBO;

	public SalesOrderVBO getSalesOrderVBO() {
		return salesOrderVBO;
	}

	@XmlElement(name = "SalesOrderVBO",namespace = "http://group.vodafone.com/schema/vbm/order/sales-order/v2")
	public void setSalesOrderVBO(SalesOrderVBO salesOrderVBO) {
		this.salesOrderVBO = salesOrderVBO;
	}

	@Override
	public String toString() {
		return "GetSalesOrderListVBMResp [salesOrderVBO=" + salesOrderVBO + "]";
	}

}
