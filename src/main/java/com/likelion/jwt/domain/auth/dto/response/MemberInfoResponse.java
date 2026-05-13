package com.likelion.jwt.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class MemberInfoResponse {

    private final String username;
    private final String role;

    public MemberInfoResponse(String username, String role) {
        this.username = username;
        this.role = role;
    }
}
