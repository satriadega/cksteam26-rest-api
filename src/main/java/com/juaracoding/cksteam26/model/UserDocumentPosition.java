package com.juaracoding.cksteam26.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "UserDocumentPosition",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_document_user_position",
                columnNames = {"DocumentId", "UserId", "Position"}))
public class UserDocumentPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserDocumentVerifierId")
    private Long userDocumentVerifierId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DocumentId",
            foreignKey = @ForeignKey(name = "fk_udv_document"),
            nullable = false)
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId",
            foreignKey = @ForeignKey(name = "fk_udv_user"),
            nullable = false)
    private User user;

    @Column(name = "IsVerified", nullable = false)
    private Boolean isVerified = false;

    @Column(name = "Position", length = 20, nullable = false)
    private String position = "OWNER";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedAt")
    private Date updatedAt;

    // getters & setters

    public Long getUserDocumentVerifierId() {
        return userDocumentVerifierId;
    }

    public void setUserDocumentVerifierId(Long userDocumentVerifierId) {
        this.userDocumentVerifierId = userDocumentVerifierId;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
