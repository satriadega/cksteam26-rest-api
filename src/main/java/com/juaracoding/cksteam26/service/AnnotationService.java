package com.juaracoding.cksteam26.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juaracoding.cksteam26.core.IService;
import com.juaracoding.cksteam26.dto.response.RespAnnotationDTO;
import com.juaracoding.cksteam26.dto.validasi.ValAnnotationDTO;
import com.juaracoding.cksteam26.model.Annotation;
import com.juaracoding.cksteam26.model.Document;
import com.juaracoding.cksteam26.model.Notification;
import com.juaracoding.cksteam26.model.Tag;
import com.juaracoding.cksteam26.model.User;
import com.juaracoding.cksteam26.model.UserDocumentPosition;
import com.juaracoding.cksteam26.repo.AnnotationRepo;
import com.juaracoding.cksteam26.repo.DocumentRepo;
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
public class AnnotationService implements IService<Annotation> {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    AnnotationRepo annotationRepo;

    @Autowired
    NotificationRepo notificationRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserDocumentPositionRepo userDocumentPositionRepo;

    @Autowired
    TokenExtractor tokenExtractor;

    @Autowired
    private TransformPagination transformPagination;

    private String className = "AnnotationService";

    public ResponseEntity<Object> save(ValAnnotationDTO dto, HttpServletRequest request) {
        if (dto == null) {
            return GlobalResponse.objectNull("DOC04FV001", request);
        }

        try {
            String username = tokenExtractor.extractUsernameFromRequest(request);
            if (username == null || username.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC04FV003", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC04FV004", request);
            }

            User user = userOpt.get();
            Long userId = user.getId();

            Long docId = dto.getDocumentId();
            if (docId == null) {
                return GlobalResponse.customError("DOC04FV005", "Document ID is required", request);
            }

            Optional<Document> documentOpt = documentRepo.findAccessibleDocumentById(docId, userId);
            if (documentOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC04FV006", request);
            }

            Document document = documentOpt.get();
            if (Boolean.FALSE.equals(document.getAnnotable())) {
                return GlobalResponse.customError("DOC04FV012", "Document is not annotable", request);
            }

            List<UserDocumentPosition> udpList = userDocumentPositionRepo.findAllByUserIdAndDocumentId(userId,
                    document.getReferenceDocumentId());
            if (udpList.isEmpty()) {
                return GlobalResponse.customError("DOC04FV007", "You do not have permission to annotate this document",
                        request);
            }

            UserDocumentPosition udp = udpList.get(0);
            String position = udp.getPosition();

            if (udpList.size() > 1) {
                Long refId = document.getReferenceDocumentId();
                if (refId != null) {
                    documentRepo.findById(refId).ifPresent(refDoc -> {
                        refDoc.setVerifiedAll(false);
                        documentRepo.save(refDoc);
                    });
                }
            }

            Annotation annotation = mapToAnnotation(dto, document, userId);

            if ("OWNER".equalsIgnoreCase(position)) {
                annotation.setVerified(true);
            }

            String content = document.getContent();
            if (content == null) {
                return GlobalResponse.customError("DOC04FV008", "Document content is empty", request);
            }

            Integer startNo = annotation.getStartNo();
            Integer endNo = annotation.getEndNo();
            String annotationText = annotation.getSelectedText();

            if (startNo == null || endNo == null) {
                return GlobalResponse.customError("DOC04FV009", "StartNo and EndNo must be provided", request);
            }

            if (startNo < 0 || endNo > content.length() || startNo > endNo) {
                return GlobalResponse.customError("DOC04FV010", "Invalid range for StartNo and EndNo", request);
            }

            String substring = content.substring(startNo, endNo);
            if (!substring.equals(annotationText)) {
                return GlobalResponse.customError("DOC04FV011",
                        "Annotation text does not match document content at the specified position", request);
            }

            if ("VERIFIER".equalsIgnoreCase(position)) {
                Long referenceId = document.getReferenceDocumentId();
                if (referenceId == null) {
                    referenceId = document.getId();
                }
                annotationRepo.updateAllIsVerifiedFalseByReferenceId(referenceId);
                annotation.setVerified(false);
                document.setVerifiedAll(false);
                documentRepo.save(document);
            }

            annotation.setId(null);
            annotationRepo.save(annotation);

            // Create notification for owner
            Optional<UserDocumentPosition> userDocumentPositionOpt = userDocumentPositionRepo
                    .findByDocumentIdAndPosition(
                            document.getId(), "OWNER");

            if (userDocumentPositionOpt.isPresent()) {
                User ownerUser = userDocumentPositionOpt.get().getUser();
                if (!ownerUser.getId().equals(userId)) {
                    ownerUser.setUpdatedAt(new Date());
                    ownerUser.setHasNotification(true);

                    Integer notifType = ownerUser.getNotificationType();
                    if (notifType == null || notifType == 0 || notifType == 2) {
                        ownerUser.setNotificationType(2);
                        System.err.println("debug");
                    } else {
                        ownerUser.setNotificationType(3);
                    }

                    Integer notifCounter = ownerUser.getNotificationCounter();
                    ownerUser.setNotificationCounter((notifCounter == null ? 1 : notifCounter + 1));

                    userRepo.save(ownerUser);

                    Notification notification = new Notification();
                    notification.setUser(ownerUser);
                    notification.setIsRead(false);
                    notification.setType("ANNOTATION");
                    notification.setCreatedAt(new Date());
                    notification.setId(null);
                    notificationRepo.save(notification);
                }
            }

            return GlobalResponse.dataSavedSuccessfully(null, request);
        } catch (Exception e) {
            LoggingFile.logException(className, "save(ValAnnotationDTO dto, HttpServletRequest request)", e);
            return GlobalResponse.serverError("DOC04FE001", request);
        }
    }

    @Override
    public ResponseEntity<Object> save(Annotation annotation, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> update(Long id, Annotation annotation, HttpServletRequest request) {
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
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value,
            HttpServletRequest request) {
        try {
            String username = tokenExtractor.extractUsernameFromRequest(request);
            if (username == null || username.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC04FV011", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC04FV012", request);
            }

            Long ownerUserId = userOpt.get().getId();

            Page<Annotation> page;

            boolean noFilter = (value == null || value.trim().isEmpty());

            if (noFilter) {
                page = annotationRepo.findByOwnerUserId(ownerUserId, pageable);
            } else {
                switch (column.toLowerCase()) {
                    case "selectedtext":
                        page = annotationRepo.findBySelectedTextContainingIgnoreCaseAndOwnerUserId(value, ownerUserId,
                                pageable);
                        break;
                    case "description":
                        page = annotationRepo.findByDescriptionContainingIgnoreCaseAndOwnerUserId(value, ownerUserId,
                                pageable);
                        break;
                    case "isverified":
                        page = annotationRepo.findByIsVerifiedAndOwnerUserId(Boolean.parseBoolean(value), ownerUserId,
                                pageable);
                        break;
                    case "startno":
                        try {
                            Integer startNo = Integer.parseInt(value);
                            page = annotationRepo.findByStartNoAndOwnerUserId(startNo, ownerUserId, pageable);
                        } catch (NumberFormatException e) {
                            page = annotationRepo.findByOwnerUserId(ownerUserId, pageable);
                        }
                        break;
                    case "endno":
                        try {
                            Integer endNo = Integer.parseInt(value);
                            page = annotationRepo.findByEndNoAndOwnerUserId(endNo, ownerUserId, pageable);
                        } catch (NumberFormatException e) {
                            page = annotationRepo.findByOwnerUserId(ownerUserId, pageable);
                        }
                        break;
                    default:
                        page = annotationRepo.findByOwnerUserId(ownerUserId, pageable);
                        break;
                }
            }

            if (page.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC04FV013", request);
            }

            List<RespAnnotationDTO> dtoList = page.getContent().stream().map(annotation -> {
                RespAnnotationDTO dto = mapToAnnotationDTO(annotation);
                return dto;
            }).collect(Collectors.toList());

            Map<String, Object> mapResponse = transformPagination.transform(dtoList, page, column, value);

            return GlobalResponse.dataIsFound(mapResponse, request);

        } catch (Exception e) {
            LoggingFile.logException(className, "findByParam", e);
            return GlobalResponse.serverError("DOC04FE011", request);
        }
    }

    public Annotation mapToAnnotation(ValAnnotationDTO dto, Document document, Long ownerUserId) {
        Annotation annotation = new Annotation();
        annotation.setDocument(document);
        annotation.setOwnerUserId(ownerUserId);
        annotation.setSelectedText(dto.getSelectedText());
        annotation.setStartNo(dto.getStartNo());
        annotation.setEndNo(dto.getEndNo());
        annotation.setDescription(dto.getDescription());
        annotation.setVerified(false); // default

        List<Tag> tagEntities = dto.getTags().stream().map(tagStr -> {
            Tag tag = new Tag();
            tag.setTagName(tagStr);
            tag.setAnnotation(annotation); // penting untuk relasi
            return tag;
        }).toList();

        annotation.setTags(tagEntities);
        return annotation;
    }

    public RespAnnotationDTO mapToAnnotationDTO(Annotation annotation) {
        return modelMapper.map(annotation, RespAnnotationDTO.class);
    }
}
