package com.mobicule.vodafone.loginService.syncMQ.model.cocRequest;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;


//@XmlRootElement(name = "RouteInfo")
public class RouteInfo {

	private List<Route> route;

	public List<Route> getRoute() {
		return route;
	}

	//XmlElement(name = "Route", namespace = "http://group.vodafone.com/contract/vho/header/v1")
	@XmlElement(name="ns2:Route")
	public void setRoute(List<Route> route) {
		this.route = route;
	}

	

}
