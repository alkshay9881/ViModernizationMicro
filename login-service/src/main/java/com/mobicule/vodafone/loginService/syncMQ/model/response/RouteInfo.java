package com.mobicule.vodafone.loginService.syncMQ.model.response;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RouteInfo")
public class RouteInfo {

	private Route route;

	
	public Route getRoute() {
		return route;
	}

	@XmlElement(name = "Route", namespace="http://group.vodafone.com/contract/vho/header/v1")
	public void setRoute(Route route) {
		this.route = route;
	}
	
	@Override
	public String toString() {
		return "RouteInfo [route=" + route + "]";
	}

}
