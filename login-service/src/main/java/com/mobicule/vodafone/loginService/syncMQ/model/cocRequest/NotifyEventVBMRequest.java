package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;



/*@XmlRootElement(name = "NotifyResourceInventoryVBMRequest")*/
public class NotifyEventVBMRequest {
	private EventVBO eventVBO;

	public EventVBO getEventVBO() {
		return eventVBO;
	}
	
	//@XmlElement(name = "EventVBO",namespace="http://group.vodafone.com/schema/vbm/technical/event/v1")
	@XmlElement(name = "ns5:EventVBO")
	public void setEventVBO(EventVBO eventVBO) {
		this.eventVBO = eventVBO;
	}

	@Override
	public String toString() {
		return "NotifyEventVBMRequest [eventVBO=" + eventVBO + "]";
	}

}
