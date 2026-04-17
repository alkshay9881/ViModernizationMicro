
package com.mobicule.vodafone.loginService.syncMQ.model.posResubResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RolesResp {

    @JsonProperty("requestor")
    public RequestorResp requestor;

}
