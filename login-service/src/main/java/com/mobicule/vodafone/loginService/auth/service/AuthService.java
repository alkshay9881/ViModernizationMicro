package com.mobicule.vodafone.loginService.auth.service;

import com.mobicule.vodafone.loginService.common.entities.Request;
import com.mobicule.vodafone.loginService.common.entities.Response;

public interface AuthService {
    Response generateOtp(Request request);
    Response verifyPassword(Request request);
    Response refreshToken(Request request);
    Response verifyOtp(Request request);
    Response setPassword(Request request);
}
