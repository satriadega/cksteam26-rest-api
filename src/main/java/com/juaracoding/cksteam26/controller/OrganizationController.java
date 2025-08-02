package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 26/07/25 05.05
@Last Modified 26/07/25 05.05
Version 1.0
*/

import com.juaracoding.cksteam26.dto.validasi.ValCreateOrganizationDTO;
import com.juaracoding.cksteam26.service.UserOrganizationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @PutMapping("/{id}")
//    public Object update(
//            @PathVariable Long id,
//            @Valid @RequestBody ValOrganizationDTO valOrganizationDTO,
//            HttpServletRequest request) {
//        return organizationService.update(id, organizationService.mapToModelMapper(valOrganizationDTO), request);
//    }
//
//    @DeleteMapping("/{id}")
//    public Object delete(
//            @PathVariable Long id,
//            HttpServletRequest request) {
//        return organizationService.delete(id, request);
//    }
//
//    @GetMapping("/{id}")
//    public Object findById(
//            @PathVariable Long id,
//            HttpServletRequest request) {
//        return organizationService.findById(id, request);
//    }

}
