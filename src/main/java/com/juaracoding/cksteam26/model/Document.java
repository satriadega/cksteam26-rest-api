package com.juaracoding.cksteam26.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentId")
    private Long documentId;

    @Column(name = "Title", length = 50, nullable = false)
    private String title;

    @Lob
    @Column(name = "Content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "IsVerifiedAll", nullable = false, columnDefinition = "bit default 1")
    private Boolean isVerifiedAll = true;

    @Column(name = "PublicVisibility", nullable = false, columnDefinition = "bit default 0")
    private Boolean publicVisibility = false;

    @Column(name = "ReferenceDocumentId")
    private Long referenceDocumentId;

    @Column(name = "Version")
    private Integer version;

    @Column(name = "Subversion")
    private Integer subversion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedAt", nullable = false, updatable = false, columnDefinition = "datetime default getdate()")
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedAt")
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

    public Boolean getIsVerifiedAll() {
        return isVerifiedAll;
    }

    public void setIsVerifiedAll(Boolean isVerifiedAll) {
        this.isVerifiedAll = isVerifiedAll;
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
