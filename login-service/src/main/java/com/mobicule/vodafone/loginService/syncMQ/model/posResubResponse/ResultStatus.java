
package com.mobicule.vodafone.loginService.syncMQ.model.posResubResponse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class ResultStatus {

    @JsonProperty("errorCode")
    public String errorCode;
    @JsonProperty("description")
    public List<String> description;
    @JsonProperty("category")
    public String category;

}
