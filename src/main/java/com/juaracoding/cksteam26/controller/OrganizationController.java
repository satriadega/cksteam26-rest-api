package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 26/07/25 05.05
@Last Modified 26/07/25 05.05
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
import org.springframework.web.bind.annotation.RestController;

import com.juaracoding.cksteam26.dto.validasi.ValCreateOrganizationDTO;
import com.juaracoding.cksteam26.dto.validasi.ValUserOrganizationDTO;
import com.juaracoding.cksteam26.service.UserOrganizationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("organization")
public class OrganizationController {

    @Autowired
    UserOrganizationService userOrganizationService;

    @GetMapping
    public Object findAll(HttpServletRequest request) {
        return userOrganizationService.findAllWithoutPagination(request);
    }

    @PostMapping()
    public ResponseEntity<Object> registration(@Valid @RequestBody ValCreateOrganizationDTO valCreateOrganizationDTO
            , HttpServletRequest request

    ) {
        return userOrganizationService.saveOrganizationWithMembers(valCreateOrganizationDTO, request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable Long id,
            @Valid @RequestBody ValUserOrganizationDTO valUserOrganizationDTO,
            HttpServletRequest request) {
        return userOrganizationService.updateOrganization(id, valUserOrganizationDTO, request);
    }
//
//    @DeleteMapping("/{id}")
//    public Object delete(
//            @PathVariable Long id,
//            HttpServletRequest request) {
//        return organizationService.delete(id, request);
//    }
//
    @GetMapping("/{id}")
    public Object findById(
            @PathVariable Long id,
            HttpServletRequest request) {
        return userOrganizationService.findById(id, request);
    }

}
