package com.juaracoding.cksteam26.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juaracoding.cksteam26.model.AnnotationVerification;

@Repository
public interface AnnotationVerificationRepo extends JpaRepository<AnnotationVerification, Long> {
    Optional<AnnotationVerification> findByAnnotationIdAndUsername(Long annotationId, String username);
}
