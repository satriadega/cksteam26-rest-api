package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 03/08/25 12.26
@Last Modified 03/08/25 12.26
Version 1.0
*/

import com.juaracoding.cksteam26.service.DocumentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class PublicController {

    @Autowired
    DocumentService documentService;

    @GetMapping("/document")
    public Object searchByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") Integer page,
            HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        return documentService.searchByKeyword(keyword, pageable, request);
    }

    @GetMapping("document/{id}")
    public ResponseEntity<Object> findByIdDocument(
            @PathVariable Long id,
            HttpServletRequest request) {
        return documentService.findById(id, request);
    }
}
