package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(name = "ns6:READ_WRITE_REQ_GBO")
//@XmlRootElement(name = "READ_WRITE_REQ_GBO", namespace = "http://group.vodafone.com/schema/service1/event/v1")
@XmlType(propOrder = {"cmn","xsi","cct","wsa","ws_bf","vfo","extvbo","ccts","vbm","vbo","vc","tns", "tns1","header", "notifyEventVBMRequest" })
public class AsyncReadWriteReqGBO {

	private String cmn;

	private String xsi;

	private String cct;

	private String wsa;

	private String ws_bf;

	private String vfo;

	private String extvbo;

	private String ccts;

	private String vbm;

	private String vbo;
	
	private String vc;
	
	private String tns;
	
	private String tns1;
	
	private Header header;

	private NotifyEventVBMRequest notifyEventVBMRequest;

	@XmlAttribute(name = "xmlns:bf")
	public void setCmn(String cmn) {
		this.cmn = cmn;
	}

	public String getXsi() {
		return xsi;
	}

	@XmlAttribute(name = "xmlns:flt")
	public void setXsi(String xsi) {
		this.xsi = xsi;
	}

	public String getCct() {
		return cct;
	}

	@XmlAttribute(name = "xmlns:hed")
	public void setCct(String cct) {
		this.cct = cct;
	}

	public String getWsa() {
		return wsa;
	}

	@XmlAttribute(name = "xmlns:tns1")
	public void setWsa(String wsa) {
		this.wsa = wsa;
	}

	public String getWs_bf() {
		return ws_bf;
	}
	
	@XmlAttribute(name = "xmlns:vbm")
	public void setWs_bf(String ws_bf) {
		this.ws_bf = ws_bf;
	}

	public String getVfo() {
		return vfo;
	}

	@XmlAttribute(name = "xmlns:vbo")
	public void setVfo(String vfo) {
		this.vfo = vfo;
	}

	public String getExtvbo() {
		return extvbo;
	}

	@XmlAttribute(name = "xmlns:vc")
	public void setExtvbo(String extvbo) {
		this.extvbo = extvbo;
	}

	public String getCcts() {
		return ccts;
	}

	@XmlAttribute(name = "xmlns:xsd")
	public void setCcts(String ccts) {
		this.ccts = ccts;
	}

	public String getVbm() {
		return vbm;
	}

	@XmlAttribute(name = "xmlns:xsi")
	public void setVbm(String vbm) {
		this.vbm = vbm;
	}

	public String getVbo() {
		return vbo;
	}

	@XmlAttribute(name = "xmlns:ns6")
	public void setVbo(String vbo) {
		this.vbo = vbo;
	}

	public String getVc() {
		return vc;
	}

	@XmlAttribute(name = "xmlns:ns5")
	public void setVc(String vc) {
		this.vc = vc;
	}

	public String getTns() {
		return tns;
	}

	@XmlAttribute(name = "xmlns:ns2")
	public void setTns(String tns) {
		this.tns = tns;
	}

	public String getTns1() {
		return tns1;
	}

	@XmlAttribute(name = "xmlns:ns4")
	public void setTns1(String tns1) {
		this.tns1 = tns1;
	}


	public Header getHeader() {
		return header;
	}

	@XmlElement(name = "Header")
	public void setHeader(Header header) {
		this.header = header;
	}

	public NotifyEventVBMRequest getNotifyEventVBMRequest() {
		return notifyEventVBMRequest;
	}

	@XmlElement(name = "NotifyEventVBMRequest")
	public void setNotifyEventVBMRequest(NotifyEventVBMRequest notifyEventVBMRequest) {
		this.notifyEventVBMRequest = notifyEventVBMRequest;
	}

	public String getCmn() {
		return cmn;
	}


	
}