package com.juaracoding.cksteam26.service;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 23/07/25 03.11
@Last Modified 23/07/25 03.11
Version 1.0
*/

import com.juaracoding.cksteam26.core.IService;
import com.juaracoding.cksteam26.dto.response.RespAnnotationDTO;
import com.juaracoding.cksteam26.dto.response.RespDocumentDTO;
import com.juaracoding.cksteam26.dto.response.RespTagDTO;
import com.juaracoding.cksteam26.dto.validasi.ValDocumentDTO;
import com.juaracoding.cksteam26.model.*;
import com.juaracoding.cksteam26.repo.*;
import com.juaracoding.cksteam26.security.JwtUtility;
import com.juaracoding.cksteam26.security.TokenExtractor;
import com.juaracoding.cksteam26.util.GlobalResponse;
import com.juaracoding.cksteam26.util.LoggingFile;
import com.juaracoding.cksteam26.util.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
                return GlobalResponse.dataIsNotFound("DOC01FV001", request);
            }
            mapResponse = transformPagination.transform(mapToModelMapper(pageData.getContent()),
                    pageData, "id", null);

        } catch (Exception e) {
//            System.out.println(e);
            return GlobalResponse.serverError("DOC01FE001", request);
        }
        return GlobalResponse.dataIsFound(mapResponse, request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        Page<Document> page;
        Map<String, Object> mapResponse;

        try {
            switch (column.toLowerCase()) {
                case "title":
                    page = documentRepo.findByTitleContainsIgnoreCase(value, pageable);
                    break;
                case "content":
                    page = documentRepo.findByContentContainsIgnoreCase(value, pageable);
                    break;
                case "isverifiedall":
                    page = documentRepo.findByIsVerifiedAll(Boolean.parseBoolean(value), pageable);
                    break;
                case "publicvisibility":
                    page = documentRepo.findByPublicVisibility(Boolean.parseBoolean(value), pageable);
                    break;
                case "referencedocumentid":
                    page = documentRepo.findByReferenceDocumentId(Long.parseLong(value), pageable);
                    break;
                case "version":
                    page = documentRepo.findByVersion(Integer.parseInt(value), pageable);
                    break;
                case "subversion":
                    page = documentRepo.findBySubversion(Integer.parseInt(value), pageable);
                    break;
                default:
                    page = documentRepo.findAll(pageable);
                    break;
            }
            mapResponse = transformPagination.transform(mapToModelMapper(page.getContent()), page, column, value);
            return GlobalResponse.dataIsFound(mapResponse, request);
        } catch (Exception e) {
            LoggingFile.logException("DocumentService", "findByParam", e);
            return GlobalResponse.serverError("DOC01FE011", request);
        }
    }


    @Override
    public ResponseEntity<Object> save(Document document, HttpServletRequest request) {
        if (document == null) {
            return GlobalResponse.objectNull("DOC02FV021", request);
        }

        if (Boolean.TRUE.equals(document.getPublicVisibility()) && Boolean.TRUE.equals(document.getPrivate())) {
            return GlobalResponse.customError("DOC02FV025", "Document cannot be public dan private at the same time", request);
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

            User user = userOpt.get();

            document.setVerifiedAll(true);
            document.setAnnotable(true);
            Document savedDoc = documentRepo.save(document);

            UserDocumentPosition userDocPosition = new UserDocumentPosition();
            userDocPosition.setUser(user);
            userDocPosition.setDocument(savedDoc);
            userDocPosition.setIsVerified(true);
            userDocPosition.setPosition("OWNER");

            userDocumentPositionRepo.save(userDocPosition);
        } catch (Exception e) {
            LoggingFile.logException(className, "save(Document document, HttpServletRequest request)", e);
            return GlobalResponse.serverError("DOC02FE021", request);
        }

        return GlobalResponse.dataSavedSuccessfully(request);
    }


    @Override
    public ResponseEntity<Object> findById(Long documentId, HttpServletRequest request) {
        if (documentId == null || documentId == 0) {
            return GlobalResponse.objectNull("DOC02FV031", request);
        }

        String username = tokenExtractor.extractUsernameFromRequest(request);
        Long userId = null;
        if (username != null) {
            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isPresent()) {
                userId = userOpt.get().getId();
            }
        }

        try {
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
                            .collect(Collectors.toList())
            );

            return GlobalResponse.dataIsFound(dto, request);
        } catch (Exception e) {
            LoggingFile.logException(className, "findById", e);
            return GlobalResponse.serverError("DOC02FE031", request);
        }
    }


    //    NO UPDATE
    @Override
    public ResponseEntity<Object> update(Long id, Document document, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    public Document mapToModelMapper(ValDocumentDTO valDocumentDTO) {
        return modelMapper.map(valDocumentDTO, Document.class);
    }

    public List<RespDocumentDTO> mapToModelMapper(List<Document> documentList) {
        return modelMapper.map(documentList, new TypeToken<List<RespDocumentDTO>>() {
        }.getType());
    }

    public RespDocumentDTO mapToModelMapper(Document document) {
        return modelMapper.map(document, RespDocumentDTO.class);
    }

    public RespAnnotationDTO mapToAnnotationDTO(Annotation annotation) {
        return modelMapper.map(annotation, RespAnnotationDTO.class);
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

            Page<Document> page = documentRepo.searchDocumentsByKeyword(keyword, userId, pageable);

            if (page.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC01FV031", request);
            }

            List<Long> documentIds = page.getContent().stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());

            List<Tag> tags = tagRepo.findTagsByDocumentIds(documentIds);

            Map<Long, List<Tag>> tagsByDocumentIdRaw = tags.stream()
                    .collect(Collectors.groupingBy(tag -> tag.getAnnotation().getDocument().getId()));

            Map<Long, List<RespTagDTO>> tagsByDocumentId = new HashMap<>();
            for (Map.Entry<Long, List<Tag>> entry : tagsByDocumentIdRaw.entrySet()) {
                List<RespTagDTO> dtoList = entry.getValue().stream()
                        .map(tag -> modelMapper.map(tag, RespTagDTO.class))
                        .collect(Collectors.toList());
                tagsByDocumentId.put(entry.getKey(), dtoList);
            }

            List<RespDocumentDTO> dtoList = page.getContent().stream()
                    .map(doc -> {
                        RespDocumentDTO dto = modelMapper.map(doc, RespDocumentDTO.class);
                        dto.setTags(tagsByDocumentId.getOrDefault(doc.getId(), Collections.emptyList()));
                        return dto;
                    })
                    .collect(Collectors.toList());

            Map<String, Object> mapResponse = transformPagination.transform(dtoList, page, "title", keyword);

            return GlobalResponse.dataIsFound(mapResponse, request);

        } catch (Exception e) {
            return GlobalResponse.serverError("DOC01FE031", request);
        }
    }
}
