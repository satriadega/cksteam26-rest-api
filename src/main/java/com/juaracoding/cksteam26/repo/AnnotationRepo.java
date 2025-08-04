package com.juaracoding.cksteam26.repo;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 04/08/25 12.39
@Last Modified 04/08/25 12.39
Version 1.0
*/

import com.juaracoding.cksteam26.model.Annotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnotationRepo extends JpaRepository<Annotation, Long> {
    @Query("select a from Annotation a left join fetch a.tags where a.document.id = :documentId")
    List<Annotation> findByDocumentIdWithTags(@Param("documentId") Long documentId);
}

