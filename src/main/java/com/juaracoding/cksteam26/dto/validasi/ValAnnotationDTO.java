package com.juaracoding.cksteam26.dto.validasi;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ValAnnotationDTO {

    @NotNull(message = "DocumentId wajib diisi")
    private Long documentId;

    @NotBlank(message = "SelectedText tidak boleh kosong")
    @Size(max = 1000, message = "SelectedText minimal 1 karakter maksimal 1000 karakter")
    @Pattern(regexp = "(?s)^.{1,1000}$", message = "SelectedText boleh berisi huruf, angka, spasi, dan simbol apa saja, maksimal 500 karakter")
    private String selectedText;

    @NotNull(message = "StartNo wajib diisi")
    @Min(value = 0, message = "StartNo minimal 0")
    private Integer startNo;

    @NotNull(message = "EndNo wajib diisi")
    @Min(value = 0, message = "EndNo minimal 0")
    private Integer endNo;

    @NotBlank(message = "Description tidak boleh kosong")
    @Size(max = 500, message = "Description maksimal 500 karakter")
    @Pattern(regexp = "^[\\s\\S]{1,500}$", message = "Description maksimal 500 karakter")
    private String description;

    @Size(max = 10, message = "Maksimal 10 tags")
    private List<@Pattern(regexp = "^[a-zA-Z]+$", message = "Tag hanya boleh huruf tanpa spasi") String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
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
