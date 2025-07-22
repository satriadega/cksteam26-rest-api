package com.juaracoding.cksteam26.controller;

import com.juaracoding.cksteam26.dto.validasi.ValRegistrationDTO;
import com.juaracoding.cksteam26.service.AuthService;
import com.juaracoding.cksteam26.service.DocumentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/test")
    public Object findAll(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id").descending());
        return documentService.findAll(pageable, request);
    }
}
