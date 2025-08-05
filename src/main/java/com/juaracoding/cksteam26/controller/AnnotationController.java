package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 05/08/25 02.54
@Last Modified 05/08/25 02.54
Version 1.0
*/

import com.juaracoding.cksteam26.dto.validasi.ValAnnotationDTO;
import com.juaracoding.cksteam26.service.AnnotationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("annotation")
public class AnnotationController {

    @Autowired
    AnnotationService annotationService;

    @PostMapping
    public Object save(@Valid @RequestBody ValAnnotationDTO valAnnotationDTO,
                       HttpServletRequest request) {
        return annotationService.save(valAnnotationDTO, request);
    }
}
