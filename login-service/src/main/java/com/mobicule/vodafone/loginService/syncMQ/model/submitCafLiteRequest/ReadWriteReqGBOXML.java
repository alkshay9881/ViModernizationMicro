package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

//@XmlRootElement(name = "READ_WRITE_REQ_IN_GBO")
@XmlRootElement(name = "tns1:READ_WRITE_REQ_GBO")
@XmlType(propOrder = { "header", "createSalesOrderVBMRequest" })
public class ReadWriteReqGBOXML {

//	private String soapenv;

	private String xsi;
	private String cmn;
	private String cct;
	private String v2;
	private String extVbo;
	private String tns1;
	private String vbm;
	private String hed;
	private String flt;
	private String bf;
	private String ccts;
	private String tns;

	private SubmitCafLiteMqHeader header;

	private CreateSalesOrderVBMRequest createSalesOrderVBMRequest;

//	public String getSoapenv() {
//		return soapenv;
//	}
//
//	@XmlAttribute(name = "xmlns:soapenv")
//	public void setSoapenv(String soapenv) {
//		this.soapenv = soapenv;
//	}
	
	@XmlElement(name = "Header")
	public void setHeader(SubmitCafLiteMqHeader header) {
		this.header = header;
	}
	

	public String getTns1() {
		return tns1;
	}

	@XmlAttribute(name = "xmlns:tns1")
	public void setTns1(String tns1) {
		this.tns1 = tns1;
	}

	public String getXsi() {
		return xsi;
	}

	@XmlAttribute(name = "xmlns:xsi")
	public void setXsi(String xsi) {
		this.xsi = xsi;
	}

	public String getVbm() {
		return vbm;
	}

	@XmlAttribute(name = "xmlns:vbm")
	public void setVbm(String vbm) {
		this.vbm = vbm;
	}

	public String getExtVbo() {
		return extVbo;
	}

	@XmlAttribute(name = "xmlns:extvbo")
	public void setExtVbo(String extVbo) {
		this.extVbo = extVbo;
	}

	public String getCmn() {
		return cmn;
	}

	@XmlAttribute(name = "xmlns:cmn")
	public void setCmn(String cmn) {
		this.cmn = cmn;
	}

	public String getHed() {
		return hed;
	}

	@XmlAttribute(name = "xmlns:hed")
	public void setHed(String hed) {
		this.hed = hed;
	}

	public String getFlt() {
		return flt;
	}

	@XmlAttribute(name = "xmlns:flt")
	public void setFlt(String flt) {
		this.flt = flt;
	}

	public String getBf() {
		return bf;
	}

	@XmlAttribute(name = "xmlns:bf")
	public void setBf(String bf) {
		this.bf = bf;
	}

	public String getTns() {
		return tns;
	}

	@XmlAttribute(name = "xmlns:tns")
	public void setTns(String tns) {
		this.tns = tns;
	}

	public String getCct() {
		return cct;
	}

	@XmlAttribute(name = "xmlns:cct")
	public void setCct(String cct) {
		this.cct = cct;
	}
	

	public String getCcts() {
		return ccts;
	}

	@XmlAttribute(name = "xmlns:ccts")
	public void setCcts(String ccts) {
		this.ccts = ccts;
	}


	public String getV2() {
		return v2;
	}

	@XmlAttribute(name = "xmlns:v2")
	public void setV2(String v2) {
		this.v2 = v2;
	}


	public SubmitCafLiteMqHeader getHeader() {
		return header;
	}

	public CreateSalesOrderVBMRequest getCreateSalesOrderVBMRequest() {
		return createSalesOrderVBMRequest;
	}

	@XmlElement(name = "CreateSalesOrderVBMRequest")
	public void setCreateSalesOrderVBMRequest(CreateSalesOrderVBMRequest createSalesOrderVBMRequest) {
		this.createSalesOrderVBMRequest = createSalesOrderVBMRequest;
	}

	
	
}
