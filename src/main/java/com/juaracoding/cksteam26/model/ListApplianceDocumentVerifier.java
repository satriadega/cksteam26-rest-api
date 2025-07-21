package com.juaracoding.cksteam26.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ListApplianceDocumentVerifier")
public class ListApplianceDocumentVerifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ListApplianceDocumentVerifierId")
    private Long listApplianceDocumentVerifierId;

    @Column(name = "DocumentId", nullable = false)
    private Long documentId;

    @Column(name = "UserId", nullable = false)
    private Long userId;

    @Column(name = "IsAccepted", nullable = false, columnDefinition = "bit default 0")
    private Boolean isAccepted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedAt", nullable = false, updatable = false, columnDefinition = "datetime default getdate()")
    private Date createdAt;

    public Long getListApplianceDocumentVerifierId() {
        return listApplianceDocumentVerifierId;
    }

    public void setListApplianceDocumentVerifierId(Long listApplianceDocumentVerifierId) {
        this.listApplianceDocumentVerifierId = listApplianceDocumentVerifierId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}