package com.juaracoding.cksteam26.dto.response;

public class RespUserOrganizationDTO {
    private Long userId;
    private Boolean organizationOwner;
    private RespOrganizationDTO organization;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getOrganizationOwner() {
        return organizationOwner;
    }

    public void setOrganizationOwner(Boolean organizationOwner) {
        this.organizationOwner = organizationOwner;
    }

    public RespOrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(RespOrganizationDTO organization) {
        this.organization = organization;
    }
}
