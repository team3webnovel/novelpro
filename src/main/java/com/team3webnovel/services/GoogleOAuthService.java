package com.team3webnovel.services;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Person;
import com.team3webnovel.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

@Service
public class GoogleOAuthService {

    private static final Logger logger = LoggerFactory.getLogger(GoogleOAuthService.class);
    private static final String CLIENT_ID = "79063217086-lsnrlcthi1q4tkqg9cd713qa3eg6qodc.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8080/team3webnovel/callback";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final NetHttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            logger.info("HTTP Transport initialized successfully.");
        } catch (GeneralSecurityException | IOException e) {
            logger.error("Error initializing HTTP Transport", e);
            throw new RuntimeException(e);
        }
    }

    private AuthorizationCodeFlow flow;

    public GoogleOAuthService() throws IOException {
        logger.debug("Setting up token directory and flow initialization.");

        // 사용자 홈 디렉토리 설정
        String homeDir = System.getProperty("user.home");
        File tokensDir = new File(homeDir, "team3webnovel_tokens");
        logger.info("Tokens directory path: {}", tokensDir.getAbsolutePath());

        // 디렉토리가 존재하지 않으면 생성
        if (!tokensDir.exists()) {
            boolean isCreated = tokensDir.mkdirs();  // 경로가 존재하지 않을 경우 디렉토리 생성
            if (isCreated) {
                logger.info("Tokens directory created at {}", tokensDir.getAbsolutePath());
            } else {
                logger.error("Failed to create tokens directory at {}", tokensDir.getAbsolutePath());
            }
        }

        // Google Client Secrets 설정
        GoogleClientSecrets clientSecrets = new GoogleClientSecrets().setInstalled(
            new GoogleClientSecrets.Details().setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET));
        logger.info("Google Client Secrets initialized.");

        // 토큰 저장 경로 설정 및 OAuth 2.0 Flow 설정
        FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(tokensDir);
        flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, Arrays.asList("profile", "email"))
                .setAccessType("offline")
                .setDataStoreFactory(dataStoreFactory)
                .build();
        logger.info("Google OAuth flow initialized with offline access.");
    }

    public String getAuthorizationUrl() {
        logger.debug("Generating Google Authorization URL...");
        AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
        authorizationUrl.setRedirectUri(REDIRECT_URI);
        String url = authorizationUrl.build();
        logger.info("Authorization URL generated: {}", url);
        return url;
    }

    public Credential getCredential(String code) throws IOException {
        logger.debug("Exchanging authorization code for credentials...");
        TokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, code, REDIRECT_URI)
                .execute();
        logger.info("Token received: {}", tokenResponse.toPrettyString());

        String userId = "useremail@example.com";  // 사용자 ID를 지정해 줍니다.
        Credential credential = flow.createAndStoreCredential(tokenResponse, userId);

        // Credential이 null인지 확인
        if (credential == null) {
            logger.error("Failed to obtain Credential. Returned null.");
            throw new RuntimeException("Credential is null.");
        } else {
            logger.info("Credential successfully created and stored.");
        }
        return credential;
    }

    public UserVo getUserInfo(String code) throws IOException {
        logger.debug("Fetching user information using the provided code...");

        Credential credential = getCredential(code);

        logger.debug("Initializing People API...");
        PeopleService peopleService = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("Team3 Webnovel Application")
                .build();
        logger.info("People API initialized.");

        logger.debug("Requesting user profile from Google People API...");
        Person profile = peopleService.people().get("people/me")
                .setPersonFields("names,emailAddresses")
                .execute();
        logger.info("User profile retrieved: {}", profile.toPrettyString());

        UserVo user = new UserVo();
        if (profile.getNames() != null && !profile.getNames().isEmpty()) {
            user.setUsername(profile.getNames().get(0).getDisplayName());
            logger.debug("User name set: {}", user.getUsername());
        }
        if (profile.getEmailAddresses() != null && !profile.getEmailAddresses().isEmpty()) {
            user.setEmail(profile.getEmailAddresses().get(0).getValue());
            logger.debug("User email set: {}", user.getEmail());
        }

        logger.info("User information successfully processed.");
        return user;
    }
}
