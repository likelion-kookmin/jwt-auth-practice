package com.likelion.jwt.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class TokenResponse {

    private final String accessToken;
    private final String tokenType = "Bearer";

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
