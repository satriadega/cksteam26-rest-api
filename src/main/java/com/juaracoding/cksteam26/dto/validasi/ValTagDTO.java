package com.juaracoding.cksteam26.dto.validasi;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ValTagDTO {

    @NotNull(message = "AnnotationId wajib diisi")
    private Long annotationId;

    @NotBlank(message = "TagName wajib diisi")
    @Size(max = 50, message = "TagName maksimal 50 karakter")
    @Pattern(regexp = "^[a-z]+$", message = "TagName hanya boleh berisi huruf kecil")
    private String tagName;

    public Long getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(Long annotationId) {
        this.annotationId = annotationId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
