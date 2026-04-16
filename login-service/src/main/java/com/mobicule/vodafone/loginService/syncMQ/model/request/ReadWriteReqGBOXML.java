package com.mobicule.vodafone.loginService.syncMQ.model.request;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(
        name = "READ_WRITE_RESP_GBO",
        namespace = "http://group.vodafone.com/schema/order/sales-order/v2"
)
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ReadWriteReqGBOXML {

    @XmlElement(name = "msisdn", namespace = "http://group.vodafone.com/schema/order/sales-order/v2")
    private String msisdn;

    @XmlElement(name = "requestId", namespace = "http://group.vodafone.com/schema/order/sales-order/v2")
    private String requestId;
}