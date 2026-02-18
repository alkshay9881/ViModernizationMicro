package com.mobicule.vodafone.loginService.common.dto;




import lombok.Data;

@Data
public class TokenResponse {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private Integer expires_in;
    private Integer refresh_expires_in;
}

