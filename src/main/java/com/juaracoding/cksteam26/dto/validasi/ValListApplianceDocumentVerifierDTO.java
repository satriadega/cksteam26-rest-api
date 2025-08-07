package com.juaracoding.cksteam26.dto.validasi;

import jakarta.validation.constraints.NotNull;

public class ValListApplianceDocumentVerifierDTO {

    @NotNull(message = "DocumentId wajib diisi")
    private Long documentId;

    @NotNull(message = "UserId wajib diisi")
    private Long userId;

    private Boolean isAccepted = false;

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
}
