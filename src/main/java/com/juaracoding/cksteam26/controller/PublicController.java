package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 03/08/25 12.26
@Last Modified 03/08/25 12.26
Version 1.0
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juaracoding.cksteam26.service.DocumentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("public")
public class PublicController {

    @Autowired
    DocumentService documentService;

    @GetMapping("/document")
    public Object searchByKeyword(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") Integer page,
            HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        return documentService.searchByKeyword(keyword, pageable, request);
    }

    @GetMapping("document/{id}")
    public ResponseEntity<Object> findByIdDocument(
            @PathVariable Long id,
            HttpServletRequest request) {
        ResponseEntity<Object> response = documentService.findById(id, request);
        return response;
    }

    @GetMapping("/document/related/{id}")
    public ResponseEntity<Object> findRelatedDocuments(
            @PathVariable("id") Long referenceDocumentId,
            HttpServletRequest request) {
        return documentService.findRelatedDocuments(referenceDocumentId, request);
    }
}
