
package com.mobicule.vodafone.loginService.syncMQ.model.posResubRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IdList {

    @JsonProperty("schemeName")
    public String schemeName;
    
    @JsonProperty("value")
    public String value;

}
