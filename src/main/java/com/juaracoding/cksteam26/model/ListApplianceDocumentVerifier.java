package com.juaracoding.cksteam26.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ListApplianceDocumentVerifier",
        uniqueConstraints = @UniqueConstraint(columnNames = {"UserId", "DocumentId"}))
public class ListApplianceDocumentVerifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ListApplianceDocumentVerifierId")
    private Long id;

    @Column(name = "DocumentId", nullable = false)
    private Long documentId;

    @Column(name = "UserId", nullable = false)
    private Long userId;

    @Column(name = "OwnerDocumentUserId", nullable = false)
    private Long ownerDocumentUserId;

    @Column(name = "IsAccepted", nullable = false, columnDefinition = "bit default 0")
    private Boolean isAccepted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedAt", nullable = false, updatable = false, columnDefinition = "datetime default getdate()")
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getOwnerDocumentUserId() {
        return ownerDocumentUserId;
    }

    public void setOwnerDocumentUserId(Long ownerDocumentUserId) {
        this.ownerDocumentUserId = ownerDocumentUserId;
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
