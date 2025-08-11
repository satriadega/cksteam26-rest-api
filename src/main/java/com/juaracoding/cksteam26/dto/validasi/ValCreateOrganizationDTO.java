package com.juaracoding.cksteam26.dto.validasi;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 03/08/25 04.30
@Last Modified 03/08/25 04.30
Version 1.0
*/

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ValCreateOrganizationDTO {

    @NotBlank(message = "Organization name must not be empty")
    @Size(min = 3, max = 100, message = "Organization name must be between 3 and 100 characters")
    private String organizationName;

    private List<@Email(message = "Invalid email format") String> members;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }


    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
