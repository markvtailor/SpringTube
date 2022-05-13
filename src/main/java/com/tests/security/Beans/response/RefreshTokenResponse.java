package com.tests.security.Beans.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenResponse {

    private String accessToken;
    private String refreshToken;
    private String type = "Bearer";

    public RefreshTokenResponse(String accessToken, String refreshToken){
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
