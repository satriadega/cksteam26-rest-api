package com.juaracoding.cksteam26.dto.response;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 09/08/25 23.44
@Last Modified 09/08/25 23.44
Version 1.0
*/

import java.util.Date;

public class RespListApplianceVerifierDTO {

    private Long documentId;
    private Boolean isAccepted;
    private Date createdAt;

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
