
package com.mobicule.vodafone.loginService.syncMQ.model.posResubResponse;

import jakarta.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CorrelationResp {

    @JsonProperty("conversationID")
    public String conversationID;

}
