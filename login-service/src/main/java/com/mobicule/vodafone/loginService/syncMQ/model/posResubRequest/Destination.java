
package com.mobicule.vodafone.loginService.syncMQ.model.posResubRequest;

import jakarta.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Destination {

    @JsonProperty("system")
    public String system;

}
