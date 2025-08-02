package com.juaracoding.cksteam26.repo;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 02/08/25 06.44
@Last Modified 02/08/25 06.44
Version 1.0
*/

import com.juaracoding.cksteam26.model.UserOrganization;
import com.juaracoding.cksteam26.model.UserOrganizationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOrganizationRepo extends JpaRepository<UserOrganization, UserOrganizationId> {

    List<UserOrganization> findByUserId(Long userId);

    List<UserOrganization> findByOrganizationId(Long organizationId);

    Optional<UserOrganization> findTop1ByOrderByCreatedAtDesc();
}
