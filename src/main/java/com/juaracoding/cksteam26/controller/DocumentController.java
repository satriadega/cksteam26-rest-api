package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 23/07/25 03.10
@Last Modified 23/07/25 03.10
Version 1.0
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juaracoding.cksteam26.config.MainConfig;
import com.juaracoding.cksteam26.dto.validasi.ValDocumentDTO;
import com.juaracoding.cksteam26.service.DocumentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("document")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    // PROTOTYPE
    // @GetMapping
    // public Object findAll(HttpServletRequest request) {
    // Pageable pageable = PageRequest.of(0, 2, Sort.by("id"));
    // return documentService.findAll(pageable, request);
    // }

    @PostMapping
    public Object save(@Valid @RequestBody ValDocumentDTO valDocumentDTO,
            HttpServletRequest request) {
        return documentService.save(documentService.mapToModelMapper(valDocumentDTO), request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    public Object findByParam(
            @PathVariable Integer page,
            @PathVariable(value = "sort-by") String sortBy,
            @PathVariable String sort,
            @RequestParam(required = false) String column,
            @RequestParam(required = false) String value,
            HttpServletRequest request) {

        Pageable pageable;
        String resolvedSortBy = resolveSortBy(sortBy);

        if ("asc".equalsIgnoreCase(sort)) {
            pageable = PageRequest.of(page, MainConfig.PAGE_SIZE, Sort.by(resolvedSortBy));
        } else {
            pageable = PageRequest.of(page, MainConfig.PAGE_SIZE, Sort.by(resolvedSortBy).descending());
        }

        return documentService.findByParam(pageable, column, value, request);
    }

    private String resolveSortBy(String input) {
        if (input == null)
            return "id";
        switch (input.toLowerCase()) {
            case "title":
                return "title";
            case "isverifiedall":
                return "isVerifiedAll";
            case "publicvisibility":
                return "publicVisibility";
            case "referencedocumentid":
                return "referenceDocumentId";
            case "version":
                return "version";
            case "subversion":
                return "subversion";
            default:
                return "id";
        }
    }
}
