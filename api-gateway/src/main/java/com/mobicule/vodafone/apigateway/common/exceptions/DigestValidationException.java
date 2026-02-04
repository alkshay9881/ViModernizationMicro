package com.mobicule.vodafone.apigateway.common.exceptions;

public class DigestValidationException extends RuntimeException {
    public DigestValidationException(String message) {
        super(message);
    }
}
