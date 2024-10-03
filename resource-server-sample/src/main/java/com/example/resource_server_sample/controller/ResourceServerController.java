package com.example.resource_server_sample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceServerController {

    @GetMapping("/")
    public ResponseEntity<String> getResource() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String clientId = principal instanceof Jwt && ((Jwt) principal).getClaims().get("sub") != null ? ((Jwt) principal).getClaims().get("sub").toString() : "Unknown client";

        String response = String.format("Successfully accessed the resource server with clientId: %s", clientId);

        return ResponseEntity.ok(response);
    }

}
