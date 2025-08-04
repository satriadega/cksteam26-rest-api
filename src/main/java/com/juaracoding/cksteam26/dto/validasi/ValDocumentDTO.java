package com.juaracoding.cksteam26.dto.validasi;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ValDocumentDTO {


    @NotBlank(message = "Title is required")
    @Size(max = 50, message = "Title must be at most 50 characters")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "publicVisibility cannot be null")
    private Boolean publicVisibility;

    @NotNull(message = "isPrivate cannot be null")
    private Boolean isPrivate;
    private Long referenceDocumentId;
    @NotNull(message = "Version is required")
    @Min(value = 0, message = "Version must be at least 0")
    private Integer version;
    @NotNull(message = "Subversion is required")
    @Min(value = 0, message = "Subversion must be at least 0")
    private Integer subversion;

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
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
}
