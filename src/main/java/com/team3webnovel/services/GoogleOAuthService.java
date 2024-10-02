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
import com.team3webnovel.mappers.PwMapper;
import com.team3webnovel.vo.PwVo;
import com.team3webnovel.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Service
public class GoogleOAuthService {

    private static final Logger logger = LoggerFactory.getLogger(GoogleOAuthService.class);
    private static final String CLIENT_ID = "79063217086-lsnrlcthi1q4tkqg9cd713qa3eg6qodc.apps.googleusercontent.com";
    private String CLIENT_SECRET;  // 나중에 초기화할 수 있도록 변경
    private static final String REDIRECT_URI = "http://localhost:8080/team3webnovel/callback";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final NetHttpTransport HTTP_TRANSPORT;

    // Static AES key and IV
    private static final String AES_KEY = "01234567890123456789012345678901"; // 32-byte key
    private static final String AES_IV = "0123456789012345"; // 16-byte IV

    @Autowired
    private PwMapper pwMapper;

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

    // AES Decryption Method
    private String decryptAES(String encryptedPassword) throws Exception {
        logger.debug("Starting AES decryption process...");

        // Convert static key and IV to byte arrays
        byte[] decodedKey = AES_KEY.getBytes("UTF-8");
        byte[] decodedIv = AES_IV.getBytes("UTF-8");

        logger.debug("Decoded Key: {}", Base64.getEncoder().encodeToString(decodedKey));
        logger.debug("IV: {}", AES_IV);

        // Check IV length
        if (decodedIv.length != 16) {
            logger.error("Invalid IV length: {} bytes. IV must be 16 bytes long.", decodedIv.length);
            throw new IllegalArgumentException("IV length must be 16 bytes");
        }

        // AES decryption process
        try {
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(decodedIv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

            // Decode encrypted Base64 password
            byte[] decodedValue = Base64.getDecoder().decode(encryptedPassword);
            logger.debug("Decoded encrypted data (Base64): {}", Base64.getEncoder().encodeToString(decodedValue));

            byte[] decryptedVal = cipher.doFinal(decodedValue);
            String result = new String(decryptedVal, "UTF-8");

            logger.debug("Decryption successful. Decrypted value: {}", result);
            return result;

        } catch (Exception e) {
            logger.error("Decryption failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    // Fetches client secret from database and decrypts it
    private String getClientSecret(String pwName) throws Exception {
        PwVo pwVo = pwMapper.getPasswordByName(pwName);

        if (pwVo != null && pwVo.getPwPw() != null) {
            return decryptAES(pwVo.getPwPw()); // Decrypt password using static key and IV
        } else {
            throw new RuntimeException("Password not found for the given name.");
        }
    }

    // Initialize OAuth flow
    public void initializeFlow() throws Exception {
        if (this.CLIENT_SECRET == null) {
            this.CLIENT_SECRET = getClientSecret("google_client_secret"); // Replace with the appropriate pwName
        }

        String homeDir = System.getProperty("user.home");
        File tokensDir = new File(homeDir, "team3webnovel_tokens");

        if (!tokensDir.exists()) {
            tokensDir.mkdirs();
        }

        GoogleClientSecrets clientSecrets = new GoogleClientSecrets().setInstalled(
                new GoogleClientSecrets.Details().setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET));

        FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(tokensDir);
        flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, Arrays.asList("profile", "email"))
                .setAccessType("offline")
                .setDataStoreFactory(dataStoreFactory)
                .build();
        logger.info("Google OAuth flow initialized.");
    }

    // Returns the authorization URL
    public String getAuthorizationUrl() throws Exception {
        initializeFlow();
        AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
        authorizationUrl.setRedirectUri(REDIRECT_URI);
        return authorizationUrl.build();
    }

    public GoogleOAuthService() {
        logger.debug("GoogleOAuthService constructor called.");
    }

    public Credential getCredential(String code) throws IOException {
        logger.debug("Exchanging authorization code for credentials...");
        TokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, code, REDIRECT_URI)
                .execute();
        logger.info("Token received: {}", tokenResponse.toPrettyString());

        String userId = "useremail@example.com"; // Replace with appropriate user identifier
        Credential credential = flow.createAndStoreCredential(tokenResponse, userId);

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
