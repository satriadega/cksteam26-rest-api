package com.juaracoding.cksteam26.dto;

import jakarta.validation.constraints.*;

public class ValTagDTO {

    @NotNull(message = "AnnotationId wajib diisi")
    private Long annotationId;

    @NotBlank(message = "TagName wajib diisi")
    @Size(max = 50, message = "TagName maksimal 50 karakter")
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
