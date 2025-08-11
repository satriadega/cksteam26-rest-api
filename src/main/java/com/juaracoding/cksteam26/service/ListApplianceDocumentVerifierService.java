package com.juaracoding.cksteam26.service;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 05/08/25 11.58
@Last Modified 05/08/25 11.58
Version 1.0
*/

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juaracoding.cksteam26.core.IService;
import com.juaracoding.cksteam26.dto.response.RespListApplianceVerifierDTO;
import com.juaracoding.cksteam26.dto.validasi.ValUpdateApplianceVerifierDTO;
import com.juaracoding.cksteam26.model.Document;
import com.juaracoding.cksteam26.model.ListApplianceDocumentVerifier;
import com.juaracoding.cksteam26.model.Notification;
import com.juaracoding.cksteam26.model.User;
import com.juaracoding.cksteam26.model.UserDocumentPosition;
import com.juaracoding.cksteam26.repo.AnnotationRepo;
import com.juaracoding.cksteam26.repo.DocumentRepo;
import com.juaracoding.cksteam26.repo.ListApplianceDocumentVerifierRepo;
import com.juaracoding.cksteam26.repo.NotificationRepo;
import com.juaracoding.cksteam26.repo.UserDocumentPositionRepo;
import com.juaracoding.cksteam26.repo.UserRepo;
import com.juaracoding.cksteam26.security.TokenExtractor;
import com.juaracoding.cksteam26.util.GlobalResponse;
import com.juaracoding.cksteam26.util.LoggingFile;
import com.juaracoding.cksteam26.util.TransformPagination;

import jakarta.servlet.http.HttpServletRequest;

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

    @Autowired
    TransformPagination transformPagination;

    @Override
    public ResponseEntity<Object> save(ListApplianceDocumentVerifier listApplianceDocumentVerifier,
            HttpServletRequest request) {
        try {
            String username = tokenExtractor.extractUsernameFromRequest(request);
            if (username == null) {
                return GlobalResponse.customError("DOC05FV011", "Unauthorized", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.customError("DOC05FV012", "Unauthorized", request);
            }

            User user = userOpt.get();
            Long userId = user.getId();

            if (listApplianceDocumentVerifier.getDocumentId() == null) {
                return GlobalResponse.customError("DOC05FV013", "Document ID is required", request);
            }

            Optional<Document> documentOpt = documentRepo.findAccessibleDocumentById(
                    listApplianceDocumentVerifier.getDocumentId(), userId);

            if (documentOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV014", request);
            }

            Document document = documentOpt.get();

            if (!Boolean.TRUE.equals(document.getAnnotable())) {
                return GlobalResponse.customError("DOC05FV015", "Document is not annotable", request);
            }

            Optional<UserDocumentPosition> userDocumentPositionOpt = userDocumentPositionRepo
                    .findByDocumentIdAndPosition(
                            listApplianceDocumentVerifier.getDocumentId(), "OWNER");

            User ownerUser = null;

            if (userDocumentPositionOpt.isPresent()) {
                ownerUser = userDocumentPositionOpt.get().getUser();

                if (ownerUser.getId().equals(userId)) {
                    return GlobalResponse.customError("DOC05FV016", "Owner cannot do this request", request);
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
            } else {
                return GlobalResponse.customError("DOC05FV017", "Owner user not found", request);
            }

            listApplianceDocumentVerifier.setId(null);
            listApplianceDocumentVerifier.setCreatedAt(new Date());
            listApplianceDocumentVerifier.setDocumentId(document.getReferenceDocumentId());
            listApplianceDocumentVerifier.setUserId(userId);
            listApplianceDocumentVerifier.setIsAccepted(false);
            listApplianceDocumentVerifier.setOwnerDocumentUserId(ownerUser.getId());

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
            return GlobalResponse.serverError("DOC05FE011", request);
        }
    }

    @Override
    public ResponseEntity<Object> update(Long id, ListApplianceDocumentVerifier listApplianceDocumentVerifier,
            HttpServletRequest request) {
        return null;
    }

    public ResponseEntity<Object> update(Long documentId, ValUpdateApplianceVerifierDTO valUpdateApplianceDTO,
            HttpServletRequest request) {
        try {
            String tokenUsername = tokenExtractor.extractUsernameFromRequest(request);
            if (tokenUsername == null || tokenUsername.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV021", request);
            }

            Optional<User> tokenUserOpt = userRepo.findByUsername(tokenUsername);
            if (tokenUserOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV022", request);
            }
            Long tokenUserId = tokenUserOpt.get().getId();

            Optional<UserDocumentPosition> udpOpt = userDocumentPositionRepo.findByDocumentIdAndUserIdAndPosition(
                    documentId, tokenUserId, "OWNER");
            if (udpOpt.isEmpty()) {
                return GlobalResponse.customError("DOC05FV023", "Forbidden", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(valUpdateApplianceDTO.getUsername());
            if (userOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV025", request);
            }
            Long userId = userOpt.get().getId();

            Optional<ListApplianceDocumentVerifier> existingOpt = listApplianceDocumentVerifierRepo
                    .findByDocumentIdAndUserId(documentId, userId);
            if (existingOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV024", request);
            }
            ListApplianceDocumentVerifier existing = existingOpt.get();

            if (Boolean.TRUE.equals(valUpdateApplianceDTO.getIsAccepted())) {
                existing.setIsAccepted(true);
                existing.setUserId(userId);

                ListApplianceDocumentVerifier updated = listApplianceDocumentVerifierRepo.save(existing);

                Document document = new Document();
                document.setId(documentId);

                User user = new User();
                user.setId(userId);

                UserDocumentPosition newUdp = new UserDocumentPosition();
                newUdp.setDocument(document);
                newUdp.setUser(user);
                newUdp.setPosition("VERIFIER");
                newUdp.setIsVerified(false);

                userDocumentPositionRepo.save(newUdp);

                annotationRepo.setIsVerifiedFalseByDocumentId(documentId);

                return GlobalResponse.dataSavedSuccessfully(updated, request);
            } else {
                if (Boolean.TRUE.equals(existing.getIsAccepted())) {
                    return GlobalResponse.customError("DOC05FV027", "Cannot delete verifier after acceptance", request);
                } else {
                    listApplianceDocumentVerifierRepo.deleteByDocumentIdAndUserId(documentId, userId);
                    return GlobalResponse.dataIsDeleted(request);
                }
            }
        } catch (Exception e) {
            LoggingFile.logException(getClass().getSimpleName(), "update", e);
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
        System.out.println("sampe sini gan!");
        if (documentId == null || documentId == 0) {
            return GlobalResponse.objectNull("DOC05FV051", request);
        }

        try {
            String username = tokenExtractor.extractUsernameFromRequest(request);
            if (username == null || username.isEmpty()) {
                return GlobalResponse.customError("DOC05FV052", "Unauthorized", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.customError("DOC05FV053", "Unauthorized", request);
            }

            Optional<Document> documentOpt = documentRepo.findById(documentId);
            if (documentOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV054", request);
            }
            Document document = documentOpt.get();
            Long referenceDocumentId = document.getReferenceDocumentId();

            Optional<ListApplianceDocumentVerifier> verifierOpt = listApplianceDocumentVerifierRepo
                    .findFirstByDocumentId(referenceDocumentId);
            if (verifierOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV055", request);
            }

            RespListApplianceVerifierDTO dto = mapToModelMapper(verifierOpt.get());

            return GlobalResponse.dataIsFound(dto, request);

        } catch (Exception e) {
            LoggingFile.logException(getClass().getSimpleName(), "findByDocumentId", e);
            return GlobalResponse.serverError("DOC05FE051", request);
        }
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value,
            HttpServletRequest request) {
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

    public ResponseEntity<Object> findAllPendingApplianceByOwnerDocumentUserId(int page, int size,
            HttpServletRequest request) {
        try {
            String username = tokenExtractor.extractUsernameFromRequest(request);
            if (username == null || username.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV071", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV072", request);
            }

            Long ownerUserId = userOpt.get().getId();

            Pageable pageable = PageRequest.of(page, size);
            Page<ListApplianceDocumentVerifier> pageData = listApplianceDocumentVerifierRepo
                    .findAllByOwnerDocumentUserIdAndIsAccepted(ownerUserId, false, pageable);

            if (pageData.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC05FV073", request);
            }

            List<RespListApplianceVerifierDTO> dtoList = pageData.getContent().stream().map(verifier -> {
                RespListApplianceVerifierDTO dto = new RespListApplianceVerifierDTO();
                dto.setDocumentId(verifier.getDocumentId());
                dto.setReferenceDocumentId(verifier.getDocumentId());
                dto.setAccepted(verifier.getIsAccepted());
                dto.setCreatedAt(verifier.getCreatedAt());

                Optional<User> userOpt2 = userRepo.findById(verifier.getUserId());
                if (userOpt2.isPresent()) {
                    User user = userOpt2.get();
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                }
                Optional<Document> documentOpt = documentRepo.findById(verifier.getDocumentId());
                if (documentOpt.isPresent()) {
                    Document document = documentOpt.get();
                    dto.setName(document.getTitle());
                    dto.setReferenceDocumentId(document.getReferenceDocumentId());
                }
                return dto;
            }).toList();

            Map<String, Object> mapResponse = transformPagination.transform(dtoList, pageData, null, null);

            return GlobalResponse.dataIsFound(mapResponse, request);

        } catch (Exception e) {
            LoggingFile.logException("ListApplianceDocumentVerifierService",
                    "findAllPendingApplianceByOwnerDocumentUserId", e);
            return GlobalResponse.serverError("DOC05FE071", request);
        }
    }

}
