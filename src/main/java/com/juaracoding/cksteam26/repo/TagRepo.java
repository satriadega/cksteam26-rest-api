package com.juaracoding.cksteam26.repo;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 04/08/25 12.59
@Last Modified 04/08/25 12.59
Version 1.0
*/

import com.juaracoding.cksteam26.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepo extends JpaRepository<Tag, Long> {

    @Query("select t from Tag t join t.annotation a where a.document.id in :docIds")
    List<Tag> findTagsByDocumentIds(@Param("docIds") List<Long> docIds);

    @Query("select t from Tag t join t.annotation a where a.document.id = :documentId")
    List<Tag> findTagsByDocumentId(@Param("documentId") Long documentId);
}

