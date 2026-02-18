package com.mobicule.vodafone.loginService.common.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;  // Added for ignoring fields
import com.fasterxml.jackson.annotation.JsonProperty;  // Added for custom property names
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
public class Response {

	// Commented out instance Logger (not serialized anyway, but static Logger is fine)
	// private final Logger log = LoggerFactory.getLogger(this.getClass());
	public enum ResponseStatus {
		SUCCESS, FAILURE, upgrade_major, upgrade_minor;
	}

	@JsonIgnore  // Exclude from JSON serialization (internal map)
	private Map<String, Object> responsetMap;

	@JsonIgnore  // Exclude from JSON serialization (string representation)
	private String responseString;

	@SuppressWarnings("deprecation")
	public Response(ResponseStatus status, String message, Object data) {
		this.responsetMap = new HashMap<>();
		if (status != null)
			this.responsetMap.put("status", status.toString());
		if (message != null)
			this.responsetMap.put("message", message);
		if (data != null)
			this.responsetMap.put("data", data);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			(new ObjectMapper()).writeValue(byteArrayOutputStream, this.responsetMap);
		} catch (Exception e) {
			log.error("JSON creation failed from given string.", e);
		}
		this.responseString = byteArrayOutputStream.toString();
	}

	@SuppressWarnings("deprecation")
	public Response(Object data) {
		this.responsetMap = new HashMap<>();
		if (data != null)
			this.responsetMap.put("data", data);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			(new ObjectMapper()).writeValue(byteArrayOutputStream, this.responsetMap);
		} catch (Exception e) {
			log.error("JSON creation failed from given string.", e);
		}
		this.responseString = byteArrayOutputStream.toString();
	}

	public Response(ResponseStatus status, Object data) {
		this.responsetMap = new HashMap<>();
		if (status != null)
			this.responsetMap.put("status", status.toString());
		if (data != null)
			this.responsetMap.put("data", data);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			(new ObjectMapper()).writeValue(byteArrayOutputStream, this.responsetMap);
		} catch (Exception e) {
			log.error("JSON creation failed from given string.", e);
		}
		this.responseString = byteArrayOutputStream.toString();
	}

	public JSONObject print() {
		return new JSONObject(responsetMap);
	}

	@JsonProperty("status")  // Include in JSON as "status"
	public String getStatus() {
		if (this.responsetMap == null)
			return null;
		return ResponseStatus.valueOf((String) this.responsetMap.get("status")).toString();
	}

	@JsonProperty("message")  // Include in JSON as "message"
	public String getMessage() {
		if (this.responsetMap == null)
			return null;
		return (String) this.responsetMap.get("message");
	}

	@JsonProperty("data")  // Include in JSON as "data"
	public Object getData() {
		if (this.responsetMap == null)
			return null;
		return this.responsetMap.get("data");
	}
}