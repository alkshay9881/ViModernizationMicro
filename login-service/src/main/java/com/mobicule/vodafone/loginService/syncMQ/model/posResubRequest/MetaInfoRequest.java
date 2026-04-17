package com.mobicule.vodafone.loginService.syncMQ.model.posResubRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MetaInfoRequest {

	 	@JsonProperty("RouteInfo")
	    private RouteInfo routeInfo;
	 	
	    @JsonProperty("Correlation")
	    private Correlation correlation;
	    
	    @JsonProperty("Destination")
	    private Destination destination;
	    
	    @JsonProperty("Source")
	    private Source source;
}
