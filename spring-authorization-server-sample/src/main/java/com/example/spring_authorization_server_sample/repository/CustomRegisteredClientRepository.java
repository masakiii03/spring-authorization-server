package com.example.spring_authorization_server_sample.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final Map<String, RegisteredClient> clientMap = new HashMap<>();

    @Override
    public void save(RegisteredClient registeredClient) {
        clientMap.put(registeredClient.getClientId(), registeredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        return clientMap.values().stream()
                .filter(client -> client.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return clientMap.get(clientId);
    }

    public Collection<RegisteredClient> findAll() {
        return clientMap.values();
    }

}
