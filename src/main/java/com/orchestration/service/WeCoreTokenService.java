package com.orchestration.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.orchestration.routingService.WeCoreAuthClient;

@Service
public class WeCoreTokenService {

    private final WeCoreAuthClient weCoreAuthClient;
    private final String loginId;
    private final String password;

    private volatile String cachedToken;

    public WeCoreTokenService(
            WeCoreAuthClient weCoreAuthClient,
            @Value("${wecore.login-id:}") String loginId,
            @Value("${wecore.password:}") String password) {
        this.weCoreAuthClient = weCoreAuthClient;
        this.loginId = loginId;
        this.password = password;
    }

    public synchronized String bearerToken() {
        if (cachedToken != null && !cachedToken.isBlank()) {
            return "Bearer " + cachedToken;
        }
        if (loginId == null || loginId.isBlank() || password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Set wecore.login-id and wecore.password (WeCore portal user, not the MySQL RDS user)");
        }
        Map<String, String> login = new LinkedHashMap<>();
        login.put("LoginId", loginId);
        login.put("Password", password);
        login.put("ReLoginKey", "Y");
        Map<String, Object> response = weCoreAuthClient.login(login);
        String token = extractToken(response);
        if (token == null || token.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "WeCore login failed. Check GRPZAM portal password. Response: " + response);
        }
        cachedToken = token;
        return "Bearer " + cachedToken;
    }

    public synchronized void evict() {
        cachedToken = null;
    }

    @SuppressWarnings("unchecked")
    private String extractToken(Map<String, Object> response) {
        if (response == null) {
            return null;
        }
        Object result = response.get("Result");
        if (result instanceof Map<?, ?> resultMap) {
            Object token = resultMap.get("Token");
            return token == null ? null : token.toString();
        }
        Object token = response.get("Token");
        return token == null ? null : token.toString();
    }
}
