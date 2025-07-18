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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DocumentId",
            foreignKey = @ForeignKey(name = "fk-ladv-document"))
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId",
            foreignKey = @ForeignKey(name = "fk-ladv-user"))
    private User user;

    @Column(name = "IsAccepted", nullable = false)
    private Boolean isAccepted = false;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private Date createdAt = new Date();

    public Long getListApplianceDocumentVerifierId() {
        return listApplianceDocumentVerifierId;
    }

    public void setListApplianceDocumentVerifierId(Long listApplianceDocumentVerifierId) {
        this.listApplianceDocumentVerifierId = listApplianceDocumentVerifierId;
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
