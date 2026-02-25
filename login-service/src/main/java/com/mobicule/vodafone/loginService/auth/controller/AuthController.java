package com.mobicule.vodafone.loginService.auth.controller;
import com.mobicule.vodafone.loginService.auth.util.KeycloakTokenUtil;
import com.mobicule.vodafone.loginService.auth.util.KeycloakUserService;
import com.mobicule.vodafone.loginService.common.dto.TokenResponse;
import com.mobicule.vodafone.loginService.common.entities.Request;
import com.mobicule.vodafone.loginService.common.entities.Response;

import com.mobicule.vodafone.loginService.common.exceptions.PropertyFilePathNotFoundException;
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

    private final KeycloakTokenUtil keycloakTokenUtil;

    @Autowired
    KeycloakUserService keycloakUserService;


    @Autowired
    private APIService utility;

    public AuthController(KeycloakTokenUtil keycloakTokenUtil) {
        this.keycloakTokenUtil = keycloakTokenUtil;
    }


    @PostMapping("/generateOTP")
    public Response generateOtp(@RequestBody Request request) throws IOException {



        String path=utility.getPropertyFilePath("jwtTokenOnOff");

        String isJwtTokenOn =utility.getValueFromPropertyFile(path,"isJwtTokenOn");

        log.info("isJwtTokenOn - "+isJwtTokenOn);



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

        log.info("Response from generateOtp : " + response.getResponseString());
        return response;
    }


    @PostMapping("/verifyOTP")
    public Response verifyOtp(@RequestBody Request request) {


        Response response = new Response(Response.ResponseStatus.SUCCESS, "verify otp", new ArrayList<>());

        log.info("Response from verifyOtp : " + response.getResponseString());
        return response;
    }


    @PostMapping("/verifyPassword")
    public Response verifyPassword(@RequestBody Request request) {

        try {

            Map<String, Object> RequestDataMap =
                    (Map<String, Object>) request.getData().get(0);

            String username = (String) RequestDataMap.get("mobNo");
            String password = (String) RequestDataMap.get("password");

            if (username == null || password == null) {
                return new Response(
                        Response.ResponseStatus.FAILURE,
                        "Username or Password missing",
                        new ArrayList<>()
                );
            }


            //keycloak logic start

            //create user in keycloak
            keycloakUserService.createOrUpdateUser(username, password);

            //get token from keycloak
            TokenResponse token =
                    keycloakTokenUtil.requestToken(username, password);

            Map<String, Object> ResponseDataMap = new HashMap<>();
            ResponseDataMap.put("token", token.getAccess_token());
            ResponseDataMap.put("refresh_token", token.getRefresh_token());
            ResponseDataMap.put("expires_in", token.getExpires_in());
            ResponseDataMap.put("token_type", token.getToken_type());
            ResponseDataMap.put("refresh_expires_in", token.getRefresh_expires_in());

            //keycloak logic end

            Response response = new Response(
                    Response.ResponseStatus.SUCCESS,
                    "Login successful",
                    List.of(ResponseDataMap)
            );


            log.info("Response from verifyPassword: " + response.getResponseString());
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

    @PostMapping("/refreshToken")
    public Response refreshToken(@RequestBody Request request) {

        try {

            Map<String, Object> RequestDataMap =
                    (Map<String, Object>) request.getData().get(0);


            String refreshToken = (String) RequestDataMap.get("refreshToken");

            TokenResponse refreshTokenResponse;

            try {

                refreshTokenResponse = keycloakTokenUtil.refreshToken(refreshToken);

            } catch (ResourceAccessException e) {
                Response response = new Response(Response.ResponseStatus.FAILURE, "Sorry! Your request has timed out. Kindly retry",
                        new ArrayList<>());

                return response;

            } catch (HttpClientErrorException e) {
                log.info("HttpClientErrorException: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);

                if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                    return new Response(Response.ResponseStatus.FAILURE, "some error has been occur - 400 Bad Request",
                            new HashMap<>());
                } else {
                    return new Response(Response.ResponseStatus.FAILURE, "some error has been occur", new HashMap<>());
                }
            } catch (HttpServerErrorException e) {
                log.info("HttpServerErrorException: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
                return new Response(Response.ResponseStatus.FAILURE, "some error has been occur - 500", new HashMap<>());
            } catch (Exception e) {
                log.info("Exception occurred:", e);
                return new Response(Response.ResponseStatus.FAILURE, "Unexpected error occurred", new HashMap<>());
            }


            if (refreshTokenResponse==null)
            {

            }
            //get token from keycloak
            Map<String, Object> ResponseDataMap = new HashMap<>();
            ResponseDataMap.put("token", refreshTokenResponse.getAccess_token());
            ResponseDataMap.put("refresh_token", refreshTokenResponse.getRefresh_token());
            ResponseDataMap.put("expires_in", refreshTokenResponse.getExpires_in());
            ResponseDataMap.put("token_type", refreshTokenResponse.getToken_type());
            ResponseDataMap.put("refresh_expires_in", refreshTokenResponse.getRefresh_expires_in());


            Response response = new Response(Response.ResponseStatus.SUCCESS, "refresh token successfully ", List.of(ResponseDataMap));

            log.info("Response from refreshToken : " + response.getResponseString());
            return response;
        }catch (Exception e)
        {
            log.info("Exception ", e);
        }

        return null;
    }


    @PostMapping("/setPassword")
    public Response setPassword(@RequestBody Request request)
    {
        Response response = new Response(Response.ResponseStatus.SUCCESS, "setPassword successfully ", new ArrayList<>());

        log.info("Response from setPassword : " + response.getResponseString());
        return response;

    }


}
