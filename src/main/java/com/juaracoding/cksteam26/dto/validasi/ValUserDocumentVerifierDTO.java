package com.juaracoding.cksteam26.dto;

import jakarta.validation.constraints.NotNull;

public class ValUserDocumentVerifierDTO {

    @NotNull(message = "DocumentId wajib diisi")
    private Long documentId;

    @NotNull(message = "UserId wajib diisi")
    private Long userId;

    @NotNull(message = "IsVerified wajib diisi")
    private Boolean isVerified;

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

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }
}
