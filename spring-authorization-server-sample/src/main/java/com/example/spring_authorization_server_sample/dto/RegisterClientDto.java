package com.example.spring_authorization_server_sample.dto;

public class RegisterClientDto {

    String clientId;
    String clientSecret;

    public RegisterClientDto(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }
    public String getClientSecret() {
        return clientSecret;
    }

}
