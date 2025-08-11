package com.juaracoding.cksteam26.dto.response;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 26/07/25 05.20
@Last Modified 26/07/25 05.20
Version 1.0
*/

public class RespOrganizationDTO {

    private Long id;

    private String organizationName;
    private Boolean publicVisibility; // may be disabled

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Boolean getPublicVisibility() {
        return publicVisibility;
    }

    public void setPublicVisibility(Boolean publicVisibility) {
        this.publicVisibility = publicVisibility;
    }
}
