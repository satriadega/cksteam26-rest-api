package com.juaracoding.cksteam26.service;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 05/08/25 11.58
@Last Modified 05/08/25 11.58
Version 1.0
*/

import com.juaracoding.cksteam26.core.IService;
import com.juaracoding.cksteam26.dto.response.RespListApplianceVerifierDTO;
import com.juaracoding.cksteam26.model.*;
import com.juaracoding.cksteam26.repo.*;
import com.juaracoding.cksteam26.security.TokenExtractor;
import com.juaracoding.cksteam26.util.GlobalResponse;
import com.juaracoding.cksteam26.util.LoggingFile;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
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

    @Autowired
    AnnotationRepo annotationRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseEntity<Object> save(ListApplianceDocumentVerifier listApplianceDocumentVerifier, HttpServletRequest request) {
        try {
            String username = tokenExtractor.extractUsernameFromRequest(request);
            if (username == null) {
                return GlobalResponse.customError("DOC05FV001", "Unauthorized", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.customError("DOC05FV002", "Unauthorized", request);
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

            return GlobalResponse.dataSavedSuccessfully(null, request);

        } catch (Exception e) {
            LoggingFile.logException(getClass().getSimpleName(), "save", e);
            return GlobalResponse.serverError("DOC05FE001", request);
        }
    }

    @Override
    public ResponseEntity<Object> update(Long id, ListApplianceDocumentVerifier listApplianceDocumentVerifier, HttpServletRequest request) {
        return null;
    }


    public ResponseEntity<Object> update(ListApplianceDocumentVerifier model, HttpServletRequest request) {
        try {
            System.out.println(model.getIsAccepted());
            System.out.println(model.getUserId());
            String username = tokenExtractor.extractUsernameFromRequest(request);
            if (username == null || username.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV021", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV022", request);
            }
            Long userId = userOpt.get().getId();

            Optional<UserDocumentPosition> udpOpt = userDocumentPositionRepo.findByDocumentIdAndUserIdAndPosition(
                    model.getDocumentId(), userId, "OWNER");

            if (udpOpt.isEmpty()) {
                return GlobalResponse.customError("DOC05FV023", "Forbidden", request);
            }

            Optional<ListApplianceDocumentVerifier> existingOpt = listApplianceDocumentVerifierRepo.findByDocumentId(model.getDocumentId());
            if (existingOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV024", request);
            }
            if (Boolean.TRUE.equals(model.getAccepted())) {
                ListApplianceDocumentVerifier result = listApplianceDocumentVerifierRepo.save(model);

                Document document = new Document();
                document.setId(model.getDocumentId());

                User user = new User();
                user.setId(model.getUserId());

                UserDocumentPosition newUdp = new UserDocumentPosition();
                newUdp.setDocument(document);
                newUdp.setUser(user);
                newUdp.setPosition("VERIFIER");
                newUdp.setIsVerified(false);

                userDocumentPositionRepo.save(newUdp);

                annotationRepo.setIsVerifiedFalseByDocumentId(model.getDocumentId());

                return GlobalResponse.dataSavedSuccessfully(result, request);
            } else {
                listApplianceDocumentVerifierRepo.deleteByDocumentIdAndUserId(model.getDocumentId(), model.getUserId());
                return GlobalResponse.dataIsDeleted(request);
            }

        } catch (Exception e) {
            LoggingFile.logException("ListApplianceDocumentVerifierService", "update", e);
            return GlobalResponse.serverError("DOC05FE026", request);
        }
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
    public ResponseEntity<Object> findById(Long documentId, HttpServletRequest request) {
        if (documentId == null || documentId == 0) {
            return GlobalResponse.objectNull("DOC05FV011", request);
        }

        try {
            String username = tokenExtractor.extractUsernameFromRequest(request);
            Long userId = null;
            if (username == null || username.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV012", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV013", request);
            }
            userId = userOpt.get().getId();

            Optional<ListApplianceDocumentVerifier> verifierOpt = listApplianceDocumentVerifierRepo.findFirstByDocumentId(documentId);
            if (verifierOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV014", request);
            }

            RespListApplianceVerifierDTO dto = mapToModelMapper(verifierOpt.get());

            return GlobalResponse.dataIsFound(dto, request);

        } catch (Exception e) {
            LoggingFile.logException(getClass().getSimpleName(), "findByDocumentId", e);
            return GlobalResponse.serverError("DOC05FE011", request);
        }
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

    public ListApplianceDocumentVerifier mapToModelMapper(Long documentId, Long userId, Boolean isAccepted) {
        ListApplianceDocumentVerifier entity = new ListApplianceDocumentVerifier();
        entity.setDocumentId(documentId);
        entity.setUserId(userId);
        entity.setIsAccepted(isAccepted);
        return entity;
    }


    private RespListApplianceVerifierDTO mapToModelMapper(ListApplianceDocumentVerifier verifier) {
        return modelMapper.map(verifier, RespListApplianceVerifierDTO.class);
    }

}
