package com.juaracoding.cksteam26.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Annotation")
public class Annotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnnotationId", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DocumentId", nullable = false, foreignKey = @ForeignKey(name = "fk-annotation-document"))
    private Document document;

    @Column(name = "OwnerUserId")
    private Long ownerUserId;

    @Column(name = "IsVerified", nullable = false)
    private Boolean isVerified = false;

    @Column(name = "SelectedText", length = 1000, nullable = false)
    private String selectedText;

    @Column(name = "StartNo", nullable = false)
    private Integer startNo;

    @Column(name = "EndNo", nullable = false)
    private Integer endNo;

    @Column(name = "Description", length = 500, nullable = false)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedAt")
    private Date updatedAt;

    @OneToMany(mappedBy = "annotation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags;

    @Column(name = "CounterRejected", nullable = false)
    private Integer counterRejected = 0;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {

        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
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

    // getter setter untuk counterRejected
    public Integer getCounterRejected() {
        return counterRejected;
    }

    public void setCounterRejected(Integer counterRejected) {
        this.counterRejected = counterRejected;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
        if (this.counterRejected == null) {
            this.counterRejected = 0;
        }
    }
}
