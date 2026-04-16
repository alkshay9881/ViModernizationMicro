package com.mobicule.vodafone.loginService.syncMQ.model.response;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ResultStatus")
public class ResultStatus {

	private String errorCode;
	
	private String Desciption;
	
	private String Descriptions;
	
	private String Category;

	public String getErrorCode() {
		return errorCode;
	}

	@XmlElement(name = "ErrorCode",namespace="http://docs.oasis-open.org/wsrf/bf-2")
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDesciption() {
		return Desciption;
	}

	@XmlElement(name = "Description",namespace="http://docs.oasis-open.org/wsrf/bf-2")
	public void setDesciption(String desciption) {
		Desciption = desciption;
	}

	public String getDescriptions() {
		return Descriptions;
	}

	@XmlElement(name = "Description",namespace="http://docs.oasis-open.org/wsrf/bf-2")
	public void setDescriptions(String descriptions) {
		Descriptions = descriptions;
	}

	public String getCategory() {
		return Category;
	}

	@XmlElement(name = "Category",namespace="http://docs.oasis-open.org/wsrf/bf-2")
	public void setCategory(String category) {
		Category = category;
	}

	@Override
	public String toString() {
		return "ResultStatus [errorCode=" + errorCode + ", Desciption=" + Desciption + ", Descriptions=" + Descriptions
				+ ", Category=" + Category + "]";
	}
}
