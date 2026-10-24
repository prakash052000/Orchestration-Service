package com.orchestration.userController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.orchestration.response.CommonRes;
import com.orchestration.routingService.WeCoreMotorClient;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/nonmotor")
public class NonMotorController {

    private final WeCoreMotorClient weCoreMotorClient;

    public NonMotorController(WeCoreMotorClient weCoreMotorClient) {
        this.weCoreMotorClient = weCoreMotorClient;
    }

    @PostMapping("/save")
    public ResponseEntity<CommonRes> save(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody Object body,
            HttpServletRequest request) {
         ResponseEntity<CommonRes> ok = ResponseEntity.ok(weCoreMotorClient.nonMotorSave(bearer(authorization, request), body));
         return ok;
    }

    private String bearer(String authorization, HttpServletRequest request) {
        String header = authorization;
        if (header == null || header.isBlank()) {
            header = request.getHeader("Authorization");
        }
        if (header == null || header.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Missing Authorization. Login at POST http://localhost:8080/auth/login then send Authorization: Bearer <token>");
        }
        header = header.trim();
        if (header.length() >= 2 && header.startsWith("\"") && header.endsWith("\"")) {
            header = header.substring(1, header.length() - 1).trim();
        }
        if (!header.regionMatches(true, 0, "Bearer ", 0, 7)) {
            header = "Bearer " + header;
        }
        return header;
    }
}
