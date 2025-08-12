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
            select  d
            from Document d
            left join Annotation a on a.document = d
            left join Tag t on t.annotation = a
            left join UserDocumentPosition udp on udp.document = d
            where (
                lower(d.title) like lower(concat('%', :keyword, '%'))
                or lower(a.selectedText) like lower(concat('%', :keyword, '%'))
                or lower(a.description) like lower(concat('%', :keyword, '%'))
                or lower(t.tagName) like lower(concat('%', :keyword, '%'))
            )
            and
            (
                (:userId IS NOT NULL AND udp.user.id = :userId)
                OR
                (d.isPrivate = false AND (
                    :userId IS NULL
                    OR
                    (:userId IS NOT NULL AND EXISTS (
                       SELECT 1
                       FROM UserOrganization uo_viewer
                       JOIN UserOrganization uo_owner ON uo_viewer.organizationId = uo_owner.organizationId
                       WHERE uo_viewer.userId = :userId AND uo_owner.userId = udp.user.id
                    ))
                ))
            )
            """)
    Page<Document> searchDocumentsByKeyword(@Param("keyword") String keyword,
            @Param("userId") Long userId,
            Pageable pageable);

    @Query("""
            select  d
            from Document d
            left join UserDocumentPosition udp on udp.document = d
            where
            (
                (:userId IS NOT NULL AND udp.user.id = :userId)
                OR
                (d.isPrivate = false AND (
                    :userId IS NULL
                    OR
                    (:userId IS NOT NULL AND EXISTS (
                       SELECT 1
                       FROM UserOrganization uo_viewer
                       JOIN UserOrganization uo_owner ON uo_viewer.organizationId = uo_owner.organizationId
                       WHERE uo_viewer.userId = :userId AND uo_owner.userId = udp.user.id
                    ))
                ))
            )
            """)
    Page<Document> findAllVisibleDocuments(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT d FROM Document d
             LEFT JOIN UserDocumentPosition udp ON udp.document.id = d.id
             WHERE d.id = :documentId
             AND (
                (:userId IS NOT NULL AND udp.user.id = :userId)
                OR
                (d.isPrivate = false AND (
                    :userId IS NULL
                    OR
                    (:userId IS NOT NULL AND EXISTS (
                       SELECT 1
                       FROM UserOrganization uo_viewer
                       JOIN UserOrganization uo_owner ON uo_viewer.organizationId = uo_owner.organizationId
                       WHERE uo_viewer.userId = :userId AND uo_owner.userId = udp.user.id
                    ))
                ))
             )
            """)
    Optional<Document> findAccessibleDocumentById(@Param("documentId") Long documentId, @Param("userId") Long userId);

    @Query("""
                SELECT d
                FROM Document d
                WHERE d.referenceDocumentId = :referenceId
                ORDER BY d.version DESC, d.subversion DESC
            """)
    List<Document> findTopDocumentsByReferenceDocumentId(@Param("referenceId") Long referenceId, Pageable pageable);

    @Query("""
            SELECT d FROM Document d
             LEFT JOIN UserDocumentPosition udp ON udp.document.id = d.id
             WHERE d.referenceDocumentId = :referenceDocumentId
             AND (
                (:userId IS NOT NULL AND udp.user.id = :userId)
                OR
                (d.isPrivate = false AND (
                    :userId IS NULL
                    OR
                    (:userId IS NOT NULL AND EXISTS (
                       SELECT 1
                       FROM UserOrganization uo_viewer
                       JOIN UserOrganization uo_owner ON uo_viewer.organizationId = uo_owner.organizationId
                       WHERE uo_viewer.userId = :userId AND uo_owner.userId = udp.user.id
                    ))
                ))
             )
            """)
    List<Document> findRelatedDocumentsByReferenceId(@Param("referenceDocumentId") Long referenceDocumentId,
            @Param("userId") Long userId);

    Page<Document> findByTitleContainsIgnoreCaseAndIdIn(String value, List<Long> documentIds, Pageable pageable);

    Page<Document> findByContentContainsIgnoreCaseAndIdIn(String value, List<Long> documentIds, Pageable pageable);

    Page<Document> findByIsVerifiedAllAndIdIn(boolean b, List<Long> documentIds, Pageable pageable);

    Page<Document> findByPublicVisibilityAndIdIn(boolean b, List<Long> documentIds, Pageable pageable);

    Page<Document> findByReferenceDocumentIdAndIdIn(long l, List<Long> documentIds, Pageable pageable);

    Page<Document> findByVersionAndIdIn(int i, List<Long> documentIds, Pageable pageable);

    Page<Document> findBySubversionAndIdIn(int i, List<Long> documentIds, Pageable pageable);

    Page<Document> findByIdIn(List<Long> documentIds, Pageable pageable);

    Page<Document> findByIsAnnotableAndIdIn(boolean b, List<Long> documentIds, Pageable pageable);

    Page<Document> findByIsPrivateAndIdIn(boolean b, List<Long> documentIds, Pageable pageable);

    @Query("""
            select distinct d.id
            from Document d
            left join UserDocumentPosition udp on udp.document = d
            where
            (
                (:userId IS NOT NULL AND udp.user.id = :userId)
                OR
                (d.isPrivate = false AND (
                    :userId IS NULL
                    OR
                    (:userId IS NOT NULL AND EXISTS (
                       SELECT 1
                       FROM UserOrganization uo_viewer
                       JOIN UserOrganization uo_owner ON uo_viewer.organizationId = uo_owner.organizationId
                       WHERE uo_viewer.userId = :userId AND uo_owner.userId = udp.user.id
                    ))
                ))
            )
            """)
    List<Long> findVisibleDocumentIds(@Param("userId") Long userId);

}
