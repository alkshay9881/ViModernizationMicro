package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import jakarta.xml.bind.annotation.XmlElement;

//@XmlRootElement(name = "v2:CreateSalesOrderVBMRequest")
//@XmlType(propOrder = {"SalesOrderVBO" })
public class CreateSalesOrderVBMRequest {
//	private SoapHeaderReqResponse headerReqResponse;
	private SalesOrderVBO SalesOrderVBO;

	public SalesOrderVBO getSalesOrderVBO() {
		return SalesOrderVBO;
	}

	@XmlElement(name = "vbm:SalesOrderVBO")
	public void setSalesOrderVBO(SalesOrderVBO salesOrderVBO) {
		SalesOrderVBO = salesOrderVBO;
	}


	@Override
	public String toString() {
		return "CreateSalesOrderVBMRequest [SalesOrderVBO=" + SalesOrderVBO + "]";
	}

}
