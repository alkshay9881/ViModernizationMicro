
package com.mobicule.vodafone.loginService.syncMQ.model.posResubResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MetaInfoResponse {

    @JsonProperty("RouteInfo")
    public RouteInfoResp routeInfo;
    
    @JsonProperty("Source")
    public SourceResp source;
    
    @JsonProperty("Destination")
    public DestinationResp destination;
    
    @JsonProperty("Correlation")
    public CorrelationResp correlation;
    
    @JsonProperty("ResultStatus")
    public ResultStatus resultStatus;

}
