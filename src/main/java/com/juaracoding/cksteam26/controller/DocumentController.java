package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 23/07/25 03.10
@Last Modified 23/07/25 03.10
Version 1.0
*/

import com.juaracoding.cksteam26.service.DocumentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("document")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @GetMapping
    public Object findAll(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(1, 2, Sort.by("id").descending());
        return documentService.findAll(pageable, request);
    }
}
