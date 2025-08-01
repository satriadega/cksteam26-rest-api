package com.juaracoding.cksteam26.controller;

import com.juaracoding.cksteam26.dto.validasi.*;
import com.juaracoding.cksteam26.service.AuthService;
import com.juaracoding.cksteam26.service.DocumentService;
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

    @Autowired
    DocumentService documentService;

    @PostMapping("/registration")
    public ResponseEntity<Object> registration(@Valid @RequestBody ValRegistrationDTO registrationDTO, HttpServletRequest request) {
        return authService.registration(authService.mapToUser(registrationDTO), request);
    }

    @PostMapping("/verify-registration")
    public ResponseEntity<Object> verifyRegistration(@Valid @RequestBody ValVerifyRegistrationDTO verifyRegistrationDTO
            , HttpServletRequest request) {
        return authService.verifyRegistration(authService.mapToUser(verifyRegistrationDTO), request);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody ValLoginDTO loginDTO
            , HttpServletRequest request) {
        return authService.login(authService.mapToUser(loginDTO), request);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> tokenExpired(@Valid @RequestBody ValLoginDTO loginDTO
            , HttpServletRequest request) {
        return authService.refreshToken(authService.mapToUser(loginDTO), request);
    }

    @PostMapping("/forgot-password-step-one")
    public ResponseEntity<Object> forgotPasswordStepOne(@Valid @RequestBody ValForgotPasswordDTO forgotPasswordDTO,
                                                        HttpServletRequest request) {
        return authService.lupaPasswordStepOne(authService.mapToUser(forgotPasswordDTO), request);
    }

    @PostMapping("/forgot-password-step-two")
    public ResponseEntity<Object> forgotPasswordStepTwo(@Valid @RequestBody ValForgotPasswordStepTwoDTO dto,
                                                        HttpServletRequest request) {
        return authService.lupaPasswordStepTwo(authService.mapToUser(dto), request);
    }

    @PostMapping("/forgot-password-step-three")
    public ResponseEntity<Object> resetPassword(@RequestBody ValForgotPasswordStepThreeDTO dto, HttpServletRequest request) {
        return authService.lupaPasswordStepThree(dto, request);
    }


}
