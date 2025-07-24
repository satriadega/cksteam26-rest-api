package com.juaracoding.cksteam26.repo;

import com.juaracoding.cksteam26.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepo extends JpaRepository<Document, Long> {

    @Query("""
            select distinct d
            from Document d
            left join Annotation a on a.document = d
            left join Tag t on t.annotation = a
            where lower(d.title) like lower(concat('%', :keyword, '%'))
               or lower(a.selectedText) like lower(concat('%', :keyword, '%'))
               or lower(a.description) like lower(concat('%', :keyword, '%'))
               or lower(t.tagName) like lower(concat('%', :keyword, '%'))
            """)
    Page<Document> searchDocumentsByKeyword(String keyword, Pageable pageable);

    Page<Document> findByTitleContainsIgnoreCase(String title, Pageable pageable);

    Page<Document> findByContentContainsIgnoreCase(String content, Pageable pageable);

    Page<Document> findByIsVerifiedAll(Boolean isVerifiedAll, Pageable pageable);

    Page<Document> findByPublicVisibility(Boolean publicVisibility, Pageable pageable);

    Page<Document> findByReferenceDocumentId(Long referenceDocumentId, Pageable pageable);

    Page<Document> findByVersion(Integer version, Pageable pageable);

    Page<Document> findBySubversion(Integer subversion, Pageable pageable);

}
