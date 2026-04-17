package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteRequest;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RouteInfo")
public class RouteInfo {

	private Route route;

	
	public Route getRoute() {
		return route;
	}

	@XmlElement(name = "hed:Route")
	public void setRoute(Route route) {
		this.route = route;
	}
	
	@Override
	public String toString() {
		return "RouteInfo [route=" + route + "]";
	}

}
