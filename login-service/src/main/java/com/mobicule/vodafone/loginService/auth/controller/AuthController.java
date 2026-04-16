package com.mobicule.vodafone.loginService.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobicule.vodafone.loginService.auth.service.AuthServiceImpl;
import com.mobicule.vodafone.loginService.auth.util.KeycloakTokenUtil;
import com.mobicule.vodafone.loginService.auth.util.KeycloakUserService;
import com.mobicule.vodafone.loginService.common.dto.TokenResponse;
import com.mobicule.vodafone.loginService.common.entities.Request;
import com.mobicule.vodafone.loginService.common.entities.Response;
import com.mobicule.vodafone.loginService.common.service.APIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthServiceImpl authServiceimpl;

    public AuthController( AuthServiceImpl authServiceimpl) {

        this.authServiceimpl = authServiceimpl;
    }

    @PostMapping("/generateOTP")
    public Response generateOtp(@RequestBody Request request) throws IOException {
        return authServiceimpl.generateOtp(request);
    }

    @PostMapping("/verifyOTP")
    public Response verifyOtp(@RequestBody Request request) {
        return authServiceimpl.verifyOtp(request);
    }

    @PostMapping("/verifyPassword")
    public Response verifyPassword(@RequestBody Request request) {
        return authServiceimpl.verifyPassword(request);

    }

    @PostMapping("/refreshToken")
    public Response refreshToken(@RequestBody Request request) {
        return authServiceimpl.refreshToken(request);
    }

    @PostMapping("/setPassword")
    public Response setPassword(@RequestBody Request request) {
        return authServiceimpl.setPassword(request);

    }
}
