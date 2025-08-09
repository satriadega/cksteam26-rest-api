package com.juaracoding.cksteam26.repo;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 04/08/25 12.39
@Last Modified 04/08/25 12.39
Version 1.0
*/

import com.juaracoding.cksteam26.model.Annotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnotationRepo extends JpaRepository<Annotation, Long> {
    @Query("select a from Annotation a left join fetch a.tags where a.document.id = :documentId")
    List<Annotation> findByDocumentIdWithTags(@Param("documentId") Long documentId);

    long countByDocumentId(@Param("documentId") Long documentId);

    Page<Annotation> findByOwnerUserId(Long ownerUserId, Pageable pageable);

    Page<Annotation> findBySelectedTextContainingIgnoreCaseAndOwnerUserId(String selectedText, Long ownerUserId, Pageable pageable);

    Page<Annotation> findByDescriptionContainingIgnoreCaseAndOwnerUserId(String description, Long ownerUserId, Pageable pageable);

    Page<Annotation> findByIsVerifiedAndOwnerUserId(Boolean isVerified, Long ownerUserId, Pageable pageable);

    Page<Annotation> findByStartNoAndOwnerUserId(Integer startNo, Long ownerUserId, Pageable pageable);

    Page<Annotation> findByEndNoAndOwnerUserId(Integer endNo, Long ownerUserId, Pageable pageable);

    @Modifying
    @Query("UPDATE Annotation a SET a.isVerified = false WHERE a.document.id = :documentId")
    void setIsVerifiedFalseByDocumentId(@Param("documentId") Long documentId);
}
