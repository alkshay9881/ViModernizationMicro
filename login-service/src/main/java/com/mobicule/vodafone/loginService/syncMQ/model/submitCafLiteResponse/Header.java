package com.mobicule.vodafone.loginService.syncMQ.model.submitCafLiteResponse;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;




@XmlRootElement(name = "Header")
public class Header {

	private RouteInfo routeInfo;
	
	private ResultStatus ResultStatus;

	private Correlation correaltion;
	
	private Destination destination;
	
	private Source source;
	
	public RouteInfo getRouteInfo() {
		return routeInfo;
	}

	@XmlElement(name = "RouteInfo")
	public void setRouteInfo(RouteInfo routeInfo) {
		this.routeInfo = routeInfo;
	}

	public ResultStatus getResultStatus() {
		return ResultStatus;
	}

	@XmlElement(name = "ResultStatus")
	public void setResultStatus(ResultStatus resultStatus) {
		ResultStatus = resultStatus;
	}

	
	public Correlation getCorrealtion() {
		return correaltion;
	}

	@XmlElement(name = "Correlation")
	public void setCorrealtion(Correlation correaltion) {
		this.correaltion = correaltion;
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
		return "Header [routeInfo=" + routeInfo + ", ResultStatus=" + ResultStatus + ", correaltion=" + correaltion
				+ ", destination=" + destination + ", source=" + source + "]";
	}
	
	
}
