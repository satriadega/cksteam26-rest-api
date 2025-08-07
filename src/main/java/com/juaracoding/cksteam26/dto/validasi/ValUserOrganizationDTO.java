package com.juaracoding.cksteam26.dto.validasi;

import java.util.Date;

import jakarta.validation.constraints.NotNull;

public class ValUserOrganizationDTO {

    @NotNull(message = "UserId cannot be null")
    private Long userId;

    @NotNull(message = "OrganizationId cannot be null")
    private Long organizationId;

    @NotNull(message = "OrganizationOwner cannot be null")
    private Boolean organizationOwner;

    private Date createdAt;

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

    public Boolean getOrganizationOwner() {
        return organizationOwner;
    }

    public void setOrganizationOwner(Boolean organizationOwner) {
        this.organizationOwner = organizationOwner;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
