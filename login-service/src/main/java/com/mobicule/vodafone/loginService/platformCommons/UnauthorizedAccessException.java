package com.mobicule.vodafone.loginService.platformCommons;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnauthorizedAccessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public UnauthorizedAccessException(String message) {
        log.error(message);
    }
}
