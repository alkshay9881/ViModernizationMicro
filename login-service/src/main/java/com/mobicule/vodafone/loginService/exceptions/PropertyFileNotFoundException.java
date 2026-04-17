package com.mobicule.vodafone.loginService.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

public class PropertyFileNotFoundException extends FileNotFoundException {

	private static final long serialVersionUID = 1L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public PropertyFileNotFoundException(String propertyFileName) {
		super("Some error has occurred... - PFNFE");
		log.error(String.format("Property file not found '%s'", propertyFileName));
	}
}
