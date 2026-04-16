package com.mobicule.vodafone.loginService.syncMQ.model.response;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Correlation")
public class Correlation {

	private String conversionId;

	public String getConversionId() {
		return conversionId;
	}

	@XmlElement(name = "ConversationID",namespace ="http://group.vodafone.com/contract/vho/header/v1")
	public void setConversionId(String conversionId) {
		this.conversionId = conversionId;
	}
	@Override
	public String toString() {
		return "Correlation [conversionId=" + conversionId + "]";
	}	
	
	
}
