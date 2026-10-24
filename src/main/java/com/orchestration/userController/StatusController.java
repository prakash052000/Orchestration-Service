package com.orchestration.userController;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping({"/", "/ping"})
    public Map<String, String> ping() {
        return Map.of(
                "service", "OrchestrationService",
                "status", "UP",
                "me", "GET /OrchestrationService/me",
                "demo", "GET /OrchestrationService/Demo/user");
    }

    @GetMapping("/me")
    public Map<String, String> me(
            @RequestHeader(value = "X-Auth-User", required = false) String user,
            @RequestHeader(value = "X-Auth-Role", required = false) String role,
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("service", "OrchestrationService");
        body.put("validatedBy", "API-Gateway JWT filter");
        body.put("username", user == null ? "anonymous (hit 9191 directly, not via gateway)" : user);
        body.put("role", role == null ? "none" : role);
        body.put("authorizationHeaderPresent", String.valueOf(authorization != null && !authorization.isBlank()));
        return body;
    }
}
