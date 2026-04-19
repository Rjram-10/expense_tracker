package com.example.expenses_tracker.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health/host")
    public ResponseEntity<Map<String, String>> hostInfo(HttpServletRequest request) {
        Map<String, String> info = new LinkedHashMap<>();
        info.put("requestURL", request.getRequestURL().toString());
        info.put("requestURI", request.getRequestURI());
        info.put("scheme", request.getScheme());
        info.put("serverName", request.getServerName());
        info.put("serverPort", String.valueOf(request.getServerPort()));
        info.put("hostHeader", request.getHeader("Host"));
        info.put("xForwardedHost", request.getHeader("X-Forwarded-Host"));
        info.put("xForwardedProto", request.getHeader("X-Forwarded-Proto"));
        info.put("xForwardedFor", request.getHeader("X-Forwarded-For"));
        return ResponseEntity.ok(info);
    }
}
