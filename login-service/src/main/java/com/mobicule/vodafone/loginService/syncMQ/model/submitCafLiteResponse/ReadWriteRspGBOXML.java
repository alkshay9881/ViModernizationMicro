package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteResponse;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "READ_WRITE_RESP_GBO",namespace="http://group.vodafone.com/schema/order/sales-order/v2")
public class ReadWriteRspGBOXML {

	private Header header;

	private CreateSalesOrderVBMResp createSalesOrderVBMResp;

	public Header getHeader() {
		return header;
	}

	@XmlElement(name = "Header")
	public void setHeader(Header header) {
		this.header = header;
	}

	public CreateSalesOrderVBMResp getCreateSalesOrderVBMResp() {
		return createSalesOrderVBMResp;
	}

	@XmlElement(name = "CreateSalesOrderVBMResponse")
	public void setCreateSalesOrderVBMResp(CreateSalesOrderVBMResp createSalesOrderVBMResp) {
		this.createSalesOrderVBMResp = createSalesOrderVBMResp;
	}

	@Override
	public String toString() {
		return "ReadWriteRspGBOXML [header=" + header + ", createSalesOrderVBMResp=" + createSalesOrderVBMResp + "]";
	}

}
