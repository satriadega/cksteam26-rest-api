package com.juaracoding.cksteam26.controller;

import com.juaracoding.cksteam26.dto.validasi.ValRegistrationDTO;
import com.juaracoding.cksteam26.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<Object> registration(@Valid @RequestBody ValRegistrationDTO registrationDTO, HttpServletRequest request) {
        return authService.registration(authService.mapToUser(registrationDTO), request);
    }
}
