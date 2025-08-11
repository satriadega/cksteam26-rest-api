package com.juaracoding.cksteam26.repo;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 02/08/25 06.44
@Last Modified 02/08/25 06.44
Version 1.0
*/

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juaracoding.cksteam26.model.UserOrganization;
import com.juaracoding.cksteam26.model.UserOrganizationId;

@Repository
public interface UserOrganizationRepo extends JpaRepository<UserOrganization, UserOrganizationId> {

    List<UserOrganization> findByUserId(Long userId);

    List<UserOrganization> findByOrganizationId(Long organizationId);

    Optional<UserOrganization> findByUserIdAndOrganizationId(Long userId, Long organizationId);

    Optional<UserOrganization> findTop1ByOrderByCreatedAtDesc();

    @Modifying
    @Query("UPDATE UserOrganization uo SET uo.userId = NULL WHERE uo.userId = :userId")
    void updateUserIdToNullByUserId(@Param("userId") Long userId);
}
