package com.example.ecommerce.modals;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("role")
    private String role;
    @JsonProperty("message")
    private String message;

    public AuthenticationResponse(String accessToken, String refreshToken, String message,String role,Long id) {
        this.accessToken = accessToken;
        this.message = message;
        this.refreshToken = refreshToken;
        this.role = role;
        this.id=id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getMessage() {
        return message;
    }
}
