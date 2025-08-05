package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 05/08/25 11.53
@Last Modified 05/08/25 11.53
Version 1.0
*/

import com.juaracoding.cksteam26.model.ListApplianceDocumentVerifier;
import com.juaracoding.cksteam26.service.ListApplianceDocumentVerifierService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appliance")
public class ListApplianceVerifierController {

    @Autowired
    ListApplianceDocumentVerifierService listApplianceDocumentVerifierService;

    @PostMapping("/{id}")
    public Object save(@PathVariable Long id,
                       HttpServletRequest request) {
        ListApplianceDocumentVerifier model = listApplianceDocumentVerifierService.mapToModelMapper(id);
        return listApplianceDocumentVerifierService.save(model, request);

    }
}
