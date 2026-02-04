package com.mobicule.vodafone.apigateway.fallback;


import com.mobicule.vodafone.apigateway.common.entities.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    private static final Logger log =
            LoggerFactory.getLogger(FallbackController.class);


    @GetMapping("/login")
    public Response loginFallback() {

        Response response = new Response(Response.ResponseStatus.FAILURE, "login Service is currently unavailable", new ArrayList<>());
        log.info("Response : "+response.getResponseString());
        return response;

    }


    @PostMapping("/login")
    public Response loginFallbackPost() {

        Response response = new Response(Response.ResponseStatus.FAILURE, "login Service is currently unavailable", new ArrayList<>());
        log.info("Response : "+response.getResponseString());
        return response;

    }



}
