package com.mobicule.vodafone.loginService.syncMQ.model.posResubRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SalesOrderVBO {

	    @JsonProperty("ids")
	    public Ids ids;
	    
	    @JsonProperty("relatedSalesOrders")
	    public RelatedSalesOrders relatedSalesOrders;
	    
	    @JsonProperty("roles")
	    public Roles roles;
	
}
