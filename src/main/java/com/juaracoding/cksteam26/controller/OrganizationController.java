package com.juaracoding.cksteam26.controller;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 26/07/25 05.05
@Last Modified 26/07/25 05.05
Version 1.0
*/

import com.juaracoding.cksteam26.service.OrganizationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("organization")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @GetMapping
    public Object findAll(HttpServletRequest request) {
        return organizationService.findAllWithoutPagination(request);
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
