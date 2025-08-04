package com.juaracoding.cksteam26.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentId")
    private Long id;

    @Column(name = "Title", length = 50, nullable = false)
    private String title;

    @Lob
    @Column(name = "Content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "IsVerifiedAll", nullable = false, columnDefinition = "bit default 1")
    private Boolean isVerifiedAll = true;

    @Column(name = "IsAnnotable", nullable = false, columnDefinition = "bit default 1")
    private Boolean isAnnotable = true;
    @Column(name = "PublicVisibility", nullable = false, columnDefinition = "bit default 0")
    private Boolean publicVisibility = false;
    @Column(name = "IsPrivate", nullable = false, columnDefinition = "bit default 1")
    private Boolean isPrivate = true;
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

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Boolean getAnnotable() {
        return isAnnotable;
    }

    public void setAnnotable(Boolean annotable) {
        isAnnotable = annotable;
    }

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
