package com.juaracoding.cksteam26.repo;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 26/07/25 05.17
@Last Modified 26/07/25 05.17
Version 1.0
*/

import com.juaracoding.cksteam26.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepo extends JpaRepository<Organization, Long> {
}
