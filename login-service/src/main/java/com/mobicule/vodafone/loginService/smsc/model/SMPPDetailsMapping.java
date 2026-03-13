package com.mobicule.vodafone.loginService.smsc.model;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "SMS_CONFIG_DETAILS")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SMPPDetailsMapping {
    @Id
    @GeneratedValue(generator = "sms_config_detials_seq")
    @SequenceGenerator(name = "sms_config_detials_seq", sequenceName = "sms_config_detials_seq", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ETOP_NUMBER")
    private String etopNumber;

    @Column(name = "CIRCLE_CODE")
    private Long circleCode;

    @Column(name = "MODULE_NAME")
    private String moduleName;

    @Column(name = "EVENT_NAME")
    private String eventName;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "CREATED_ON")
    private Timestamp createdOn;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "MODIFIED_ON")
    private Timestamp modifiedOn;

    @Column(name = "MODIFIED_BY")
    private Long modifiedBy;

    @Column(name = "DELETE_FLAG")
    private String deleteFlag;

    @Column(name = "SMS_SENT_NUMBER")
    private String smsSentNumber;

    @Column(name = "MSG_ID")
    private String msgId;

}

