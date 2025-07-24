package com.juaracoding.cksteam26.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ValDocumentDTO {

    private Long userId;

    private Long organizationId;

    @NotBlank(message = "Title is required")
    @Size(max = 50, message = "Title must be at most 50 characters")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    private Boolean isVerifiedAll = true;

    private Boolean publicVisibility = false;

    private Long referenceDocumentId;

    @Min(value = 0, message = "Version must be at least 0")
    private Integer version;

    @Min(value = 0, message = "Subversion must be at least 0")
    private Integer subversion;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
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
}
