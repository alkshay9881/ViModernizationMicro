package com.mobicule.vodafone.apigateway.common.entities;

import lombok.Data;

@Data
public class User {
	private String syncCheck;
	
//	@JsonProperty("long")
	private String Long;
	private String versionCode;
	private String network;
	private String client;
	private String etop;
	private String entityId;
	private String deviceModel;
	private String version;
	private String syncDate;
	private String lat;
	private String checkVersion;
	private String opt2;
	private String opt1;
	private String androidVersion;
	private String isbetaForSimexConsolidation;
	
}