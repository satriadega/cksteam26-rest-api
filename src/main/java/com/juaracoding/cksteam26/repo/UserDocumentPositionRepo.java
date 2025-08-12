package com.juaracoding.cksteam26.repo;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 03/08/25 11.40
@Last Modified 03/08/25 11.40
Version 1.0
*/

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juaracoding.cksteam26.model.UserDocumentPosition;

@Repository
public interface UserDocumentPositionRepo extends JpaRepository<UserDocumentPosition, Long> {

    Optional<UserDocumentPosition> findByUserIdAndDocumentId(Long userId, Long documentId);

    List<UserDocumentPosition> findAllByUserIdAndDocumentId(Long userId, Long documentId);

    Optional<UserDocumentPosition> findByDocumentIdAndPosition(Long documentId, String position);

    @Query("SELECT udp.document.id FROM UserDocumentPosition udp WHERE udp.user.id = :userId")
    List<Long> findDocumentIdsByUserId(@Param("userId") Long userId);

    Optional<UserDocumentPosition> findByDocumentIdAndUserIdAndPosition(Long documentId, Long userId, String position);

}
