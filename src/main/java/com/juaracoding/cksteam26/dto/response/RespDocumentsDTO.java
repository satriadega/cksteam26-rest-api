package com.juaracoding.cksteam26.dto.response;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 04/08/25 12.56
@Last Modified 04/08/25 12.56
Version 1.0
*/

import java.time.Instant;
import java.util.List;

public class RespDocumentsDTO {
    private Long id;
    private String title;
    private String content;
    private Boolean publicVisibility;
    private Long referenceDocumentId;
    private Integer version;
    private Integer subversion;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean privateDoc;
    private Boolean verifiedAll;
    private Boolean annotable;
    private List<RespTagDTO> tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getPublicVisibility() {
        return publicVisibility;
    }

    public void setPublicVisibility(Boolean publicVisibility) {
        this.publicVisibility = publicVisibility;
    }

    public Long getReferenceDocumentId() {
        return referenceDocumentId;
    }

    public void setReferenceDocumentId(Long referenceDocumentId) {
        this.referenceDocumentId = referenceDocumentId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getSubversion() {
        return subversion;
    }

    public void setSubversion(Integer subversion) {
        this.subversion = subversion;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getPrivateDoc() {
        return privateDoc;
    }

    public void setPrivateDoc(Boolean privateDoc) {
        this.privateDoc = privateDoc;
    }

    public Boolean getVerifiedAll() {
        return verifiedAll;
    }

    public void setVerifiedAll(Boolean verifiedAll) {
        this.verifiedAll = verifiedAll;
    }

    public Boolean getAnnotable() {
        return annotable;
    }

    public void setAnnotable(Boolean annotable) {
        this.annotable = annotable;
    }

    public List<RespTagDTO> getTags() {
        return tags;
    }

    public void setTags(List<RespTagDTO> tags) {
        this.tags = tags;
    }
}
