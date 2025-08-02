package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 03/08/25 01.26
@Last Modified 03/08/25 01.26
Version 1.0
*/

import com.juaracoding.cksteam26.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping()
    public Object findByAccount(
            HttpServletRequest request) {
        return profileService.findByAccount(request);
    }
}
