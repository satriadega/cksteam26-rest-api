package com.juaracoding.cksteam26.repo;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 03/08/25 11.40
@Last Modified 03/08/25 11.40
Version 1.0
*/

import com.juaracoding.cksteam26.model.UserDocumentPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDocumentPositionRepo extends JpaRepository<UserDocumentPosition, Long> {

    Optional<UserDocumentPosition> findByUserIdAndDocumentId(Long userId, Long documentId);

}
