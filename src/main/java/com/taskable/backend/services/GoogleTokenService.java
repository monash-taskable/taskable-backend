package com.taskable.backend.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.taskable.backend.controllers.AuthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GoogleTokenService {

    private static final Logger logger = LoggerFactory.getLogger(GoogleTokenService.class);

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String tokenUri;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();


    public GoogleIdToken exchangeAuthorizationCodeForIdToken(String authorizationCode) throws Exception {
        // Exchange the authorization code for an access token and ID token
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                httpTransport,
                JSON_FACTORY,
                tokenUri,
                clientId,
                clientSecret,
                authorizationCode,
                redirectUri
        ).execute();

        // extract id token from the token response
        String idTokenString = tokenResponse.getIdToken();

        // verify and parse the ID token
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, JSON_FACTORY)
                .setAudience(Collections.singletonList(clientId))
                .build();
        return verifier.verify(idTokenString);
    }
}

