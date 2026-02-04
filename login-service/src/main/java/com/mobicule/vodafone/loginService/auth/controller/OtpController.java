package com.mobicule.vodafone.loginService.auth.controller;

import com.mobicule.vodafone.loginService.common.entities.Request;
import com.mobicule.vodafone.loginService.common.entities.Response;

import com.mobicule.vodafone.loginService.dto.TokenResponse;
import com.mobicule.vodafone.loginService.util.KeycloakTokenUtil;
import com.mobicule.vodafone.loginService.util.KeycloakUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/v1/otp")
public class OtpController {


    private static final Logger log =
            LoggerFactory.getLogger(OtpController.class);


    private final KeycloakTokenUtil keycloakTokenUtil;


    @Autowired
    KeycloakUserService keycloakUserService;

    public OtpController(KeycloakTokenUtil keycloakTokenUtil) {
        this.keycloakTokenUtil = keycloakTokenUtil;
    }


    @PostMapping("/generateOTP")
    public Response generateOtp(@RequestBody Request request) {


        // Get pre-login token
        String preLoginToken = keycloakTokenUtil.getPreLoginToken();
        log.info("Pre-Login Token: {}", preLoginToken);


// Create a Map for token
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", preLoginToken);

// Add token map to a list (data is a List)
        List<Map<String, String>> dataList = new ArrayList<>();
        dataList.add(tokenMap);


        Response response = new Response(Response.ResponseStatus.SUCCESS, "generate otp", dataList);

        log.info("Response : " + response.getResponseString());
        return response;
    }


    @PostMapping("/verifyOTP")
    public Response verifyOtp(@RequestBody Request request) {


        Response response = new Response(Response.ResponseStatus.SUCCESS, "verify otp", new ArrayList<>());

        log.info("Response : " + response.getResponseString());
        return response;
    }


    @PostMapping("/verifyPassword")
    public Response verifyPassword(@RequestBody Request request) {

        try {

            Map<String, Object> dataMap =
                    (Map<String, Object>) request.getData().get(0);

            String username = (String) dataMap.get("mobNo");
            String password = (String) dataMap.get("password");

            if (username == null || password == null) {
                return new Response(
                        Response.ResponseStatus.FAILURE,
                        "Username or Password missing",
                        new ArrayList<>()
                );
            }

            keycloakUserService.createOrUpdateUser(username,password );

        //   String Token= keycloakTokenUtil.getUserLoginToken(username,password);


           // Create a Map for token
           /* Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", Token);*/

            // Add token map to a list (data is a List)
           /* List<Map<String, String>> dataList = new ArrayList<>();
            dataList.add(tokenMap);*/


            TokenResponse token =
                    keycloakTokenUtil.requestToken(username, password);
            Response response= new Response(
                    Response.ResponseStatus.SUCCESS,
                    "Login successful",
                    List.of(token)
            );


            log.info("Response : " + response.getResponseString());
            return response;

        } catch (Exception e) {

            log.error("Error in verifyPassword", e);

            return new Response(
                    Response.ResponseStatus.FAILURE,
                    "Something went wrong",
                    new ArrayList<>()
            );
        }
    }


}
