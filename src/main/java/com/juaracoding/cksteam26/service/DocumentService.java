package com.juaracoding.cksteam26.service;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 23/07/25 03.11
@Last Modified 23/07/25 03.11
Version 1.0
*/

import com.juaracoding.cksteam26.core.IService;
import com.juaracoding.cksteam26.dto.ValDocumentDTO;
import com.juaracoding.cksteam26.dto.response.RespDocumentDTO;
import com.juaracoding.cksteam26.model.Document;
import com.juaracoding.cksteam26.repo.DocumentRepo;
import com.juaracoding.cksteam26.util.GlobalResponse;
import com.juaracoding.cksteam26.util.LoggingFile;
import com.juaracoding.cksteam26.util.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DocumentService implements IService<Document> {

    private String className = "DocumentService";

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

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
            System.out.println(e);
            return GlobalResponse.serverError("DOC01FE001", request);
        }
        return GlobalResponse.dataIsFound(mapResponse, request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
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
            return GlobalResponse.objectNull("DOC01FV021", request);
        }
        try {
            documentRepo.save(document);
        } catch (Exception e) {
            LoggingFile.logException(className, "save(Document document, HttpServletRequest request) SQLException", e);
            return GlobalResponse.serverError("DOC01FE021", request);
        }

        return GlobalResponse.dataSavedSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Document document, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    public Document mapToModelMapper(ValDocumentDTO valDocumentDTO) {
        modelMapper.addMappings(new PropertyMap<ValDocumentDTO, Document>() {
            @Override
            protected void configure() {
                map().setId(source.getReferenceDocumentId());
            }
        });
        return modelMapper.map(valDocumentDTO, Document.class);
    }

    public List<RespDocumentDTO> mapToModelMapper(List<Document> documentList) {
        return modelMapper.map(documentList, new TypeToken<List<RespDocumentDTO>>() {
        }.getType());
    }

    public ResponseEntity<Object> searchByKeyword(String keyword, Pageable pageable, HttpServletRequest request) {
        try {
            Page<Document> page = documentRepo.searchDocumentsByKeyword(keyword, pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC01FV031", request);
            }
            return GlobalResponse.dataIsFound(page.getContent(), request);
        } catch (Exception e) {
            return GlobalResponse.serverError("DOC01FE031", request);
        }
    }
}
