package com.juaracoding.cksteam26.dto.validasi;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ValOrganizationDTO {

    @NotBlank(message = "OrganizationName wajib diisi")
    @Size(max = 100, message = "OrganizationName maksimal 100 karakter")
    private String organizationName;

    private Boolean publicVisibility = false;

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
