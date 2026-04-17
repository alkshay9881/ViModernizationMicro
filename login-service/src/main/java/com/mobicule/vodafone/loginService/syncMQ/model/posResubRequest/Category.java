
package com.mobicule.vodafone.loginService.syncMQ.model.posResubRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Category {

    @JsonProperty("listHierarchyID")
    public String listHierarchyID;
    
    @JsonProperty("value")
    public String value;
    
    @JsonProperty("listName")
    public String listName;
    
    @JsonProperty("listID")
    private String listID;

}
