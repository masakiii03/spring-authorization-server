package com.example.spring_authorization_server_sample.service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;
import java.util.Collection;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import com.example.spring_authorization_server_sample.dto.RegisterClientDto;
import com.example.spring_authorization_server_sample.repository.CustomRegisteredClientRepository;

@Service
public class AuthorizationServerService {

    private final CustomRegisteredClientRepository customRegisteredClientRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthorizationServerService(CustomRegisteredClientRepository customRegisteredClientRepository, PasswordEncoder passwordEncoder) {
        this.customRegisteredClientRepository = customRegisteredClientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 新規でクライアントを登録する
     * トークンの期限は10分
     * 
     * @return クライアントIDとクライアントシークレット
     */
    public RegisterClientDto registerClient() {
        String clientId = UUID.randomUUID().toString();
        String clientSecret = generateClientSecret();

        TokenSettings tokenSettings = TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofMinutes(10))
                .build();

        RegisteredClient newClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientId)
                .clientSecret(passwordEncoder.encode(clientSecret))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenSettings(tokenSettings)
                .scope("read")
                .scope("write")
                .build();

            customRegisteredClientRepository.save(newClient);

        return new RegisterClientDto(clientId, clientSecret);
    }

    /**
     * 登録されているクライアントを全て取得する
     * 
     * @return 全ての登録されているクライアント情報
     */
    public Collection<RegisteredClient> findAllClients() {
        return customRegisteredClientRepository.findAll();
    }

    /**
     * クライアントIDを指定してクライアント情報を取得する
     * 
     * @param clientId クライアントID
     * @return 特定のクライアント情報
     */
    public RegisteredClient findClient(String clientId) {
        return customRegisteredClientRepository.findByClientId(clientId);
    }

    /**
     * base64形式のクライアントシークレットを生成する
     * 
     * @return クライアントシークレット
     */
    private String generateClientSecret() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

}
