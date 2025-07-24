package com.juaracoding.cksteam26.repo;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 23/07/25 03.16
@Last Modified 23/07/25 03.16
Version 1.0
*/

import com.juaracoding.cksteam26.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepo extends JpaRepository<Document, Long> {

    @Query("""
            select d
            from Document d
            left join Annotation a on a.document = d
            left join Tag t on t.annotation = a
            where lower(d.title) like lower(concat('%', :keyword, '%'))
               or lower(a.selectedText) like lower(concat('%', :keyword, '%'))
               or lower(a.description) like lower(concat('%', :keyword, '%'))
               or lower(t.tagName) like lower(concat('%', :keyword, '%'))
            """)
    Page<Document> searchDocumentsByKeyword(String keyword, Pageable pageable);


}
