package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 05/08/25 02.54
@Last Modified 05/08/25 02.54
Version 1.0
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juaracoding.cksteam26.config.MainConfig;
import com.juaracoding.cksteam26.dto.validasi.ValAnnotationDTO;
import com.juaracoding.cksteam26.service.AnnotationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

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

    @GetMapping("/verifier")
    public Object findAllByVerifier(HttpServletRequest request) {
        return annotationService.findAllByVerifier(request);
    }

    @PutMapping("/verifier/{annotationId}")
    public Object updateAnnotationStatus(
            @PathVariable Long annotationId,
            @RequestParam String action,
            HttpServletRequest request) {
        return annotationService.updateAnnotationStatus(annotationId, action, request);
    }

    @GetMapping("/{sort}/{sortBy}/{page}")
    public Object findByParam(
            @PathVariable Integer page,
            @PathVariable("sortBy") String sortBy,
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

        return annotationService.findByParam(pageable, column, value, request);
    }

    private String resolveSortBy(String input) {
        if (input == null)
            return "id";
        switch (input.toLowerCase()) {
            case "selectedtext":
                return "selectedText";
            case "description":
                return "description";
            case "createdat":
                return "createdAt";
            default:
                return "id";
        }
    }
}
