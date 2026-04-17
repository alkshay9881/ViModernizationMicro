package com.mobicule.vodafone.loginService.syncMQ.model.posResubRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateSalesOrderVBMRequestReq {

	    @JsonProperty("MetaInfoRequest")
	    private MetaInfoRequest metaInfoRequest;
	    
	    @JsonProperty("salesOrderVBO")
	    private SalesOrderVBO salesOrderVBO;
}
