package com.mobicule.vodafone.loginService.syncMQ.model.posResubRequest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RouteInfo {

	
	    @JsonProperty("route")
	    private List<Route> route;
	
}
