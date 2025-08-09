package com.juaracoding.cksteam26.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.juaracoding.cksteam26.model.Document;

public interface DocumentRepo extends JpaRepository<Document, Long> {

    @Query("""
                select d
                from Document d
                left join Annotation a on a.document = d
                left join Tag t on t.annotation = a
                left join UserDocumentPosition udp on udp.document = d
                left join UserOrganization uo on uo.userId = :userId
                left join UserOrganization ownerOrg on ownerOrg.userId = udp.user.id
                where (
                    lower(d.title) like lower(concat('%', :keyword, '%'))
                    or lower(a.selectedText) like lower(concat('%', :keyword, '%'))
                    or lower(a.description) like lower(concat('%', :keyword, '%'))
                    or lower(t.tagName) like lower(concat('%', :keyword, '%'))
                )
                and (
                    d.publicVisibility = true
                    or (:userId is not null and udp.user.id = :userId)
                    or (:userId is not null and uo.organizationId = ownerOrg.organizationId and d.isPrivate != true)
                )
            """)
    Page<Document> searchDocumentsByKeyword(@Param("keyword") String keyword,
            @Param("userId") Long userId,
            Pageable pageable);

    @Query("""
                select d
                from Document d
                left join UserDocumentPosition udp on udp.document = d
                left join UserOrganization uo on uo.userId = :userId
                left join UserOrganization ownerOrg on ownerOrg.userId = udp.user.id
                where (
                    d.publicVisibility = true
                    or (:userId is not null and udp.user.id = :userId)
                    or (:userId is not null and uo.organizationId = ownerOrg.organizationId and d.isPrivate != true)
                )
            """)
    Page<Document> findAllVisibleDocuments(@Param("userId") Long userId, Pageable pageable);

    @Query("""
                SELECT d FROM Document d
                 LEFT JOIN UserDocumentPosition udp ON udp.document.id = d.id
                 LEFT JOIN UserOrganization uo ON uo.userId = udp.user.id
                 left join UserOrganization ownerOrg on ownerOrg.userId = udp.user.id
                 WHERE d.id = :documentId
                 AND (
                     d.publicVisibility = true
                     or (:userId is not null and udp.user.id = :userId)
                     or (:userId is not null and uo.organizationId = ownerOrg.organizationId and d.isPrivate != true)
                 )
            """)
    Optional<Document> findAccessibleDocumentById(@Param("documentId") Long documentId, @Param("userId") Long userId);

    Page<Document> findByTitleContainsIgnoreCase(String title, Pageable pageable);

    Page<Document> findByContentContainsIgnoreCase(String content, Pageable pageable);

    Page<Document> findByIsVerifiedAll(Boolean isVerifiedAll, Pageable pageable);

    Page<Document> findByPublicVisibility(Boolean publicVisibility, Pageable pageable);

    Page<Document> findByReferenceDocumentId(Long referenceDocumentId, Pageable pageable);

    Page<Document> findByVersion(Integer version, Pageable pageable);

    Page<Document> findBySubversion(Integer subversion, Pageable pageable);

    Optional<Document> findTopByIdOrderByVersionDescSubversionDesc(Long referenceDocumentId);

    @Query("""
                SELECT d
                FROM Document d
                WHERE d.referenceDocumentId = :referenceId
                ORDER BY d.version DESC, d.subversion DESC
            """)
    List<Document> findTopDocumentsByReferenceDocumentId(@Param("referenceId") Long referenceId, Pageable pageable);

    @Query("""
                select d
                from Document d
                left join UserDocumentPosition udp on udp.document = d
                left join UserOrganization uo on uo.userId = :userId
                left join UserOrganization ownerOrg on ownerOrg.userId = udp.user.id
                where d.referenceDocumentId = :referenceDocumentId
                and (
                    d.publicVisibility = true
                    or (:userId is not null and udp.user.id = :userId)
                    or (:userId is not null and uo.organizationId = ownerOrg.organizationId and d.isPrivate != true)
                )
            """)
    List<Document> findRelatedDocumentsById(@Param("referenceDocumentId") Long referenceDocumentId,
            @Param("userId") Long userId);
}
