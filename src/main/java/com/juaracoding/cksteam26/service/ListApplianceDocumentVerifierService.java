package com.juaracoding.cksteam26.service;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 05/08/25 11.58
@Last Modified 05/08/25 11.58
Version 1.0
*/

import com.juaracoding.cksteam26.core.IService;
import com.juaracoding.cksteam26.model.*;
import com.juaracoding.cksteam26.repo.*;
import com.juaracoding.cksteam26.security.TokenExtractor;
import com.juaracoding.cksteam26.util.GlobalResponse;
import com.juaracoding.cksteam26.util.LoggingFile;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class ListApplianceDocumentVerifierService implements IService<ListApplianceDocumentVerifier> {

    @Autowired
    UserRepo userRepo;

    @Autowired
    TokenExtractor tokenExtractor;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    ListApplianceDocumentVerifierRepo listApplianceDocumentVerifierRepo;

    @Autowired
    NotificationRepo notificationRepo;

    @Autowired
    UserDocumentPositionRepo userDocumentPositionRepo;

    @Override
    public ResponseEntity<Object> save(ListApplianceDocumentVerifier listApplianceDocumentVerifier, HttpServletRequest request) {
        try {
            String username = tokenExtractor.extractUsernameFromRequest(request);
            if (username == null) {
                return GlobalResponse.customError("DOC05FA001", "Unauthorized", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.customError("DOC05FA002", "Unauthorized", request);
            }

            User user = userOpt.get();
            Long userId = user.getId();

            if (listApplianceDocumentVerifier.getDocumentId() == null) {
                return GlobalResponse.customError("DOC05FV002", "Document ID is required", request);
            }

            Optional<Document> documentOpt = documentRepo.findAccessibleDocumentById(
                    listApplianceDocumentVerifier.getDocumentId(), userId
            );

            if (documentOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV001", request);
            }

            Document document = documentOpt.get();

            if (!Boolean.TRUE.equals(document.getAnnotable())) {
                return GlobalResponse.customError("DOC05FV003", "Document is not annotable", request);
            }

            Optional<UserDocumentPosition> userDocumentPositionOpt = userDocumentPositionRepo.findByDocumentIdAndPosition(
                    listApplianceDocumentVerifier.getDocumentId(), "OWNER"
            );

            if (userDocumentPositionOpt.isPresent()) {
                User ownerUser = userDocumentPositionOpt.get().getUser();

                if (ownerUser.getId().equals(userId)) {
                    return GlobalResponse.customError("DOC05FV004", "Owner cannot do this request", request);
                }

                ownerUser.setUpdatedAt(new Date());
                ownerUser.setHasNotification(true);

                Integer notifType = ownerUser.getNotificationType();
                if (notifType == null || notifType == 0 || notifType == 1) {
                    ownerUser.setNotificationType(1);
                } else {
                    ownerUser.setNotificationType(3);
                }

                Integer notifCounter = ownerUser.getNotificationCounter();
                ownerUser.setNotificationCounter((notifCounter == null ? 1 : notifCounter + 1));

                userRepo.save(ownerUser);
            }

            listApplianceDocumentVerifier.setId(null);
            listApplianceDocumentVerifier.setCreatedAt(new Date());
            listApplianceDocumentVerifier.setDocumentId(document.getReferenceDocumentId());
            listApplianceDocumentVerifier.setUserId(userId);
            listApplianceDocumentVerifier.setIsAccepted(false);

            listApplianceDocumentVerifierRepo.save(listApplianceDocumentVerifier);

            Notification notification = new Notification();
            notification.setUser(user);
            notification.setIsRead(false);
            notification.setType("APPLIANCE");
            notification.setCreatedAt(new Date());
            notification.setId(null);

            notificationRepo.save(notification);

            return GlobalResponse.dataSavedSuccessfully(request);

        } catch (Exception e) {
            LoggingFile.logException(getClass().getSimpleName(), "save", e);
            return GlobalResponse.serverError("DOC05FE001", request);
        }
    }


    @Override
    public ResponseEntity<Object> update(Long id, ListApplianceDocumentVerifier listApplianceDocumentVerifier, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        return null;
    }

    public ListApplianceDocumentVerifier mapToModelMapper(Long id) {
        ListApplianceDocumentVerifier entity = new ListApplianceDocumentVerifier();
        entity.setDocumentId(id);
        return entity;
    }

}
