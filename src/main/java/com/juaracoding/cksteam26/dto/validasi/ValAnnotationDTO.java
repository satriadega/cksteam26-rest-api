package com.juaracoding.cksteam26.dto;

import jakarta.validation.constraints.*;

public class ValAnnotationDTO {

    @NotNull(message = "DocumentId wajib diisi")
    private Long documentId;

    private Long ownerUserId;

    private Boolean isVerified = false;

    @NotBlank(message = "SelectedText tidak boleh kosong")
    @Size(max = 500, message = "SelectedText maksimal 500 karakter")
    private String selectedText;

    @NotNull(message = "StartNo wajib diisi")
    @Min(value = 0, message = "StartNo minimal 0")
    private Integer startNo;

    @NotNull(message = "EndNo wajib diisi")
    @Min(value = 0, message = "EndNo minimal 0")
    private Integer endNo;

    @NotBlank(message = "Description tidak boleh kosong")
    @Size(max = 500, message = "Description maksimal 500 karakter")
    private String description;

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getSelectedText() {
        return selectedText;
    }

    public void setSelectedText(String selectedText) {
        this.selectedText = selectedText;
    }

    public Integer getStartNo() {
        return startNo;
    }

    public void setStartNo(Integer startNo) {
        this.startNo = startNo;
    }

    public Integer getEndNo() {
        return endNo;
    }

    public void setEndNo(Integer endNo) {
        this.endNo = endNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
