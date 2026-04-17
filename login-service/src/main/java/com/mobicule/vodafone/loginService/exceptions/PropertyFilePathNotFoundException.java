package com.mobicule.vodafone.loginService.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

public class PropertyFilePathNotFoundException extends FileNotFoundException {

	private static final long serialVersionUID = 1L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public PropertyFilePathNotFoundException(String propertyFileName) {
		super("Some error has occurred... - PFPNFE");
		log.error(String.format("Property File Path Not Found For File '%s'", propertyFileName));
	}
}
