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
    private String username;
    private String name;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
