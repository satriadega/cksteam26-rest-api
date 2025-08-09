package com.juaracoding.cksteam26.dto.validasi;

import jakarta.validation.constraints.NotNull;

public class ValUpdateApplianceVerifierDTO {
    @NotNull
    private Boolean isAccepted;

    @NotNull
    private Long userId;

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
