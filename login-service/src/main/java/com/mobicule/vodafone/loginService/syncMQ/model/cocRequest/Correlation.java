package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@XmlRootElement(name = "Correlation")

public class Correlation {

	private String conversationID;

	public String getConversationID() {
		return conversationID;
	}
	//@XmlElement(name = "ConversationID",namespace = "http://group.vodafone.com/contract/vho/header/v1")
	@XmlElement(name = "ns2:ConversationID")
	public void setConversationID(String conversationID) {
		this.conversationID = conversationID;
	}

	@Override
	public String toString() {
		return "Correlation [conversationID=" + conversationID + "]";
	}
	
}
