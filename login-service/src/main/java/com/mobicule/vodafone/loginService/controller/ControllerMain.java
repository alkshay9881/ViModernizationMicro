package com.mobicule.vodafone.loginService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerMain {

    @GetMapping("/")
    public String home() {
        return "login Service is running ";
    }

}

