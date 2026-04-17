package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "routeInfo", "correlation", "destination","source" })
public class SubmitCafLiteMqHeader {

	private RouteInfo routeInfo;
	private Source source;
	private Destination destination;
	private Correlation correlation;

	public RouteInfo getRouteInfo() {
		return routeInfo;
	}

	@XmlElement(name = "RouteInfo")
	public void setRouteInfo(RouteInfo routeInfo) {
		this.routeInfo = routeInfo;
	}

	public Source getSource() {
		return source;
	}

	
	@XmlElement(name = "Source")
	public void setSource(Source source) {
		this.source = source;
	}

		
	public Destination getDestination() {
		return destination;
	}
	
	@XmlElement(name = "Destination")
	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public Correlation getCorrelation() {
		return correlation;
	}

	
	@XmlElement(name = "Correlation")
	public void setCorrelation(Correlation correlation) {
		this.correlation = correlation;
	}


	@Override
	public String toString() {
		return "SubmitCafLiteMqHeader [routeInfo=" + routeInfo + ", source=" + source + ", Destination=" + destination
				+ ", correlation=" + correlation + "]";
	}

	

}
