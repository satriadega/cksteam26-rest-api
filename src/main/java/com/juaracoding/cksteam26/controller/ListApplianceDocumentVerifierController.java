package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 05/08/25 11.53
@Last Modified 05/08/25 11.53
Version 1.0
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juaracoding.cksteam26.dto.validasi.ValUpdateApplianceVerifierDTO;
import com.juaracoding.cksteam26.model.ListApplianceDocumentVerifier;
import com.juaracoding.cksteam26.service.ListApplianceDocumentVerifierService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("appliance")
public class ListApplianceDocumentVerifierController {

    @Autowired
    ListApplianceDocumentVerifierService listApplianceDocumentVerifierService;

    @PostMapping("/{id}")
    public Object save(@PathVariable Long id,
            HttpServletRequest request) {
        ListApplianceDocumentVerifier model = listApplianceDocumentVerifierService.mapToModelMapper(id);
        return listApplianceDocumentVerifierService.save(model, request);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findByIdDocument(
            @PathVariable Long id,
            HttpServletRequest request) {
        ResponseEntity<Object> response = listApplianceDocumentVerifierService.findById(id, request);
        return response;
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<Object> update(
            @PathVariable Long documentId,
            @Valid @RequestBody ValUpdateApplianceVerifierDTO valUpdateApplianceDTO,
            HttpServletRequest request) {

        return listApplianceDocumentVerifierService.update(documentId, valUpdateApplianceDTO, request);
    }

    @GetMapping
    public ResponseEntity<Object> getAllListApplianceVerifier(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        return listApplianceDocumentVerifierService.findAllPendingApplianceByOwnerDocumentUserId(page, size, request);
    }

}
