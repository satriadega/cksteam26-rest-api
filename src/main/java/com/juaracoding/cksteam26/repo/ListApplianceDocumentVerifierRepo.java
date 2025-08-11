package com.juaracoding.cksteam26.repo;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 05/08/25 12.14
@Last Modified 05/08/25 12.14
Version 1.0
*/

import com.juaracoding.cksteam26.model.ListApplianceDocumentVerifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface ListApplianceDocumentVerifierRepo extends JpaRepository<ListApplianceDocumentVerifier, Long> {

    Optional<ListApplianceDocumentVerifier> findFirstByDocumentId(Long documentId);

    Optional<ListApplianceDocumentVerifier> findByDocumentId(Long documentId);

    @Modifying
    void deleteByDocumentIdAndUserId(Long documentId, Long userId);

    Page<ListApplianceDocumentVerifier> findAllByOwnerDocumentUserId(Long ownerDocumentUserId, Pageable pageable);

    Page<ListApplianceDocumentVerifier> findAllByOwnerDocumentUserIdAndIsAccepted(Long ownerDocumentUserId, Boolean isAccepted, Pageable pageable);

    Optional<ListApplianceDocumentVerifier> findByDocumentIdAndUserId(Long documentId, Long userId);

}
