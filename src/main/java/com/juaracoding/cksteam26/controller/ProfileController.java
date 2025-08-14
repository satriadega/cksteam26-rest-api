package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 03/08/25 01.26
@Last Modified 03/08/25 01.26
Version 1.0
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.juaracoding.cksteam26.model.User;
import com.juaracoding.cksteam26.service.AuthService;
import com.juaracoding.cksteam26.service.ProfileService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @Autowired
    AuthService authService;

    @GetMapping()
    public ResponseEntity<Object> findByAccount(
            HttpServletRequest request) {
        return profileService.findByAccount(request);
    }

    @PutMapping(value = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> update(
            @RequestPart("data") String valUpdateProfile,
            @RequestPart(value = "file", required = false) MultipartFile file,
            HttpServletRequest request) {
        return profileService.update(valUpdateProfile, file, request);
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteUser(HttpServletRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = new User();
        user.setUsername(username);
        // Password is not needed here as authentication is already done via JWT
        return authService.deleteUser(user, request);
    }
}
