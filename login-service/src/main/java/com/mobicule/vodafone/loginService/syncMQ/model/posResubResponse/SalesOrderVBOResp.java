
package com.mobicule.vodafone.loginService.syncMQ.model.posResubResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SalesOrderVBOResp {

    @JsonProperty("ids")
    public IdsResp ids;
    
    @JsonProperty("roles")
    public RolesResp roles;

}
