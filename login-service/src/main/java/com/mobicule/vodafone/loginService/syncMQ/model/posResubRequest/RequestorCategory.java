package com.mobicule.vodafone.loginService.syncMQ.model.posResubRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestorCategory {

	@JsonProperty("listName")
    public String listName;
	
    @JsonProperty("value")
    public String value;
	
}
