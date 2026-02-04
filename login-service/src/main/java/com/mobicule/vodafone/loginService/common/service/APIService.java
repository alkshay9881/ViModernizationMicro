package com.mobicule.vodafone.loginService.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.mobicule.vodafone.loginService.common.entities.WSConfig;
import com.mobicule.vodafone.loginService.common.exceptions.PropertyFileNotFoundException;
import com.mobicule.vodafone.loginService.common.exceptions.PropertyFilePathNotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;

@Component
public class APIService {

	ClassLoader classloader = Thread.currentThread().getContextClassLoader();

	@Autowired
	private Environment env;

	public String getPropertyFilePath(String propertyFileName) throws PropertyFilePathNotFoundException {
		InputStream inputStream;
		String propertyFilePath = null;
		inputStream = classloader.getResourceAsStream(env.getProperty("propertyFiles"));
		@SuppressWarnings("unchecked")
		Map<String, String> data = (Map<String, String>) new Yaml().load(inputStream);
		propertyFilePath = data.get(propertyFileName);
		if (propertyFilePath == null)
			throw new PropertyFilePathNotFoundException(propertyFileName);
		return propertyFilePath;
	}

	public String getValueFromPropertyFile(String propertyFilePath, String key)
			throws PropertyFileNotFoundException, IOException {
		String value = null;
		FileInputStream fileInput = null;
		Properties properties = null;
		try {
			fileInput = new FileInputStream(new File(propertyFilePath));
			properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			value = properties.getProperty(key);
		} catch (IOException e) {
			if (fileInput != null)
				fileInput.close();
		}
		if (properties == null)
			throw new PropertyFileNotFoundException(propertyFilePath);
		if (value == null)
			//throw new ValueFromPropertyFileNotFoundException(propertyFilePath, key);
		return value;
		return value;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getAppSettings() {
		InputStream inputStream = classloader.getResourceAsStream(env.getProperty("appSetting"));
		return (Map<String, String>) new Yaml().load(inputStream);
	}

	@SuppressWarnings("unchecked")
	public WSConfig getWebserviceConfig(String entity) {
		InputStream inputStream = classloader.getResourceAsStream(env.getProperty("webserviceConfig"));
		Map<String, Map<String, String>> webserviceConfigs = (Map<String, Map<String, String>>) new Yaml()
				.load(inputStream);
		return new ObjectMapper().convertValue(webserviceConfigs.get(entity), WSConfig.class);
	}



	public Map<String, String> getHeadersInfo(HttpServletRequest request) {
		Map<String, String> headerMap = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			headerMap.put(key, value);
		}
		return headerMap;
	}

	public JSONObject getUSerJsonFromHttpRequest(String jsonString) {
		JSONObject userJson = null;
		try {
			JSONObject json = new JSONObject(jsonString);
			String userJsonstr = json.optString("user");
			userJson = new JSONObject(userJsonstr);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return userJson;
	}

	public long calculateTokenGeneratedTimeDiff(Timestamp tokenCreatedOnDateTime) {
		Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
		Date tokenSentOnDate = new Date(tokenCreatedOnDateTime.getTime());
		Date currentDateDate = new Date(currentDateTime.getTime());
		long timeDiffMilliSeconds = (currentDateDate.getTime() - tokenSentOnDate.getTime());
		long timediffMinutes = (timeDiffMilliSeconds / 1000) / 60;
		return timediffMinutes;
	}

	public String generateSessionTokenId(String etop) {
		int randomNumber = 0;
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder alphabets = new StringBuilder();
		randomNumber = ((int) ((Math.random() * 90000) + 10000));
		for (int i = 0; i < 2; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			alphabets.append(AlphaNumericString.charAt(index));
		}
		return "" + alphabets + randomNumber + System.currentTimeMillis() + etop.substring(5);
	}
}
