package com.juaracoding.cksteam26.dto.response;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 23/07/25 03.26
@Last Modified 23/07/25 03.26
Version 1.0
*/

import java.util.Date;

public class RespDocumentDTO {
    private Long documentId;

    private String title;

    private String content;

    private Boolean isVerifiedAll;

    private Boolean publicVisibility;

    private Long referenceDocumentId;

    private Integer version;

    private Integer subversion;

    private Date createdAt;

    private Date updatedAt;

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
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

    public Boolean getVerifiedAll() {
        return isVerifiedAll;
    }

    public void setVerifiedAll(Boolean verifiedAll) {
        isVerifiedAll = verifiedAll;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
