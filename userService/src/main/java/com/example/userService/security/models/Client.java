package com.example.userService.security.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

@Entity
@Table(name="Client1")
public class Client {
    @Id
    private String id;
    private String clientId;
    private String clientSecret;
    @Column(length = 1000)
    private String clientAuthenticationMethods;
    @Column(length = 1000)
    private String authorizationGrantTypes;
    @Column(length = 1000)
    private String redirectUris;
    @Column(length = 1000)
    private String scope;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientAuthenticationMethods() {
        return clientAuthenticationMethods;
    }

    public void setClientAuthenticationMethods(String clientAuthenticationMethods) {
        this.clientAuthenticationMethods = clientAuthenticationMethods;
    }

    public String getAuthorizationGrantTypes() {
        return authorizationGrantTypes;
    }

    public void setAuthorizationGrantTypes(String authorizationGrantTypes) {
        this.authorizationGrantTypes = authorizationGrantTypes;
    }

    public String getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }

    public String getScopes() {
        return scope;
    }

    public void setScopes(String scopes) {
        this.scope = scopes;
    }

    public static Client from(RegisteredClient registeredClient){
        Client client=new Client();

        client.setClientId(registeredClient.getClientId());
        client.setClientSecret(registeredClient.getClientSecret());
        client.setRedirectUris(
                registeredClient.getRedirectUris().stream().findAny().get()
        );
        client.setScopes(
                registeredClient.getScopes().stream().findAny().get()
        );
        client.setAuthorizationGrantTypes(
                registeredClient.getClientAuthenticationMethods().stream().findAny().get().getValue()
        );
        client.setClientAuthenticationMethods(
                registeredClient.getClientAuthenticationMethods().stream().findAny().get().getValue()
        );
        return client;

    }

    public static RegisteredClient from(Client client){
        return RegisteredClient.withId(String.valueOf(client.getId()))
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .redirectUri(client.getRedirectUris())
                .scope(client.getScopes())
                .clientAuthenticationMethod(new ClientAuthenticationMethod(client.getClientAuthenticationMethods()))
                .authorizationGrantType(new AuthorizationGrantType(client.getAuthorizationGrantTypes()))
                .build();

    }
}
