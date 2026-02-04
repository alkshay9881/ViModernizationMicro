package com.mobicule.vodafone.loginService.common.entities;

import lombok.Data;

@Data
public class WSConfig {
	private String responseMediaType;
	private String httpMethodType = "post";
	private String finalResponseType = "object";
	private String requestParameterType;
	private String mediaType;
	private String searchTag = "";
	private String responseFrom;
	private String responseFilePath;
	private int readTimeOut = 0;
	private int connectionTimeOut = 0;
	private int timeOut = 11300;
	private String formElement = "inputData";
	private String logFlag = "true";
	private String bean;
	private String soapaction;
	private String setactionheader = "true";
	private String soapstarttag;
	private String soapbodytag;
	private String soapendtag;
	private String isnamespaceaware;
	private String headerResponseclass;
	private String soapheadertag;
	private String responseClass;
	private String connectionFactory;
	private String inputJNDI;
	private String outputJNDI;
	private String hostname;
	private Long mqTimeOut = 15000L;
}
