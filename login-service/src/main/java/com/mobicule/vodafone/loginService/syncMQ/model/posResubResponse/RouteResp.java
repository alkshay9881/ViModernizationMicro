
package com.mobicule.vodafone.loginService.syncMQ.model.posResubResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RouteResp {

    @JsonProperty("id")
    public String id;
    
    @JsonProperty("keys")
    public KeysResp keys;

}
