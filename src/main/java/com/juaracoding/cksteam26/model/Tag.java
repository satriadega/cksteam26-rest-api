package com.juaracoding.cksteam26.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TagId")
    private Long tagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AnnotationId", nullable = false,
            foreignKey = @ForeignKey(name = "fk-tag-annotation"))
    private Annotation annotation;

    @Column(name = "TagName", length = 50, nullable = false)
    private String tagName;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Column(name = "UpdatedAt")
    private Date updatedAt;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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
