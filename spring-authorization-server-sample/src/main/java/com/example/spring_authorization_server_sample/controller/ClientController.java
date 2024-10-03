package com.example.spring_authorization_server_sample.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_authorization_server_sample.dto.RegisterClientDto;
import com.example.spring_authorization_server_sample.service.AuthorizationServerService;

@RestController
public class ClientController {

    private final AuthorizationServerService authorizationServerService;

    public ClientController(AuthorizationServerService authorizationServerService) {
        this.authorizationServerService = authorizationServerService;
    }

    /**
     * クライアント登録
     * 
     * @return 登録したクライアント情報(clientId, clientSecret)
     */
    @PostMapping("/clients")
    public ResponseEntity<RegisterClientDto> registerClient() {
        RegisterClientDto client = authorizationServerService.registerClient();
        return ResponseEntity.ok(client);
    }

    /**
     * クライアント情報の全件取得
     * 
     * @return クライアント情報リスト
     */
    @GetMapping("/clients")
    public ResponseEntity<Collection<RegisteredClient>> findClients() {
        return ResponseEntity.ok(authorizationServerService.findAllClients());
    }

    /**
     * クライアント情報の取得
     * 
     * @param clientId クライアントID
     * @return クライアント情報
     */
    @GetMapping("/clients/{clientId}")
    public ResponseEntity<RegisteredClient> findClient(@PathVariable("clientId") String clientId) {
        return ResponseEntity.ok(authorizationServerService.findClient(clientId));
    }

}
