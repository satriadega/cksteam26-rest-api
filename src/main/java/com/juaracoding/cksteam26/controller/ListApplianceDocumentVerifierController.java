package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 05/08/25 11.53
@Last Modified 05/08/25 11.53
Version 1.0
*/

import com.juaracoding.cksteam26.dto.validasi.ValUpdateApplianceVerifierDTO;
import com.juaracoding.cksteam26.model.ListApplianceDocumentVerifier;
import com.juaracoding.cksteam26.service.ListApplianceDocumentVerifierService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        ListApplianceDocumentVerifier model = listApplianceDocumentVerifierService.mapToModelMapper(documentId, valUpdateApplianceDTO.getUserId(), valUpdateApplianceDTO.getIsAccepted());
        return listApplianceDocumentVerifierService.update(model, request);
    }
}
