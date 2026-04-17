package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import jakarta.xml.bind.annotation.XmlElement;

public class Correlation {

	private String ConversationID;

	public String getConversationID() {
		return ConversationID;
	}

	@XmlElement(name = "hed:ConversationID")
	public void setConversationID(String conversationID) {
		ConversationID = conversationID;
	}

	@Override
	public String toString() {
		return "Correlation [ConversationID=" + ConversationID + "]";
	}

	
}
