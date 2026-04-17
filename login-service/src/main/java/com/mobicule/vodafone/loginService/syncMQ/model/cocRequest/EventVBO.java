package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

//@XmlRootElement(name = "EventVBO", namespace = "http://group.vodafone.com/schema/vbm/technical/event/v1")
@XmlType(propOrder = { "ids","type","categories"})
public class EventVBO {

	
	private IDs ids;
	
	private String type;
	
	private Categories categories;
	
	
	public IDs getIds() {
		return ids;
	}
	
	
	//@XmlElement(name = "IDs",namespace="http://group.vodafone.com/schema/common/v1")
	@XmlElement(name = "ns4:IDs")
	public void setIds(IDs ids) {
		this.ids = ids;
	}
	
	public String getType() {
		return type;
	}
	
	
	//@XmlElement(name = "Type",namespace="http://group.vodafone.com/schema/common/v1")
	@XmlElement(name = "ns4:Type")
	public void setType(String type) {
		this.type = type;
	}
	public Categories getCategories() {
		return categories;
	}
	
	
	
	//@XmlElement(name = "Categories",namespace="http://group.vodafone.com/schema/common/v1")
	@XmlElement(name = "ns4:Categories")
	public void setCategories(Categories categories) {
		this.categories = categories;
	}
	
	@Override
	public String toString() {
		return "EventVBO [ids=" + ids + ", type=" + type + ", categories=" + categories + "]";
	}
	
}
