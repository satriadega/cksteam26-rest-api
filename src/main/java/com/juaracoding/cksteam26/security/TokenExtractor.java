package com.juaracoding.cksteam26.security;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 03/08/25 13.59
@Last Modified 03/08/25 13.59
Version 1.0
*/

import com.juaracoding.cksteam26.config.JwtConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TokenExtractor {

    private final JwtUtility jwtUtility;

    public TokenExtractor(JwtUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    public String extractUsernameFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            return null;
        }

        String token = bearerToken.substring(7);
        if ("y".equalsIgnoreCase(JwtConfig.getTokenEncryptEnable())) {
            token = Crypto.performDecrypt(token);
        }

        Map<String, Object> claims = jwtUtility.mappingBodyToken(token);
        String username = String.valueOf(claims.get("username"));

//        System.out.println(username);
        return (username != null && !username.isEmpty()) ? username : null;
    }
}

