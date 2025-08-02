package com.juaracoding.cksteam26.dto.validasi;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 03/08/25 04.30
@Last Modified 03/08/25 04.30
Version 1.0
*/

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ValCreateOrganizationDTO {

    @NotBlank(message = "Organization name must not be empty")
    @Size(min = 3, max = 100, message = "Organization name must be between 3 and 100 characters")
    private String organizationName;

    @NotNull(message = "Organization type is required")
    private Boolean publicVisibility;

    private List<@Email(message = "Invalid email format") String> members;

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

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
