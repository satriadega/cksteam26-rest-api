package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 23/07/25 03.10
@Last Modified 23/07/25 03.10
Version 1.0
*/

import com.juaracoding.cksteam26.dto.ValDocumentDTO;
import com.juaracoding.cksteam26.service.DocumentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("document")
public class DocumentController {

    private static final int PAGE_SIZE = 10;

    @Autowired
    DocumentService documentService;

    @GetMapping
    public Object findAll(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id"));
        return documentService.findAll(pageable, request);
    }

    @PostMapping
    public Object save(@Valid @RequestBody ValDocumentDTO valDocumentDTO,
                       HttpServletRequest request) {
        return documentService.save(documentService.mapToModelMapper(valDocumentDTO), request);
    }

//    @GetMapping("/{sort}/{sort-by}/{page}")
//    public Object findByParam(
//            @PathVariable Integer page,
//            @PathVariable(value = "sort-by") String sortBy,
//            @PathVariable String sort,
//            @RequestParam String column,
//            @RequestParam String value,
//            HttpServletRequest request) {
//        Pageable pageable;
//        String resolvedSortBy = resolveSortBy(sortBy);
//        if ("asc".equalsIgnoreCase(sort)) {
//            pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(resolvedSortBy));
//        } else {
//            pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(resolvedSortBy).descending());
//        }
//        return documentService.findByParam(pageable, column, value, request);
//    }
//
//
//    private String resolveSortBy(String input) {
//        if (input == null) return "id";
//        switch (input.toLowerCase()) {
//            case "nama":
//                return "nama";
//            case "deskripsi":
//                return "deskripsi";
//            default:
//                return "id";
//        }
//    }

    @GetMapping("/search")
    public Object searchByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") Integer page,
            HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        return documentService.searchByKeyword(keyword, pageable, request);
    }
}
