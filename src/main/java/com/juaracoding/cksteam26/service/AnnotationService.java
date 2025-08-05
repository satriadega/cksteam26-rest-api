package com.juaracoding.cksteam26.service;

import com.juaracoding.cksteam26.core.IService;
import com.juaracoding.cksteam26.dto.validasi.ValAnnotationDTO;
import com.juaracoding.cksteam26.model.*;
import com.juaracoding.cksteam26.repo.AnnotationRepo;
import com.juaracoding.cksteam26.repo.DocumentRepo;
import com.juaracoding.cksteam26.repo.UserDocumentPositionRepo;
import com.juaracoding.cksteam26.repo.UserRepo;
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

import java.util.List;
import java.util.Optional;

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
    UserRepo userRepo;

    @Autowired
    UserDocumentPositionRepo userDocumentPositionRepo;

    @Autowired
    TokenExtractor tokenExtractor;


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

            Optional<UserDocumentPosition> udpOpt = userDocumentPositionRepo.findByUserIdAndDocumentId(userId, docId);
            if (udpOpt.isEmpty()) {
                return GlobalResponse.customError("DOC04FV007", "You do not have permission to annotate this document", request);
            }

            Annotation annotation = mapToAnnotation(dto, document, userId);
            if ("OWNER".equals(udpOpt.get().getPosition())) {
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
                return GlobalResponse.customError("DOC04FV011", "Annotation text does not match document content at the specified position", request);
            }

            annotation.setId(null);
            annotationRepo.save(annotation);

            return GlobalResponse.dataSavedSuccessfully(request);
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
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        return null;
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
}