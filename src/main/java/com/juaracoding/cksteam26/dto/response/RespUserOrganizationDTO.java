package com.juaracoding.cksteam26.dto.response;

public class RespUserOrganizationDTO {
    private String username;
    private String name;
    private String email;
    private Boolean organizationOwner;
    private RespOrganizationDTO organization;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
