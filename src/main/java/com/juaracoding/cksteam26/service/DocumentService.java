package com.juaracoding.cksteam26.service;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 23/07/25 03.11
@Last Modified 23/07/25 03.11
Version 1.0
*/

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juaracoding.cksteam26.core.IService;
import com.juaracoding.cksteam26.dto.response.RespAnnotationDTO;
import com.juaracoding.cksteam26.dto.response.RespDocumentDTO;
import com.juaracoding.cksteam26.dto.response.RespTagDTO;
import com.juaracoding.cksteam26.dto.validasi.ValDocumentDTO;
import com.juaracoding.cksteam26.model.Annotation;
import com.juaracoding.cksteam26.model.Document;
import com.juaracoding.cksteam26.model.Tag;
import com.juaracoding.cksteam26.model.User;
import com.juaracoding.cksteam26.model.UserDocumentPosition;
import com.juaracoding.cksteam26.repo.AnnotationRepo;
import com.juaracoding.cksteam26.repo.DocumentRepo;
import com.juaracoding.cksteam26.repo.TagRepo;
import com.juaracoding.cksteam26.repo.UserDocumentPositionRepo;
import com.juaracoding.cksteam26.repo.UserRepo;
import com.juaracoding.cksteam26.security.JwtUtility;
import com.juaracoding.cksteam26.security.TokenExtractor;
import com.juaracoding.cksteam26.util.GlobalResponse;
import com.juaracoding.cksteam26.util.LoggingFile;
import com.juaracoding.cksteam26.util.TransformPagination;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class DocumentService implements IService<Document> {

    private String className = "DocumentService";

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDocumentPositionRepo userDocumentPositionRepo;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private AnnotationRepo annotationRepo;

    @Autowired
    private TagRepo tagRepo;

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Document> pageData = null;
        Map<String, Object> mapResponse = null;
        try {
            pageData = documentRepo.findAll(pageable);
            if (pageData.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV001", request);
            }
            mapResponse = transformPagination.transform(mapToModelMapper(pageData.getContent()),
                    pageData, "id", null);

        } catch (Exception e) {
            // System.out.println(e);
            return GlobalResponse.serverError("DOC02FE001", request);
        }
        return GlobalResponse.dataIsFound(mapResponse, request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value,
            HttpServletRequest request) {
        try {
            String username = tokenExtractor.extractUsernameFromRequest(request);
            if (username == null || username.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV011", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV012", request);
            }

            Long userId = userOpt.get().getId();

            List<Long> documentIds = documentRepo.findVisibleDocumentIds(userId);

            if (documentIds.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV013", request);
            }

            Page<Document> page;

            boolean noFilter = (value == null || value.trim().isEmpty());

            if (noFilter) {
                page = documentRepo.findByIdIn(documentIds, pageable);
            } else {
                switch (column.toLowerCase()) {
                    case "title":
                        page = documentRepo.findByTitleContainsIgnoreCaseAndIdIn(value, documentIds, pageable);
                        break;
                    case "content":
                        page = documentRepo.findByContentContainsIgnoreCaseAndIdIn(value, documentIds, pageable);
                        break;
                    case "isverifiedall":
                        page = documentRepo.findByIsVerifiedAllAndIdIn(Boolean.parseBoolean(value), documentIds,
                                pageable);
                        break;
                    case "publicvisibility":
                        page = documentRepo.findByPublicVisibilityAndIdIn(Boolean.parseBoolean(value), documentIds,
                                pageable);
                        break;
                    case "referencedocumentid":
                        page = documentRepo.findByReferenceDocumentIdAndIdIn(Long.parseLong(value), documentIds,
                                pageable);
                        break;
                    case "version":
                        page = documentRepo.findByVersionAndIdIn(Integer.parseInt(value), documentIds, pageable);
                        break;
                    case "subversion":
                        page = documentRepo.findBySubversionAndIdIn(Integer.parseInt(value), documentIds, pageable);
                        break;
                    case "isannotable":
                        page = documentRepo.findByIsAnnotableAndIdIn(Boolean.parseBoolean(value), documentIds,
                                pageable);
                        break;
                    case "isprivate":
                        page = documentRepo.findByIsPrivateAndIdIn(Boolean.parseBoolean(value), documentIds,
                                pageable);
                        break;
                    default:
                        page = documentRepo.findByIdIn(documentIds, pageable);
                        break;
                }
            }

            if (page.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV014", request);
            }

            List<RespDocumentDTO> dtoList = page.getContent().stream().map(doc -> {
                RespDocumentDTO dto = mapToModelMapper(doc);

                List<Tag> tags = tagRepo.findTagsByDocumentId(doc.getId());
                dto.setTags(tags.stream().map(this::mapToTagDTO).collect(Collectors.toList()));

                dto.setAnnotationCount(annotationRepo.countByDocumentId(doc.getId()));

                Optional<UserDocumentPosition> userDocPositions = userDocumentPositionRepo
                        .findByDocumentIdAndPosition(doc.getId(), "OWNER");
                if (userDocPositions.isPresent()) {
                    User anyUser = userDocPositions.get().getUser();
                    if (anyUser != null) {
                        dto.setName(anyUser.getName());
                        dto.setUsername(anyUser.getUsername());
                    }
                }

                return dto;
            }).collect(Collectors.toList());

            Map<String, Object> mapResponse = transformPagination.transform(dtoList, page, column, value);

            return GlobalResponse.dataIsFound(mapResponse, request);

        } catch (Exception e) {
            LoggingFile.logException(className, "findByParam", e);
            return GlobalResponse.serverError("DOC02FE011", request);
        }
    }

    @Override
    public ResponseEntity<Object> save(Document document, HttpServletRequest request) {
        if (document == null) {
            return GlobalResponse.objectNull("DOC02FV021", request);
        }

        if (Boolean.TRUE.equals(document.getPublicVisibility()) && Boolean.TRUE.equals(document.getPrivate())) {
            return GlobalResponse.customError("DOC02FV025", "Document cannot be public and private at the same time",
                    request);
        }

        try {
            String username = tokenExtractor.extractUsernameFromRequest(request);
            if (username == null || username.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV023", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV024", request);
            }

            Long referenceId = document.getReferenceDocumentId();

            if (referenceId == null) {
                if (document.getVersion() != 1 || document.getSubversion() != 0) {
                    return GlobalResponse.customError("DOC02FV032",
                            "First version must have version=1 and subversion=0", request);
                }
            } else {
                Optional<Document> refDoc = documentRepo.findById(referenceId);
                if (refDoc.isEmpty()) {
                    return GlobalResponse.customError("DOC02FV027", "Reference document not found", request);
                }

                Optional<UserDocumentPosition> userDocOpt = userDocumentPositionRepo
                        .findByUserIdAndDocumentId(userOpt.get().getId(), referenceId);
                if (userDocOpt.isEmpty() || !"OWNER".equalsIgnoreCase(userDocOpt.get().getPosition())) {
                    return GlobalResponse.customError("DOC02FV031", "You are not authorized to version this document",
                            request);
                }

                List<Document> latestDocs = documentRepo.findTopDocumentsByReferenceDocumentId(referenceId,
                        PageRequest.of(0, 1));
                if (latestDocs.isEmpty()) {
                    return GlobalResponse.customError("DOC02FV027", "Reference document not found", request);
                }
                Document latest = latestDocs.get(0);

                int versionDiff = document.getVersion() - latest.getVersion();
                int subversionDiff = document.getSubversion() - latest.getSubversion();

                boolean valid;
                if (versionDiff == 1) {
                    valid = (document.getSubversion() == 0);
                } else if (versionDiff == 0) {
                    valid = (subversionDiff == 1);
                } else {
                    valid = false;
                }

                if (!valid) {
                    return GlobalResponse.customError("DOC02FV030", "Version and Subversion must increment correctly",
                            request);
                }

                latest.setAnnotable(false);
                documentRepo.save(latest);
            }

            User user = userOpt.get();
            document.setVerifiedAll(true);
            document.setAnnotable(true);
            document.setId(null);

            Document savedDoc = documentRepo.save(document);

            if (referenceId == null) {
                savedDoc.setReferenceDocumentId(savedDoc.getId());
            } else {
                savedDoc.setReferenceDocumentId(referenceId);
            }

            documentRepo.save(savedDoc);

            UserDocumentPosition userDocPosition = new UserDocumentPosition();
            userDocPosition.setUser(user);
            userDocPosition.setDocument(savedDoc);
            userDocPosition.setIsVerified(true);
            userDocPosition.setPosition("OWNER");

            userDocumentPositionRepo.save(userDocPosition);

            return GlobalResponse.dataSavedSuccessfully(mapToModelMapper(savedDoc), request);
        } catch (Exception e) {
            LoggingFile.logException(className, "save(Document document, HttpServletRequest request)", e);
            return GlobalResponse.serverError("DOC02FE021", request);
        }
    }

    @Override
    public ResponseEntity<Object> update(Long id, Document document, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long documentId, HttpServletRequest request) {
        if (documentId == null || documentId == 0) {
            return GlobalResponse.objectNull("DOC02FV033", request);
        }

        try {
            String username = tokenExtractor.extractUsernameFromRequest(request);
            Long userId = null;
            if (username != null) {
                Optional<User> userOpt = userRepo.findByUsername(username);
                if (userOpt.isPresent()) {
                    userId = userOpt.get().getId();
                }
            }

            Optional<Document> documentOpt = documentRepo.findAccessibleDocumentById(documentId, userId);
            if (documentOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV034", request);
            }

            Document document = documentOpt.get();
            List<Annotation> annotations = annotationRepo.findByDocumentIdWithTags(documentId);

            RespDocumentDTO dto = mapToModelMapper(document);
            dto.setAnnotations(
                    annotations.stream()
                            .map(this::mapToAnnotationDTO) // map ke DTO annotation jika ada DTO-nya
                            .collect(Collectors.toList()));
            dto.setAnnotationCount(annotationRepo.countByDocumentId(documentId));

            // Populate name from owner
            Optional<UserDocumentPosition> userDocPos = userDocumentPositionRepo.findByDocumentIdAndPosition(documentId,
                    "OWNER");
            if (userDocPos.isPresent()) {
                User ownerUser = userDocPos.get().getUser();
                dto.setName(ownerUser.getName());
                dto.setUsername(ownerUser.getUsername());
            }

            return GlobalResponse.dataIsFound(dto, request);
        } catch (Exception e) {
            LoggingFile.logException(className, "findById", e);
            return GlobalResponse.serverError("DOC02FE031", request);
        }
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    public Document mapToModelMapper(ValDocumentDTO valDocumentDTO) {
        return modelMapper.map(valDocumentDTO, Document.class);
    }

    public List<RespDocumentDTO> mapToModelMapper(List<Document> documentList) {
        List<RespDocumentDTO> respDocumentDTOs = modelMapper.map(documentList, new TypeToken<List<RespDocumentDTO>>() {
        }.getType());

        for (int i = 0; i < documentList.size(); i++) {
            respDocumentDTOs.get(i).setDocumentId(documentList.get(i).getId());
        }

        return respDocumentDTOs;
    }

    public RespDocumentDTO mapToModelMapper(Document document) {
        RespDocumentDTO respDocumentDTO = modelMapper.map(document, RespDocumentDTO.class);
        respDocumentDTO.setDocumentId(document.getId());
        respDocumentDTO.setPublicVisibility(document.getPublicVisibility());
        respDocumentDTO.setIsPrivate(document.getPrivate());
        respDocumentDTO.setIsAnnotable(document.getAnnotable());
        return respDocumentDTO;
    }

    public RespAnnotationDTO mapToAnnotationDTO(Annotation annotation) {
        RespAnnotationDTO dto = modelMapper.map(annotation, RespAnnotationDTO.class);
        if (annotation.getTags() != null) {
            dto.setTags(annotation.getTags().stream().map(this::mapToTagDTO).collect(Collectors.toList()));
        }
        return dto;
    }

    public RespTagDTO mapToTagDTO(Tag tag) {
        return modelMapper.map(tag, RespTagDTO.class);
    }

    public ResponseEntity<Object> searchByKeyword(String keyword, Pageable pageable, HttpServletRequest request) {
        try {
            String username = tokenExtractor.extractUsernameFromRequest(request);
            Long userId = null;
            if (username != null) {
                Optional<User> userOpt = userRepo.findByUsername(username);
                if (userOpt.isPresent()) {
                    userId = userOpt.get().getId();
                }
            }

            Page<Document> page;
            String effectiveKeyword = (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim();

            if (effectiveKeyword == null) {
                System.out.println("gerak jalan");
                page = documentRepo.findAllVisibleDocuments(userId, pageable);
            } else {
                page = documentRepo.searchDocumentsByKeyword(effectiveKeyword, userId, pageable);
            }

            if (page.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV035", request);
            }

            List<RespDocumentDTO> dtoList = page.getContent().stream()
                    .map(doc -> {
                        RespDocumentDTO dto = mapToModelMapper(doc);

                        List<Tag> tags = tagRepo.findTagsByDocumentId(doc.getId());
                        dto.setTags(tags.stream().map(this::mapToTagDTO).collect(Collectors.toList()));
                        dto.setAnnotationCount(annotationRepo.countByDocumentId(doc.getId()));

                        // Populate name from owner
                        Optional<UserDocumentPosition> userDocPos = userDocumentPositionRepo
                                .findByDocumentIdAndPosition(doc.getId(), "OWNER");
                        if (userDocPos.isPresent()) {
                            User ownerUser = userDocPos.get().getUser();
                            if (ownerUser != null) {
                                dto.setName(ownerUser.getName());
                            }
                        }

                        return dto;
                    })
                    .collect(Collectors.toList());

            Map<String, Object> mapResponse = transformPagination.transform(dtoList, page, "title", effectiveKeyword);

            return GlobalResponse.dataIsFound(mapResponse, request);
        } catch (Exception e) {
            return GlobalResponse.serverError("DOC02FE031", request);
        }
    }

    public ResponseEntity<Object> findRelatedDocuments(Long referenceDocumentId, HttpServletRequest request) {
        try {
            String username = tokenExtractor.extractUsernameFromRequest(request);
            Long userId = null;
            if (username != null) {
                Optional<User> userOpt = userRepo.findByUsername(username);
                if (userOpt.isPresent()) {
                    userId = userOpt.get().getId();
                }
            }

            List<Document> documents = documentRepo.findRelatedDocumentsById(referenceDocumentId, userId);

            if (documents.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV041", request);
            }

            return GlobalResponse.dataIsFound(mapToModelMapper(documents), request);

        } catch (Exception e) {
            LoggingFile.logException(className, "findRelatedDocuments", e);
            return GlobalResponse.serverError("DOC02FE041", request);
        }
    }
}
