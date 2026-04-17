package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@XmlType(propOrder = { "routeInfo", "correlation", "destination", "source" })
public class Header {
	
	
	private RouteInfo routeInfo;

	private Correlation correlation;

	private Destination destination;

	private Source source;

	public RouteInfo getRouteInfo() {
		return routeInfo;
	}

	@XmlElement(name = "RouteInfo")
	public void setRouteInfo(RouteInfo routeInfo) {
		this.routeInfo = routeInfo;
	}

	public Correlation getCorrelation() {
		return correlation;
	}
	
	@XmlElement(name = "Correlation")
	public void setCorrelation(Correlation correlation) {
		this.correlation = correlation;
	}

	public Destination getDestination() {
		return destination;
	}

	@XmlElement(name = "Destination")
	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public Source getSource() {
		return source;
	}

	@XmlElement(name = "Source")
	public void setSource(Source source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "Header [routeInfo=" + routeInfo + ", correlation=" + correlation + ", destination=" + destination
				+ ", source=" + source + "]";
	}
	
}