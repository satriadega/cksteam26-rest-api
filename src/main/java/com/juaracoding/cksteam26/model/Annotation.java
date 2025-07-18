package com.juaracoding.cksteam26.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Annotation")
public class Annotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnnotationId")
    private Long annotationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DocumentId", nullable = false,
            foreignKey = @ForeignKey(name = "fk-annotation-document"))
    private Document document;

    @Column(name = "OwnerUserId")
    private Long ownerUserId;

    @Column(name = "IsVerified", nullable = false)
    private Boolean isVerified = false;

    @Column(name = "SelectedText", length = 500, nullable = false)
    private String selectedText;

    @Column(name = "StartNo", nullable = false)
    private Integer startNo;

    @Column(name = "EndNo", nullable = false)
    private Integer endNo;

    @Column(name = "Description", length = 500, nullable = false)
    private String description;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Column(name = "UpdatedAt")
    private Date updatedAt;

    public Long getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(Long annotationId) {
        this.annotationId = annotationId;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getSelectedText() {
        return selectedText;
    }

    public void setSelectedText(String selectedText) {
        this.selectedText = selectedText;
    }

    public Integer getStartNo() {
        return startNo;
    }

    public void setStartNo(Integer startNo) {
        this.startNo = startNo;
    }

    public Integer getEndNo() {
        return endNo;
    }

    public void setEndNo(Integer endNo) {
        this.endNo = endNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
