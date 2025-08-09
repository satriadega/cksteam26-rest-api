package com.juaracoding.cksteam26.dto.validasi;

import jakarta.validation.constraints.NotNull;

public class ValUpdateApplianceVerifierDTO {
    @NotNull
    private Boolean isAccepted;

    @NotNull
    private String username;

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

  
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
