package com.mobicule.vodafone.apigateway.common.entities;

import lombok.Data;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Request {
	private User user;
	private String entity;
	private String identifier;
	private String type;
	private Map<String, Object> queryParameterMap = new HashMap<>();
	private List<Map<?,?>> data = new ArrayList<>();
	private String action;
	
	public JSONObject print() {
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("user", user);
		hashMap.put("entity", entity);
		hashMap.put("type", type);
		hashMap.put("queryParameterMap", queryParameterMap);
		hashMap.put("data", data);
		hashMap.put("action", action);
		return new JSONObject(hashMap);
	}
}
