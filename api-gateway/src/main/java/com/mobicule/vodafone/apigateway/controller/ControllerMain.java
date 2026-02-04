package com.mobicule.vodafone.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerMain {

    @GetMapping("/")
    public String home() {
        return "Api gateway Service is running ";
    }

}

